package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import model.DataTypes.Appointment;
import model.DataTypes.Doctor;
import model.DataTypes.Specialization;
import model.DataTypes.Student;

public interface Model {
  void openDbConnection(String databaseUrl, String username, String password) throws Exception;
  void closeDbConnection();
  Connection getDbConnection();
  List<Doctor> getAllDoctors();
  List<Doctor> getAvailableDoctors(String specializationName,
                                   LocalDate appointmentDate,
                                   LocalTime appointmentTime);
  List<Appointment> getAllAppointments() throws SQLException;
  List<Specialization> getSpecializationList();
  Student getStudentById(String studentId);
  Appointment createNewAppointment(String studentId,
                                   String doctorId,
                                   LocalDate appointmentDate,
                                   LocalTime appointmentTime);
  String getDoctorByName(String first_name, String last_name);
  List<Appointment> getAppointmentsByStudentId(String string) throws Exception;
  boolean deleteDoctor(String doctorId);
  boolean deleteAppointment(String appointmentId);
}
