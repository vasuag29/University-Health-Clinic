package model;

import java.sql.Connection;
import java.util.List;

import model.DataTypes.Appointment;
import model.DataTypes.Doctor;
import model.DataTypes.Specialization;
import model.DataTypes.Student;

public interface Model {
  void openDbConnection(String databaseUrl, String username, String password);
  void closeDbConnection();
  Connection getDbConnection();
  List<Doctor> getAllDoctors();
  List<Appointment> getAllAppointments();
  List<Specialization> getSpecializationList();
  Student getStudentById(String studentId);
}
