package model.DataTypes;

public class Doctor {
  private String doctorId;
  private String firstName;
  private String lastName;
  private String emailId;
  private String qualification;
  private String phoneNumber;
  private String specialization;

  public Doctor(String doctorId, String firstName, String lastName, String emailId,
                String qualification, String phoneNumber, String specialization) {
    this.doctorId = doctorId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.emailId = emailId;
    this.qualification = qualification;
    this.phoneNumber = phoneNumber;
    this.specialization = specialization;
  }

  public String getDoctorId() {
    return doctorId;
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

  public String getQualification() {
    return qualification;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public String getSpecialization() {
    return specialization;
  }
}
