package model.DataTypes;

public class LabTest {
  private String name;
  private String description;

  public LabTest(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }
}
