package viewGUI.showAppointment;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import controller.Features;
import model.DataTypes.Appointment;
import model.Model;

public class ShowAppointment extends JFrame{
	JButton back;
	JButton showAppointmentByStudents;
	JButton showAppointmentStatistics;
	JPanel showAppointmentPanel;
	JTextField studentId;
	JLabel studentLabel;

	JButton showAppointments;

	public void showSubOptions(Features features) {
		showAppointmentPanel = new JPanel();

		back = new JButton("Back");
		showAppointmentByStudents = new JButton("Show a Student's Appointment");
		showAppointmentStatistics = new JButton("Show Appointment Statistics");

		showAppointmentPanel.add(showAppointmentByStudents);
		showAppointmentPanel.add(showAppointmentStatistics);
		showAppointmentPanel.add(back);

		setSize(300, 500);
		setLocation(200, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(showAppointmentPanel);
		setVisible(true);

		back.addActionListener(e -> features.showOriginalMenu());
		showAppointmentByStudents.addActionListener(e -> features.showStudentAppointments(features));
	}

	public void getStudentAppointments(Model model, Features features) {
		showAppointmentPanel.removeAll();
		revalidate();
		repaint();
		studentLabel = new JLabel("Student id");
		studentId = new JTextField(10);
		showAppointments = new JButton("Show Appointments");

		showAppointmentPanel.add(studentLabel);
		showAppointmentPanel.add(studentId);
		showAppointmentPanel.add(showAppointments);

		setSize(300, 500);
		setLocation(200, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(showAppointmentPanel);
		setVisible(true);

		showAppointments.addActionListener(e -> showAppointmentsTable(model, features));
	}

	private void showAppointmentsTable(Model model, Features features) {
			showAppointmentPanel.removeAll();
			revalidate();
			repaint();

			String studentUniqueId = studentId.getText();

			List<String> columns = new ArrayList<>();
			List<String[]> values = new ArrayList<String[]>();
			columns.add("student_id");
			columns.add("doctor_id");
			columns.add("appointment_id");
			columns.add("appointment_time");
			columns.add("appointment_date");
			JButton mainMenu = new JButton("Main Menu");

			List<Appointment> ap1 = model.getAllAppointments();
			List<Appointment> appointments = model.getAppointmentsByStudentId(studentUniqueId);

			for (int i = 0; i < appointments.size(); i++) {
				values.add(new String[]{appointments.get(i).getStudentId(),
								appointments.get(i).getDoctorId(),
								appointments.get(i).getAppointmentId(),
								String.valueOf(appointments.get(i).getAppointmentTime()),
								appointments.get(i).getDoctorId()});
			}

			TableModel tableModel = new DefaultTableModel(values.toArray(new Object[][]{}), columns.toArray());
			JTable table = new JTable(tableModel);
			showAppointmentPanel.add(new JScrollPane(table));
			showAppointmentPanel.add(back);
			back.addActionListener(e -> features.showOriginalMenu());
			add(showAppointmentPanel);
			setSize(500, 500);
			setVisible(true);

	}
	private void showMessage(String message) {
		JOptionPane.showMessageDialog(showAppointmentPanel, message);
		setVisible(true);
	}

	public void disableShowAppointmentWindow() {
		setVisible(false);
		dispose();
	}
}


