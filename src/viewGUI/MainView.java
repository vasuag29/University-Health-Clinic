package viewGUI;

import java.sql.Connection;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.swing.*;

import controller.Features;
import model.DataTypes.Specialization;
import model.Model;
import viewGUI.addTestToAppointment.AddTestToAppointment;
import viewGUI.cancelAppointment.CancelAppointment;
import viewGUI.deleteDoctor.DeleteDoctor;
import viewGUI.newAppointment.NewAppointment;
import viewGUI.newDoctor.NewDoctor;
import viewGUI.showAppointment.ShowAppointment;
import viewGUI.showInfo.ShowDoctorInfo;
import viewGUI.showInfo.ShowStudentInfo;
import viewGUI.updateAppointment.UpdateAppointment;

public class MainView extends JFrame implements View {
	NewAppointment appointment = new NewAppointment();
	NewDoctor newDoc = new NewDoctor();
	ShowAppointment showAppointment = new ShowAppointment();
	CancelAppointment cancelAppointmentObject = new CancelAppointment();
	UpdateAppointment updateAppointmentObject = new UpdateAppointment();
	DeleteDoctor deleteDoctorObject = new DeleteDoctor();
	ShowDoctorInfo showDoctor = new ShowDoctorInfo();
	ShowStudentInfo showStudent = new ShowStudentInfo();
	AddTestToAppointment addTestApp = new AddTestToAppointment();

	JButton newAppointment = new JButton("Create New Appointment");
	JButton addNewDoc = new JButton("Add New Doctor");
	JButton addTest = new JButton("Add Test to Appointment");
	JButton updateAppointment = new JButton("Update Appointment");
	JButton cancelAppointment = new JButton("Cancel Appointment");
	JButton showAppointments = new JButton("Show Appointments");
	JButton deleteDoctor = new JButton("Delete Doctor Records");
	JButton showStudentInfo = new JButton("Show Student Information");
	JButton showDoctorInfo = new JButton("Show Doctor Information");
	JLabel credentialsMessage = new JLabel("Enter credentials to connect to the database");
	JLabel username = new JLabel("Username");
	JLabel password = new JLabel("Password");
	JTextField usernameTextField = new JTextField(15);
	JPasswordField passwordTextField = new JPasswordField(15);
	JButton connect = new JButton("Connect");
	JPanel panel;
	Connection con;

	public MainView(String caption) {
		super(caption);
		setSize(300, 500);
		setLocation(200, 200);
		panel = new JPanel();
		panel.add(credentialsMessage);
		panel.add(username);
		panel.add(usernameTextField);
		panel.add(password);
		panel.add(passwordTextField);
		panel.add(connect);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(panel);
		setVisible(true);
		setResizable(false);
	}

	@Override
	public void addFeatures(Features features) {
		connect.addActionListener(e -> features.connectToDB());
		newAppointment.addActionListener(e -> features.makeNewAppointment(features));
		showAppointments.addActionListener(e -> features.showAppointmentsChart(features));
		cancelAppointment.addActionListener(e -> features.cancelAppointment());
		updateAppointment.addActionListener(e -> features.updateAppointment(features));
		deleteDoctor.addActionListener(e -> features.deleteDoctor(features));
		showStudentInfo.addActionListener(e -> features.showStudent(features));
		showDoctorInfo.addActionListener(e -> features.showDoctor(features));
		addNewDoc.addActionListener(e -> features.addDoc(features));
		addTest.addActionListener(e -> features.addTest(features));
	}

	@Override
	public void setupForNewAppointment(Features features, List<Specialization> specializations, Model model, View view) {
		appointment = new NewAppointment();
		setVisible(false);
		appointment.showNewAppointment(features, specializations, model, view);
	}

	@Override
	public void showMenu() {
		panel.removeAll();
		repaint();
		panel.add(newAppointment);
		panel.add(addNewDoc);
		panel.add(addTest);
		panel.add(updateAppointment);
		panel.add(cancelAppointment);
		panel.add(showAppointments);
		panel.add(showDoctorInfo);
		panel.add(showStudentInfo);
		panel.add(deleteDoctor);
		appointment.disableNewAppointmentWindow();
		showAppointment.disableShowAppointmentWindow();
		cancelAppointmentObject.disableShowAppointmentWindow();
		updateAppointmentObject.disableShowAppointmentWindow();
		deleteDoctorObject.disableDeleteDoctorWindow();
		showStudent.disableStudentInfoWindow();
		showDoctor.disableDoctorInfoWindow();
		newDoc.disableDoctorInfoWindow();
		addTestApp.disableDoctorInfoWindow();
		setVisible(true);
	}

	@Override
	public void createNewAppointment(Model model) {
		appointment.createAppointment(model, this);
	}

	@Override
	public String getUsername() {
		return usernameTextField.getText();
	}

	@Override
	public char[] getPassword() {
		return passwordTextField.getPassword();
	}

	@Override
	public void showErrorMessage(String errorMessage) {
		JOptionPane.showMessageDialog(panel, errorMessage);
		setVisible(true);
	}

	@Override
	public void setupForShowAppointments(Features features, Model model) {
		showAppointment = new ShowAppointment();
		setVisible(false);
		showAppointment.showSubOptions(features, model);
	}

	@Override
	public void showStudentAppointments(Model model, Features features) {
		showAppointment.getStudentAppointments(model, features);
	}

	@Override
	public void cancelAppointment(Model model, View view) {
		cancelAppointmentObject = new CancelAppointment();
		setVisible(false);
		cancelAppointmentObject.cancelAppointmentObject(view, model);
	}

	@Override
	public void updateAppointment(Model model, View view, Features features) {
		updateAppointmentObject = new UpdateAppointment();
		setVisible(false);
		updateAppointmentObject.updateAppointment(model, view, features);
	}

	@Override
	public LocalTime convertStringToLocalTime(String time) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm:ss");
		return LocalTime.parse(time, formatter);
	}

	@Override
	public LocalDate convertDateToLocalDate(Date date) {
		Instant instant = date.toInstant();
		ZoneId zoneId = ZoneId.systemDefault();
		ZonedDateTime zonedDateTime = instant.atZone(zoneId);
		return zonedDateTime.toLocalDate();
	}

	@Override
	public void deleteDoctor(Features features, Model model, View view) {
		deleteDoctorObject = new DeleteDoctor();
		setVisible(false);
		deleteDoctorObject.deleteDoctorInfo(features, model, view);
	}

	@Override
	public void showDocInfo(Features features, Model model, View view) {
		showDoctor = new ShowDoctorInfo();
		setVisible(false);
		showDoctor.showDoctorInfo(features, model, view);
	}

	@Override
	public void showStuInfo(Features features, Model model, View view) {
		showStudent = new ShowStudentInfo();
		setVisible(false);
		showStudent.showStudentInfo(features, model, view);
	}

	@Override
	public void addDocInfo(Features features, Model model, View view) {
		newDoc = new NewDoctor();
		setVisible(false);
		newDoc.addNewDoctor(features, view, model);
	}

	@Override
	public void addTest(Features features, Model model, View view) {
		addTestApp = new AddTestToAppointment();
		setVisible(false);
		addTestApp.addTestToAppointment(features, view, model);
	}
}
