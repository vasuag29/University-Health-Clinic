package viewGUI.cancelAppointment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import model.DataTypes.Appointment;
import model.Model;
import viewGUI.View;

public class CancelAppointment extends JFrame {
	JTextField studentId = new JTextField(10);
	JLabel studentLabel = new JLabel("Student Id");
	JButton cancel = new JButton("Cancel");
	JComboBox appointmentList = new JComboBox();
	JButton getAppointments = new JButton("Get Appointments");
	JLabel appointmentLabel = new JLabel("Select an appointment");
	JButton back = new JButton("Back to Main Menu");
	JPanel cancelAppointment;
	public void cancelAppointmentObject(View view, Model model) {
		cancelAppointment = new JPanel();
		cancelAppointment.add(studentLabel);
		cancelAppointment.add(studentId);
		cancelAppointment.add(back);
		cancelAppointment.add(getAppointments);
		add(cancelAppointment);
		setLocation(200, 200);
		setSize(300, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		getAppointments.addActionListener(e -> showAppointmentsByStudent(model, view));
		back.addActionListener(e -> view.showMenu());
	}

	private void showAppointmentsByStudent(Model model, View view) {
		try {
			cancelAppointment.removeAll();
			repaint();
			revalidate();
			String student = studentId.getText();
			JLabel temp = new JLabel("Select Appointment Id for " + student);
			List<Appointment> appointmentOfStudent = model.getAppointmentsByStudentId(student);

			List<String> appointmentString = new ArrayList<>();

			for (Appointment a : appointmentOfStudent) {
				appointmentString.add(a.getAppointmentId());
			}

			appointmentList = new JComboBox(appointmentString.toArray());
			cancelAppointment.add(temp);
			cancelAppointment.add(appointmentList);
			cancelAppointment.add(back);
			cancelAppointment.add(cancel);
			add(cancelAppointment);
			setLocation(200, 200);
			setSize(300, 500);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setVisible(true);

			cancel.addActionListener(e -> cancelApp(model, view));
			back.addActionListener(e -> view.showMenu());
		} catch (Exception e) {
			showMessage(e.getMessage());
			setVisible(false);
			dispose();
			view.showMenu();
		}
	}

	private void cancelApp(Model model, View view) {
		try {
			String appId = (String) appointmentList.getSelectedItem();
			if(model.deleteAppointment(appId)) {
				showMessage("Appointment deleted successfully");
				view.showMenu();
			}
		} catch (Exception e) {
			showMessage(e.getMessage());
			view.showMenu();
		}
	}

	private void showMessage(String message) {
		JOptionPane.showMessageDialog(cancelAppointment, message);
		setVisible(true);
	}
}
