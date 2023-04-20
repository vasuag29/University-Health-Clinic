package viewGUI.newDoctor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import controller.Features;
import model.DataTypes.Qualification;
import model.DataTypes.Specialization;
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

		doctorId = new JTextField(12);
		firstName = new JTextField(12);
		lastName = new JTextField(12);
		phone_number = new JTextField(12);
		email_id = new JTextField(12);

		List<Specialization> spec = model.getSpecializationList();
		List<String> temp = new ArrayList<>();

		for (Specialization s : spec) {
			temp.add(s.getSpecializationName());
		}

		specialization = new JComboBox(temp.toArray());

		List<Qualification> qua = model.getQualificationList();
		temp = new ArrayList<>();

		for(Qualification q : qua) {
			temp.add(q.abbreviation());
		}
		qualification = new JComboBox(temp.toArray());

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

		add(newDoctorPanel);
		setLocation(200, 200);
		setSize(260, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);

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

			model.addNewDoctor(doctor, firstN, lastN, email, quali, phone, spec);
			showMessage("New Doctor Added Successfully!");
			view.showMenu();
		} catch (Exception e) {
			showMessage(e.getMessage());
			view.showMenu();
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
