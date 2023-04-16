package viewGUI;

import java.sql.Connection;
import java.util.List;

import javax.swing.*;

import controller.Features;
import model.DataTypes.Specialization;
import viewGUI.newAppointment.NewAppointment;
import viewGUI.showAppointment.ShowAppointment;

public class MainView extends JFrame implements View {
	NewAppointment appointment = new NewAppointment();
	ShowAppointment showAppointment = new ShowAppointment();
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
	}

	@Override
	public void setupForNewAppointment(Features features, List<Specialization> specializations) {
		appointment = new NewAppointment();
		setVisible(false);
		appointment.showNewAppointment(features, specializations);
	}

	@Override
	public void showMenu() {
		panel.removeAll();
		repaint();
		panel.add(newAppointment);
		panel.add(updateAppointment);
		panel.add(cancelAppointment);
		panel.add(showAppointments);
		panel.add(deleteDoctor);
		appointment.disableNewAppointmentWindow();
		setVisible(true);
	}

	@Override
	public void createNewAppointment() {
		appointment.createAppointment();
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
		showAppointment.showChart(features);
	}
}
