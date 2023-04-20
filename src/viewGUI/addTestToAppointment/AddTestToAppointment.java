package viewGUI.addTestToAppointment;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import controller.Features;
import model.DataTypes.LabTest;
import model.Model;
import viewGUI.View;

public class AddTestToAppointment extends JFrame {
	JPanel addTestPanel;
	JTextField apptId;
	JLabel appLabel;
	JButton addTest = new JButton("Add Test");
	JButton back = new JButton("Back to Main Menu");
	JLabel testLabel;
	JComboBox test = new JComboBox();

	public void addTestToAppointment(Features features, View view, Model model) {
		addTestPanel = new JPanel();
		apptId = new JTextField(10);
		appLabel = new JLabel("Appointment Id");
		testLabel = new JLabel("Tests");
		List<LabTest> labTest = model.getLabTestList();
		List<String>temp = new ArrayList<>();
		for (LabTest l : labTest) {
			temp.add(l.getName());
		}
		test = new JComboBox(temp.toArray());

		addTestPanel.add(appLabel);
		addTestPanel.add(apptId);
		addTestPanel.add(test);
		addTestPanel.add(addTest);
		addTestPanel.add(back);
		add(addTestPanel);
		setLocation(200, 200);
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		addTest.addActionListener(e -> addTest(view, model));
		back.addActionListener(e -> view.showMenu());
	}

	private void addTest(View view, Model model) {
		try {
			String appointmentId = apptId.getText();
			String testName = (String) test.getSelectedItem();
			model.addLabTestToAppointment(testName, appointmentId);
			showMessage("Test Added Successfully");
			view.showMenu();
		} catch (Exception e) {
			showMessage(e.getMessage());
			view.showMenu();
		}

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
