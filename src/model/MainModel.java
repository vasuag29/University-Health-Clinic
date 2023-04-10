package model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import model.DataTypes.Appointment;
import model.DataTypes.Doctor;
import model.DataTypes.Specialization;
import model.DataTypes.Student;

public class MainModel implements Model{
  private Connection conn;

  public MainModel() {
    this.conn = null;
  }

  public Connection getDbConnection() {
    return this.conn;
  }

  @Override
  public void openDbConnection(String databaseUrl, String username, String password) throws Exception {
    try {
      conn = DriverManager.getConnection(databaseUrl, username, password);
    } catch (SQLException e) {
      throw new Exception("Could not connect to database. Please make sure that the username" +
              "and password are correct.");
    }
  }

  @Override
  public void closeDbConnection() {
    try {
      conn.close();
    } catch (SQLException e) {
      throw new RuntimeException("The DB connection could not be closed.");
    }
  }

  @Override
  public List<Doctor> getAllDoctors() {
    List<Doctor> doctors = null;

    try {
      ResultSet rs = executeSqlQuery("SELECT * FROM doctor");
      doctors = new ArrayList<>();
      while (rs.next()) {
        String doctorId = rs.getString("doctor_id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String emailId = rs.getString("email_id");
        String qualification = rs.getString("qualification");
        String phoneNumber = rs.getString("phone_number");
        String specialization = rs.getString("specialization");

        Doctor doctor = new Doctor(doctorId, firstName, lastName, emailId, qualification,
                phoneNumber, specialization);
        doctors.add(doctor);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return doctors;
  }

  @Override
  public List<Appointment> getAllAppointments() {
    List<Appointment> appointments = null;

    String call = "CALL get_appointments()";
    try (CallableStatement call_stmt = conn.prepareCall(call)) {
      call_stmt.execute();
      ResultSet rs = call_stmt.getResultSet();
      appointments = new ArrayList<>();
      while (rs.next()) {
        String apptId = rs.getString("appointment_id");
        LocalDate date = LocalDate.parse(rs.getString("appointment_date"));
        LocalTime time = LocalTime.parse(rs.getString("appointment_time"));
        String sId = rs.getString("student_id");
        String docId = rs.getString("doctor_id");

        Appointment appointment = new Appointment(apptId, date, time, sId, docId);
        appointments.add(appointment);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return appointments;
  }

  @Override
  public List<Specialization> getSpecializationList() {
    List<Specialization> specializations = null;

    try {
      ResultSet rs = executeSqlQuery("SELECT * FROM specialization");
      specializations = new ArrayList<>();
      while (rs.next()) {
        String name = rs.getString("name");
        String desc = rs.getString("description");

        Specialization specialization = new Specialization(name, desc);
        specializations.add(specialization);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return specializations;
  }

  @Override
  public Student getStudentById(String studentId) {
    Student student = null;

    String call = "CALL student_by_id(?)";
    try (CallableStatement call_stmt = conn.prepareCall(call)) {
      call_stmt.setString(1, studentId);
      call_stmt.execute();
      ResultSet rs = call_stmt.getResultSet();

      // move the cursor once because we know that there will be only one student in the result
      if(rs.next()) {
        String sId = rs.getString("student_id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String emailId = rs.getString("email_id");
        String phoneNumber = rs.getString("phone_number");
        LocalDate dob = LocalDate.parse(rs.getString("date_of_birth"));
        String sex = rs.getString("sex");
        String degree = rs.getString("degree_enrolled");
        boolean insurance = Boolean.parseBoolean(rs.getString("university_health_insurance"));

        student = new Student(sId, firstName, lastName, emailId, phoneNumber, dob, sex, degree, insurance);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return student;
  }

  private ResultSet executeSqlQuery(String query) throws SQLException {
    Statement stmt = conn.createStatement();
    ResultSet rs;
    rs = stmt.executeQuery(query);
    return rs;
  }
}
