package viewGUI.showInfo;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import controller.Features;
import model.DataTypes.Doctor;
import model.DataTypes.Student;
import model.Model;
import viewGUI.View;

public class ShowDoctorInfo extends JFrame {
	JButton showInfo;
	JTextField doctorId;
	JButton back;
	JPanel doctorInfoPanel;
	JLabel doctorLabel;

	public void showDoctorInfo(Features features, Model model, View view) {
		showInfo = new JButton("Show Information");
		doctorId = new JTextField(10);
		back = new JButton("Back to Main Menu");
		doctorLabel = new JLabel("Doctor Id");
		doctorInfoPanel = new JPanel();
		doctorInfoPanel.add(doctorLabel);
		doctorInfoPanel.add(doctorId);
		doctorInfoPanel.add(showInfo);
		doctorInfoPanel.add(back);
		add(doctorInfoPanel);
		setSize(350, 500);
		setLocation(200, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		back.addActionListener(e -> view.showMenu());
		showInfo.addActionListener(e -> doctorInfo(view, model));
	}

	private void doctorInfo(View view, Model model) {
		try {
			doctorInfoPanel.removeAll();
			revalidate();
			repaint();

			String doctor = doctorId.getText();
			Doctor doc = model.getDoctorById(doctor);
			showTable(doc, view);

		} catch (Exception e) {
			showMessage(e.getMessage());
			view.showMenu();
		}
	}

	private void showMessage(String message) {
		JOptionPane.showMessageDialog(doctorInfoPanel, message);
		setVisible(true);
	}

	public void disableDoctorInfoWindow() {
		setVisible(false);
		dispose();
	}

	private void showTable(Doctor doctor, View view) {
		back = new JButton("Back");

		List<String> columns = new ArrayList<>();
		List<String[]> values = new ArrayList<>();
		columns.add("doctor_id");
		columns.add("first_name");
		columns.add("last_name");
		columns.add("email_id");
		columns.add("qualification");
		columns.add("phone_number");
		columns.add("specialization");

		values.add(new String[]{doctor.getDoctorId(),
						doctor.getFirstName(),
						doctor.getLastName(),
						doctor.getEmailId(),
						doctor.getQualification(),
						doctor.getPhoneNumber(),
						doctor.getSpecialization()});

		TableModel tableModel = new DefaultTableModel(values.toArray(new Object[][]{}), columns.toArray());
		JTable table = new JTable(tableModel);
		doctorInfoPanel.add(new JScrollPane(table));
		doctorInfoPanel.add(back);
		back.addActionListener(e -> view.showMenu());
		add(doctorInfoPanel);
		setSize(600, 500);
		setVisible(true);
	}
}
