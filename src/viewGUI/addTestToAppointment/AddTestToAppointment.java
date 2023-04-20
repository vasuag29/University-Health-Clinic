package viewGUI.addTestToAppointment;

import javax.swing.*;

import controller.Features;
import model.Model;
import viewGUI.View;

public class AddTestToAppointment extends JFrame {
	JPanel addTestPanel;
	JTextField apptId;
	JLabel appLabel;
	JButton addTest = new JButton("Add Test");
	JButton back = new JButton("Back to Main Menu");
	JComboBox test;

	public void addTestToAppointment(Features features, View view, Model model) {
		addTestPanel = new JPanel();
		apptId = new JTextField(10);
		appLabel = new JLabel("Appointment Id");
		addTestPanel.add(appLabel);
		addTestPanel.add(apptId);
		addTestPanel.add(addTest);
		addTestPanel.add(back);
		addTest.addActionListener(e -> addTest(view, model));
		back.addActionListener(e -> view.showMenu());
	}

	private void addTest(View view, Model model) {
		String appointmentId = apptId.getText();
	}

	private void showMessage(String message) {
		JOptionPane.showMessageDialog(addTestPanel, message);
		setVisible(true);
	}

	public void disableDoctorInfoWindow() {
		setVisible(false);
		dispose();
	}

}
