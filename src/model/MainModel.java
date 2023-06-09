package model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.DataTypes.Appointment;
import model.DataTypes.AppointmentStat;
import model.DataTypes.Doctor;
import model.DataTypes.LabTest;
import model.DataTypes.Qualification;
import model.DataTypes.Specialization;
import model.DataTypes.Student;

public class MainModel implements Model{
  private Connection conn;
  private final DateTimeFormatter sqlDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  private final DateTimeFormatter sqlTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

  public MainModel() {
    this.conn = null;
  }

  public Connection getDbConnection() {
    return this.conn;
  }

  @Override
  public void openDbConnection(String databaseUrl, String username, String password) throws Exception {
    try {
      //Class.forName("com.mysql.cj.jdbc.Driver");
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
        Doctor doctor = readDoctorDataFromResultSet(rs);
        doctors.add(doctor);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return doctors;
  }

  @Override
  public List<Doctor> getAvailableDoctors(String specializationName,
                                          LocalDate appointmentDate,
                                          LocalTime appointmentTime) throws SQLException {
    List<Doctor> doctors = null;

    String call = "CALL get_available_doctors(?,?,?)";
    try (CallableStatement call_stmt = conn.prepareCall(call)) {
      call_stmt.setString(1, specializationName);
      call_stmt.setString(2, appointmentDate.format(sqlDateFormatter));
      call_stmt.setString(3, appointmentTime.format(sqlTimeFormatter));
      call_stmt.execute();
      ResultSet rs = call_stmt.getResultSet();
      doctors = new ArrayList<>();

      while (rs.next()) {
        Doctor doctor = readDoctorDataFromResultSet(rs);
        doctors.add(doctor);
      }
    } catch (SQLException e) {
      throw e;
    }

    return doctors;
  }

  @Override
  public List<Appointment> getAppointmentsByStudentId(String studentId) throws Exception {
    List<Appointment> appointments = null;

    String call = "CALL get_appointments_by_student_id(?)";
    try (CallableStatement call_stmt = conn.prepareCall(call)) {
      call_stmt.setString(1, studentId);
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
    } catch (Exception e) {
      throw  e;
    }

    return appointments;
  }

  @Override
  public List<Appointment> getAllAppointments() throws SQLException {
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
      throw e;
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
        //String desc = rs.getString("description");

        Specialization specialization = new Specialization(name);
        specializations.add(specialization);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return specializations;
  }

  @Override
  public List<Qualification> getQualificationList() {
    List<Qualification> qualifications = null;

    try {
      ResultSet rs = executeSqlQuery("SELECT * FROM qualification");
      qualifications = new ArrayList<>();
      while (rs.next()) {
        String abbr = rs.getString("abbreviation");
        String name = rs.getString("name");

        Qualification qualification = new Qualification(abbr, name);
        qualifications.add(qualification);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return qualifications;
  }

  @Override
  public List<LabTest> getLabTestList() {
    List<LabTest> tests = null;

    try {
      ResultSet rs = executeSqlQuery("SELECT * FROM lab_test");
      tests = new ArrayList<>();
      while (rs.next()) {
        String name = rs.getString("test_name");
        String description = rs.getString("test_description");

        LabTest test = new LabTest(name, description);
        tests.add(test);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return tests;
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

  @Override
  public String getDoctorByName(String first_name, String last_name) {
    String doc_id = null;
    String call = "CALL get_doctor_id(?, ?)";
    try (CallableStatement call_stmt = conn.prepareCall(call)) {
      call_stmt.setString(1, first_name);
      call_stmt.setString(2, last_name);
      call_stmt.execute();
      ResultSet rs = call_stmt.getResultSet();

      // move the cursor once because we know that there will be only one student in the result
      if(rs.next()) {
        doc_id = rs.getString("doctor_id");
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return doc_id;
  }

  @Override
  public boolean deleteDoctor(String doctorId) throws SQLException {
    String call = "CALL delete_doctor(?)";
    try (CallableStatement call_stmt = conn.prepareCall(call)) {
      call_stmt.setString(1, doctorId);
      call_stmt.execute();
      ResultSet rs = call_stmt.getResultSet();

      // the return value will be either 1 or 0 (# of rows deleted)
      if(rs.next()) {
        String rowsDeleted = rs.getString("rows_deleted");
        return rowsDeleted.equals("1");
      }
    } catch (SQLException e) {
      throw e;
    }
    return false;
  }

  @Override
  public boolean deleteAppointment(String appointmentId) {
    String call = "CALL delete_appointment(?)";
    try (CallableStatement call_stmt = conn.prepareCall(call)) {
      call_stmt.setString(1, appointmentId);
      call_stmt.execute();
      ResultSet rs = call_stmt.getResultSet();

      // the return value will be either 1 or 0 (# of rows deleted)
      if(rs.next()) {
        String rowsDeleted = rs.getString("rows_deleted");
        return rowsDeleted.equals("1");
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return false;
  }

  @Override
  public void updateAppointment(String appointmentId, LocalDate newDate, LocalTime newTime) {
    String call = "CALL update_appointment_datetime(?,?,?)";
    try (CallableStatement call_stmt = conn.prepareCall(call)) {
      call_stmt.setString(1, appointmentId);
      call_stmt.setString(2, newDate.format(sqlDateFormatter));
      call_stmt.setString(3, newTime.format(sqlTimeFormatter));
      call_stmt.execute(); // if the appointment does not exist, an SQL exception will be thrown
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<AppointmentStat> getAppointmentsByMonth() {
    List<AppointmentStat> appointmentsByMonth = null;

    String call = "CALL get_appointments_by_month()";
    try (CallableStatement call_stmt = conn.prepareCall(call)) {
      call_stmt.execute();
      ResultSet rs = call_stmt.getResultSet();
      appointmentsByMonth = new ArrayList<>();
      while (rs.next()) {
        String year = rs.getString("year");
        String month = rs.getString("month");
        String monthWithYear = month + " " + year;
        int appointmentCount = rs.getInt("appointment_count");
        appointmentsByMonth.add(new AppointmentStat(monthWithYear, appointmentCount));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return appointmentsByMonth;
  }

  @Override
  public Appointment createNewAppointment(String studentId,
                                          String doctorId,
                                          LocalDate appointmentDate,
                                          LocalTime appointmentTime) throws SQLException {
    Appointment newAppointment = null;

    String call = "CALL create_new_appointment(?,?,?,?)";
    try (CallableStatement call_stmt = conn.prepareCall(call)) {
      call_stmt.setString(1, studentId);
      call_stmt.setString(2, doctorId);
      call_stmt.setString(3, appointmentDate.format(sqlDateFormatter));
      call_stmt.setString(4, appointmentTime.format(sqlTimeFormatter));
      call_stmt.execute();
      ResultSet rs = call_stmt.getResultSet();

      // move the cursor once because we know that there will be only one appointment in the result
      if(rs.next()) {
        String appointmentId = rs.getString("appointment_id");
        String sId = rs.getString("student_id");
        String dId = rs.getString("doctor_id");
        LocalDate apptDate = LocalDate.parse(rs.getString("appointment_date"));
        LocalTime apptTime = LocalTime.parse(rs.getString("appointment_time"));

        newAppointment = new Appointment(appointmentId, apptDate, apptTime, sId, dId);
      }
    } catch (SQLException e) {
      throw e;
    }

    return newAppointment;
  }

  private ResultSet executeSqlQuery(String query) throws SQLException {
    Statement stmt = conn.createStatement();
    ResultSet rs;
    rs = stmt.executeQuery(query);
    return rs;
  }

  private Doctor readDoctorDataFromResultSet(ResultSet rs) {
    Doctor doctor = null;
    try {
      String doctorId = rs.getString("doctor_id");
      String firstName = rs.getString("first_name");
      String lastName = rs.getString("last_name");
      String emailId = rs.getString("email_id");
      String qualification = rs.getString("qualification");
      String phoneNumber = rs.getString("phone_number");
      String specialization = rs.getString("specialization");
      doctor = new Doctor(doctorId, firstName, lastName, emailId, qualification,
              phoneNumber, specialization);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return doctor;
  }

  @Override
  public Doctor getDoctorById(String doctorId) {
    Doctor doctor = null;

    String call = "CALL doctor_by_id(?)";
    try (CallableStatement call_stmt = conn.prepareCall(call)) {
      call_stmt.setString(1, doctorId);
      call_stmt.execute();
      ResultSet rs = call_stmt.getResultSet();

      // move the cursor once because we know that there will be only one student in the result
      if(rs.next()) {
        String sId = rs.getString("doctor_id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String emailId = rs.getString("email_id");
        String qualification = rs.getString("qualification");
        String phone_number = rs.getString("phone_number");
        String specialization = rs.getString("specialization");
        doctor = new Doctor(doctorId, firstName, lastName, emailId, qualification, phone_number, specialization);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return doctor;
  }

  @Override
  public Doctor addNewDoctor(String docId, String fName, String lName,
                             String emailId, String qualification, String phNumber,
                             String specialization) throws SQLException {
    Doctor newDoctor = null;

    String call = "CALL add_doctor(?,?,?,?,?,?,?)";
    try (CallableStatement call_stmt = conn.prepareCall(call)) {
      call_stmt.setString(1, docId);
      call_stmt.setString(2, fName);
      call_stmt.setString(3, lName);
      call_stmt.setString(4, emailId);
      call_stmt.setString(5, qualification);
      call_stmt.setString(6, phNumber);
      call_stmt.setString(7, specialization);
      call_stmt.execute();
      ResultSet rs = call_stmt.getResultSet();

      // move the cursor once because we know that there will be only one doctor in the result
      if(rs.next()) {
        newDoctor = readDoctorDataFromResultSet(rs);
      }
    } catch (SQLException e) {
      throw e;
    }

    return newDoctor;
  }

  @Override
  public LabTest addLabTestToAppointment(String testName, String appointmentId) throws SQLException {
    LabTest labTest = null;

    String call = "CALL add_test_to_appointment(?,?)";
    try (CallableStatement call_stmt = conn.prepareCall(call)) {
      call_stmt.setString(1, testName);
      call_stmt.setString(2, appointmentId);
      call_stmt.execute();
      ResultSet rs = call_stmt.getResultSet();

      // move the cursor once because we know that there will be only one lab test in the result
      if(rs.next()) {
        String name = rs.getString("test_name");
        String description = rs.getString("test_description");
        labTest = new LabTest(name, description);
      }
    } catch (SQLException e) {
      throw e;
    }

    return labTest;
  }
}
