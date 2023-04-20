package viewGUI.newDoctor;

import javax.swing.*;

import controller.Features;
import model.Model;
import viewGUI.View;

public class NewDoctor extends JFrame {
	JButton add;
	JTextField doctorId;
	JButton back;
	JPanel newDoctorPanel;
	JLabel doctorLabel;
	JLabel firstNameLabel, lastNameLabel, emailLabel, phoneNumberLabel, qualificationLabel, specializationLabel;
	JTextField firstName;
	JTextField lastName;
	JTextField email_id;
	JComboBox<String> qualification;
	JTextField phone_number;
	JComboBox<String> specialization;

	public void addNewDoctor(Features features, View view, Model model) {
		newDoctorPanel = new JPanel();

		add = new JButton("Add");
		back = new JButton("Back to Main Menu");

		doctorLabel = new JLabel("Doctor Id");
		firstNameLabel = new JLabel("First Name");
		lastNameLabel = new JLabel("Last Name");
		emailLabel = new JLabel("Email Id");
		phoneNumberLabel = new JLabel("Phone Number");
		qualificationLabel = new JLabel("Qualification");
		specializationLabel = new JLabel("Specialization");

		doctorId = new JTextField(10);
		firstName = new JTextField(10);
		lastName = new JTextField(10);
		phone_number = new JTextField(10);
		email_id = new JTextField(10);
		qualification = new JComboBox<>();
		specialization = new JComboBox<>();

		newDoctorPanel.add(doctorLabel);
		newDoctorPanel.add(doctorId);
		newDoctorPanel.add(firstNameLabel);
		newDoctorPanel.add(firstName);
		newDoctorPanel.add(lastNameLabel);
		newDoctorPanel.add(lastName);
		newDoctorPanel.add(emailLabel);
		newDoctorPanel.add(email_id);
		newDoctorPanel.add(phoneNumberLabel);
		newDoctorPanel.add(phone_number);
		newDoctorPanel.add(qualificationLabel);
		newDoctorPanel.add(qualification);
		newDoctorPanel.add(specializationLabel);
		newDoctorPanel.add(specialization);
		newDoctorPanel.add(add);
		newDoctorPanel.add(back);

		add.addActionListener(e -> addDoctor(view, model));
		back.addActionListener(e -> view.showMenu());
	}

	void addDoctor(View view, Model model) {
		try {
			String doctor = doctorId.getText();
			String phone = phone_number.getText();
			String email = email_id.getText();
			String quali = (String) qualification.getSelectedItem();
			String spec = (String) specialization.getSelectedItem();
			String firstN = firstName.getText();
			String lastN = lastName.getText();

		} catch (Exception e) {
			showMessage(e.getMessage());
		}
	}

	private void showMessage(String message) {
		JOptionPane.showMessageDialog(newDoctorPanel, message);
		setVisible(true);
	}

	public void disableDoctorInfoWindow() {
		setVisible(false);
		dispose();
	}
}
