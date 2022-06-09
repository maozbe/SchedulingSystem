package models;

import models.Enums.LocationEnum;
import models.Enums.StatusEnum;

public class Assignment {

  public int assignTimeForComplete;
  private String assignId;
  private String assignName;
  private LocationEnum assignLocation;
  private StatusEnum status;
  private String nameOfHandlingEngineer;

  public String getAssignId() {
    return assignId;
  }

  public void setAssignId(String assignId) {
    this.assignId = assignId;
  }

  public String getAssignName() {
    return assignName;
  }

  public void setAssignName(String assignName) {
    this.assignName = assignName;
  }

  public LocationEnum getAssignLocation() {
    return assignLocation;
  }

  public void setAssignLocation(LocationEnum assignLocationEnum) {
    this.assignLocation = assignLocationEnum;
  }

  public StatusEnum getStatus() {
    return status;
  }

  public String getNameOfHandlingEngineer() {
    return nameOfHandlingEngineer;
  }

  public void setNameOfHandlingEngineer(String nameOfHandlingEngineer) {
    this.nameOfHandlingEngineer = nameOfHandlingEngineer;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public double getAssignTimeForComplete() {
    return assignTimeForComplete;
  }

  public void setAssignTimeForComplete(int assignTimeForComplete) {
    this.assignTimeForComplete = assignTimeForComplete;
  }

  @Override
  public String toString() {
    return "Assignment{" +
        "assignTimeForComplete=" + assignTimeForComplete/1000 +"sec" +
        ", assignId='" + assignId + '\'' +
        ", assignName='" + assignName + '\'' +
        ", assignLocation=" + assignLocation +
        ", status=" + status +
        ", nameOfHandlingEngineer='" + nameOfHandlingEngineer + '\'' +
        '}';
  }
}
