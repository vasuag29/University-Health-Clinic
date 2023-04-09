package model.DataTypes;

import java.time.LocalDate;

public class Student {
  private String studentId;
  private String firstName;
  private String lastName;
  private String emailId;
  private String phoneNumber;
  private LocalDate dateOfBirth;
  private String sex;
  private String degreeEnrolled;
  private boolean universityHealthInsurance;

  public Student(String studentId, String firstName, String lastName,
                 String emailId, String phoneNumber, LocalDate dateOfBirth,
                 String sex, String degreeEnrolled, boolean universityHealthInsurance) {
    this.studentId = studentId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.emailId = emailId;
    this.phoneNumber = phoneNumber;
    this.dateOfBirth = dateOfBirth;
    this.sex = sex;
    this.degreeEnrolled = degreeEnrolled;
    this.universityHealthInsurance = universityHealthInsurance;
  }

  public String getStudentId() {
    return studentId;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmailId() {
    return emailId;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public String getSex() {
    return sex;
  }

  public String getDegreeEnrolled() {
    return degreeEnrolled;
  }

  public boolean isUniversityHealthInsurance() {
    return universityHealthInsurance;
  }
}
