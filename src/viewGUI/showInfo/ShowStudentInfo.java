package viewGUI.showInfo;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import controller.Features;
import model.DataTypes.Appointment;
import model.DataTypes.Student;
import model.Model;
import viewGUI.View;

public class ShowStudentInfo extends JFrame {
	JButton showInfo;
	JTextField studentId;
	JButton back;
	JPanel studentInfoPanel;
	JLabel studentLabel;

	public void showStudentInfo(Features features, Model model, View view) {
		showInfo = new JButton("Show Information");
		studentId = new JTextField(10);
		back = new JButton("Back to Main Menu");
		studentLabel = new JLabel("Student Id");
		studentInfoPanel = new JPanel();
		studentInfoPanel.add(studentLabel);
		studentInfoPanel.add(studentId);
		studentInfoPanel.add(showInfo);
		studentInfoPanel.add(back);
		add(studentInfoPanel);
		setSize(320, 500);
		setLocation(200, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		back.addActionListener(e -> view.showMenu());
		showInfo.addActionListener(e -> showInfo(view, model));
	}

	private void showInfo(View view, Model model) {
		try {
			studentInfoPanel.removeAll();
			revalidate();
			repaint();

			String student = studentId.getText();
			Student students = model.getStudentById(student);
			showTable(students, view);
		} catch (Exception e) {
			showMessage(e.getMessage());
			view.showMenu();
		}
	}

	private void showMessage(String message) {
		JOptionPane.showMessageDialog(studentInfoPanel, message);
		setVisible(true);
	}

	public void disableStudentInfoWindow() {
		setVisible(false);
		dispose();
	}

	private void showTable(Student student, View view) {
		back = new JButton("Back");

		List<String> columns = new ArrayList<>();
		List<String[]> values = new ArrayList<>();
		columns.add("student_id");
		columns.add("first_name");
		columns.add("last_name");
		columns.add("email_id");
		columns.add("phone_number");
		columns.add("degree_earned");
		columns.add("sex");


		values.add(new String[]{student.getStudentId(),
						student.getFirstName(),
						student.getLastName(),
						student.getEmailId(),
						student.getPhoneNumber(),
						student.getDegreeEnrolled(),
						student.getSex()});


		TableModel tableModel = new DefaultTableModel(values.toArray(new Object[][]{}), columns.toArray());
		JTable table = new JTable(tableModel);
		studentInfoPanel.add(new JScrollPane(table));
		studentInfoPanel.add(back);
		back.addActionListener(e -> view.showMenu());
		add(studentInfoPanel);
		setSize(1100, 500);
		setVisible(true);
	}
}
