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
		JButton showAllAppointments;
		JButton showAppointmentByStudents;
		JButton showAppointmentStatistics;
		JPanel showAppointmentPanel;
		JLabel studentLabel = new JLabel("Student id");
		JTextField studentId = new JTextField(10);
		JButton showAppointments = new JButton("Show Appointments");

		public void showSubOptions(Features features, Model model) {
			showAppointmentPanel = new JPanel();

			back = new JButton("Back");
			showAllAppointments = new JButton("Show All Appointments");
			showAppointmentByStudents = new JButton("Show a Student's Appointment");
			showAppointmentStatistics = new JButton("Show Appointment Statistics");

			showAppointmentPanel.add(showAllAppointments);
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
			showAllAppointments.addActionListener(e -> showAppointment(model, features));
		}

		private void showAppointment(Model model, Features features) {
			showAppointmentPanel.removeAll();
			revalidate();
			repaint();

			List<Appointment> ap1 = null;
			try {
				ap1 = model.getAllAppointments();
			} catch (Exception e) {
				showMessage(e.getMessage());
			}

			assert ap1 != null;
			if (ap1.size() > 0) {
				showTable(ap1, features);
			} else {
				showMessage("No Appointments Available");
				features.showOriginalMenu();
			}
		}

		private void showTable(List<Appointment> appointment, Features features) {
			back = new JButton("Back");

			List<String> columns = new ArrayList<>();
			List<String[]> values = new ArrayList<>();
			columns.add("student_id");
			columns.add("doctor_id");
			columns.add("appointment_id");
			columns.add("appointment_time");
			columns.add("appointment_date");

			for (Appointment value : appointment) {
				values.add(new String[]{value.getStudentId(),
								value.getDoctorId(),
								value.getAppointmentId(),
								String.valueOf(value.getAppointmentTime()),
								value.getAppointmentDate().toString()});
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
		public void getStudentAppointments(Model model, Features features) {
			back = new JButton("Back");
			showAppointmentPanel.removeAll();
			revalidate();
			repaint();


			showAppointmentPanel.add(studentLabel);
			showAppointmentPanel.add(studentId);
			showAppointmentPanel.add(showAppointments);
			showAllAppointments.add(back);

			setSize(300, 500);
			setLocation(200, 200);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			add(showAppointmentPanel);
			setVisible(true);

			showAppointments.addActionListener(e -> showAppointmentsTable(model, features));
			back.addActionListener(e -> features.showOriginalMenu());
		}

		private void showAppointmentsTable(Model model, Features features) {
			showAppointmentPanel.removeAll();
			revalidate();
			repaint();

			String studentUniqueId = studentId.getText();

			List<Appointment> appointments = new ArrayList<>();
			try {
				appointments = model.getAppointmentsByStudentId(studentUniqueId);
				showTable(appointments, features);
			} catch (Exception e) {
				showMessage(e.getMessage());
				features.showOriginalMenu();
			}
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


