package models;

import models.Enums.LocationEnum;
import models.Enums.StatusEnum;
import java.time.LocalTime;


public class Engineer implements Runnable {

  private String name;
  private LocationEnum currentLocation;
  private boolean available;
  private LocalTime startShift;
  private LocalTime endShift;
  private Assignment currentAssignment;


  public Engineer(){}

  public Engineer(Engineer eng) {
    this.name = eng.name;
    this.currentLocation = eng.currentLocation;
    this.available = eng.available;
    this.startShift = eng.startShift;
    this.endShift = eng.endShift;
    this.currentAssignment = eng.currentAssignment;
  }

  public static double locationCompare(Engineer e1, Engineer e2, Assignment assignment) {
    double e1Distance = Math.sqrt(Math.pow((e1.getCurrentLocation().getYaxis() - assignment.getAssignLocation().getYaxis()),2)
                         + (Math.pow((e1.getCurrentLocation().getXaxis() - assignment.getAssignLocation().getXaxis()),2)));
    double e2Distance = Math.sqrt(Math.pow((e2.getCurrentLocation().getYaxis() - assignment.getAssignLocation().getYaxis()),2)
                        + (Math.pow((e2.getCurrentLocation().getXaxis() - assignment.getAssignLocation().getXaxis()),2)));

    return Math.min(e1Distance, e2Distance);
  }

  public double distanceFromAssignment(Assignment assignment) {
    return Math.sqrt(Math.pow((this.getCurrentLocation().getYaxis() - assignment.getAssignLocation().getYaxis()),2)
        + (Math.pow((this.getCurrentLocation().getXaxis() - assignment.getAssignLocation().getXaxis()),2)));
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocationEnum getCurrentLocation() {
    return currentLocation;
  }

  public void setCurrentLocation(LocationEnum currentLocation) {
    this.currentLocation = currentLocation;
  }

  public boolean isAvailable() {
    return available;
  }

  public void setAvailable(boolean available) {
    this.available = available;
  }

  public LocalTime getStartShift() {
    return startShift;
  }

  public void setStartShift(LocalTime startShift) {
    this.startShift = startShift;
  }

  public LocalTime getEndShift() {
    return endShift;
  }

  public void setEndShift(LocalTime endShift) {
    this.endShift = endShift;
  }

  public Assignment getCurrentAssignment() {
    return currentAssignment;
  }

  public void setCurrentAssignment(Assignment currentAssignment) {
    this.currentAssignment = currentAssignment;
  }


  @Override
  public void run() {
    System.out.println(Thread.currentThread().getName()+"\nEngineer name: "+ this.getName()+ "(Start) AssignmentID = "+this.getCurrentAssignment().getAssignId()
                        +"\nAssignment Name: " + this.getCurrentAssignment().getAssignName()
                        +"\nAverage time to complete the mission: " + this.getCurrentAssignment().getAssignTimeForComplete()/1000 + "sec"
                        +"\nHandling by: " + this.getName());
    getCurrentAssignment().setStatus(StatusEnum.IN_PROGRESS);
    getCurrentAssignment().setNameOfHandlingEngineer(this.name);
    try {
      Thread.sleep((long)this.currentAssignment.getAssignTimeForComplete());
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println(Thread.currentThread().getName()+"\nEngineer name: "+ this.getName()+ "(completed!) AssignmentID = "+this.getCurrentAssignment().getAssignId()
        +"\nAssignment Name: " + this.getCurrentAssignment().getAssignName());
    currentAssignment.setStatus(StatusEnum.COMPLETED);
    this.setCurrentLocation(currentAssignment.getAssignLocation());
    this.setAvailable(true);
    setCurrentAssignment(null);
  }

  @Override
  public String toString() {
    String assignName;
    assignName = currentAssignment != null? currentAssignment.getAssignName() : "null";
    return "Engineer{" +
        "name='" + name + '\'' +
        ", currentLocation=" + currentLocation +
        ", available=" + available +
        ", startShift=" + startShift +
        ", endShift=" + endShift +
        ", currentAssignment=" + assignName +
        '}';
  }
}
