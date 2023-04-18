package viewGUI;

import java.sql.Connection;
import java.util.List;

import javax.swing.*;

import controller.Features;
import model.DataTypes.Specialization;
import model.Model;
import viewGUI.cancelAppointment.CancelAppointment;
import viewGUI.newAppointment.NewAppointment;
import viewGUI.showAppointment.ShowAppointment;

public class MainView extends JFrame implements View {
	NewAppointment appointment = new NewAppointment();
	ShowAppointment showAppointment = new ShowAppointment();
	CancelAppointment cancelAppointmentObject = new CancelAppointment();
	JButton newAppointment = new JButton("Create New Appointment");
	JButton updateAppointment = new JButton("Update Appointment");
	JButton cancelAppointment = new JButton("Cancel Appointment");
	JButton showAppointments = new JButton("Show Appointments");
	JButton deleteDoctor = new JButton("Delete Doctor Records");
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
	}

	@Override
	public void setupForNewAppointment(Features features, List<Specialization> specializations, Model model) {
		appointment = new NewAppointment();
		setVisible(false);
		appointment.showNewAppointment(features, specializations, model);
	}

	@Override
	public void showMenu() {
		panel.removeAll();
		repaint();
		panel.add(newAppointment);
		//panel.add(updateAppointment);
		panel.add(cancelAppointment);
		panel.add(showAppointments);
		panel.add(deleteDoctor);
		appointment.disableNewAppointmentWindow();
		showAppointment.disableShowAppointmentWindow();
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
	public void setupForShowAppointments(Features features) {
		showAppointment = new ShowAppointment();
		setVisible(false);
		showAppointment.showSubOptions(features);
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
}
