package model.DataTypes;

public class Qualification {
  private String abbreviation;
  private String name;

  public Qualification(String abbreviation, String name) {
    this.abbreviation = abbreviation;
    this.name = name;
  }

  public String abbreviation() {
    return abbreviation;
  }

  public String name() {
    return name;
  }
}