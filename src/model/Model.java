package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import model.DataTypes.Appointment;
import model.DataTypes.AppointmentStat;
import model.DataTypes.Doctor;
import model.DataTypes.LabTest;
import model.DataTypes.Qualification;
import model.DataTypes.Specialization;
import model.DataTypes.Student;

public interface Model {
  void openDbConnection(String databaseUrl, String username, String password) throws Exception;
  void closeDbConnection();
  Connection getDbConnection();
  List<Doctor> getAllDoctors();
  List<Doctor> getAvailableDoctors(String specializationName,
                                   LocalDate appointmentDate,
                                   LocalTime appointmentTime) throws SQLException;
  List<Appointment> getAllAppointments() throws SQLException;
  List<Specialization> getSpecializationList();
  List<Qualification> getQualificationList();
  List<LabTest> getLabTestList();
  Student getStudentById(String studentId);
  Appointment createNewAppointment(String studentId,
                                   String doctorId,
                                   LocalDate appointmentDate,
                                   LocalTime appointmentTime) throws SQLException;
  String getDoctorByName(String first_name, String last_name);
  List<Appointment> getAppointmentsByStudentId(String string) throws Exception;
  boolean deleteDoctor(String doctorId) throws SQLException;
  boolean deleteAppointment(String appointmentId);
  void updateAppointment(String appointmentId, LocalDate newDate, LocalTime newTime);
  List<AppointmentStat> getAppointmentsByMonth();
  Doctor getDoctorById(String doctorId);

  Doctor addNewDoctor(String docId, String fName, String lName,
                      String emailId, String qualification, String phNumber,
                      String specialization) throws SQLException;

  LabTest addLabTestToAppointment(String testName, String appointmentId) throws SQLException;
}
