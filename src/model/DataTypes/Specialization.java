package model.DataTypes;

public class Specialization {
  private String specializationName;
  private String description;

  public Specialization(String specializationName, String description) {
    this.specializationName = specializationName;
    this.description = description;
  }

  public String getSpecializationName() {
    return specializationName;
  }

  public String getDescription() {
    return description;
  }
}
