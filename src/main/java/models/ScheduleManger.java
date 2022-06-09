package models;

import models.Enums.LocationEnum;
import models.Enums.StatusEnum;

import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

public class ScheduleManger {
  private List<Assignment> assignmentList = new ArrayList<>();
  private List<Engineer> engineerList = new ArrayList<>();
  private ExecutorService executorService = Executors.newFixedThreadPool(10);

  public List<Assignment> getAssignmentList() {
    return assignmentList;
  }

  public List<Engineer> getEngineerList() {
    return engineerList;
  }


  public static void main(String[] args) throws InterruptedException {
    ScheduleManger sm = new ScheduleManger();
    sm.generateEngineers();
    sm.assignmentGenerator();
    while(true) {
      System.out.println("Time:"+LocalTime.now() + " start process");
      sm.updateEngineersAvailability();
      System.out.println("Time:"+LocalTime.now() + " availability was updated");
      sm.getEngineerList().forEach(System.out::println);
      sm.randomAssignmentGenerator();
      System.out.println("Time:"+LocalTime.now() + " Random Assignment has been added");
      sm.setEmployeeToAssignment();
      System.out.println("Time:"+LocalTime.now() + " Employer got assignment");
      sm.cleanAssignmentQueue();
      System.out.println("Time:"+LocalTime.now() + " Q got clean-up");
      sm.getAssignmentList().forEach(System.out::println);
      sleep(2000);
    }
  }

  private void randomAssignmentGenerator() {
    int rnd = new Random().nextInt(2); // max 2 mission can be added each minute
    for(int i=0; i< rnd; i++) {
      assignmentGenerator();
    }
  }

  private void updateEngineersAvailability() {
    for (Engineer eng: engineerList) {
      if(eng.getStartShift().getHour() <= LocalTime.now().getHour() &&
          eng.getEndShift().getHour() <= LocalTime.now().getHour() + 8 &&
          eng.getCurrentAssignment() == null){
        eng.setAvailable(true);
      }
      else
        eng.setAvailable(false);
    }
  }


  private void generateEngineers() {
    String[] names = {"Volvo", "BMW", "Ford", "Mazda", "John", "Ray", "Leo", "Messi", "Cris", "Ronaldo"};
    for(int i =0; i<10; i++){
      Engineer eng = new Engineer();
      eng.setName(names[i]);
      eng.setCurrentAssignment(null);
      eng.setCurrentLocation(LocationEnum.randomLocation());
      eng.setStartShift(LocalTime.of(new Random().nextInt(24),0,0,0));
      eng.setEndShift(LocalTime.of((eng.getStartShift().getHour()+8)%24,0,0,0));
      engineerList.add(eng);
    }
  }

  private synchronized void cleanAssignmentQueue() {
    if(!assignmentList.isEmpty()) {
      for (int i=0; i < assignmentList.size(); i++) {
        if (assignmentList.get(i).getStatus() == StatusEnum.COMPLETED) {
          assignmentList.remove(i);
        }
      }
    }
  }

  private synchronized boolean isAssignmentUnassignedExist() {
    for (Assignment assign : assignmentList) {
      if (assign.getStatus() == StatusEnum.UNASSIGNED) {
        return true;
      }
    }
    return false;
  }


  public synchronized void setEmployeeToAssignment(){
    if(getAssignmentList().isEmpty() || !isAssignmentUnassignedExist()){
      return;
    }
    Assignment assignment = getAssignmentList().get(new Random().nextInt(getAssignmentList().size()));
    while(assignment.getStatus() != StatusEnum.UNASSIGNED) {
      assignment = getAssignmentList().get(new Random().nextInt(getAssignmentList().size()));
    }
    Assignment finalAssignment = assignment;
    Optional<Engineer> closestEng = Optional.of(engineerList.stream()
        .filter(Engineer::isAvailable).min((e1, e2) -> (int) (e1.distanceFromAssignment(finalAssignment) - e2.distanceFromAssignment(finalAssignment))).get());
    if(closestEng.isPresent()){
      finalAssignment.setNameOfHandlingEngineer(closestEng.get().getName());
      closestEng.get().setCurrentAssignment(assignment);
      executorService.execute(closestEng.get());
    }


  }
  //TODO: can use kafka in the middle as a queue
  private void assignmentGenerator() {
    Assignment assignment = new Assignment();
    String uuid = UUID.randomUUID().toString();
    assignment.setAssignId(uuid);
    assignment.setAssignLocation(LocationEnum.randomLocation());
    assignment.setAssignName("task number:" + uuid + "\nat location: " + assignment.getAssignLocation().getCityName());
    assignment.setStatus(StatusEnum.UNASSIGNED);
    assignment.setAssignTimeForComplete(randomTime());
    assignment.setNameOfHandlingEngineer(null);
    getAssignmentList().add(assignment);

  }

  private int randomTime() {
    return 1000 + new Random().nextInt(4000);
  }


}
