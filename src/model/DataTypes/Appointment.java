package model.DataTypes;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
  private String appointmentId;
  private LocalDate appointmentDate;
  private LocalTime appointmentTime;
  private String studentId;
  private String doctorId;

  public Appointment(String appointmentId, LocalDate appointmentDate,
                     LocalTime appointmentTime, String studentId, String doctorId) {
    this.appointmentId = appointmentId;
    this.appointmentDate = appointmentDate;
    this.appointmentTime = appointmentTime;
    this.studentId = studentId;
    this.doctorId = doctorId;
  }

  public String getAppointmentId() {
    return appointmentId;
  }

  public LocalDate getAppointmentDate() {
    return appointmentDate;
  }

  public LocalTime getAppointmentTime() {
    return appointmentTime;
  }

  public String getStudentId() {
    return studentId;
  }

  public String getDoctorId() {
    return doctorId;
  }
}
