package viewGUI.updateAppointment;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.*;

import controller.Features;
import model.DataTypes.Appointment;
import model.Model;
import viewGUI.View;

public class UpdateAppointment extends JFrame {
	String[] hours = {"09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};
	String[] minutes = {"00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"};
	JLabel newDate = new JLabel("Updated Date");
	JLabel newTime = new JLabel("Updated Time");
	JComboBox hoursComboBox;
	JComboBox minutesComboBox;
	JPanel updateAppointmentPanel;
	UtilDateModel model = new UtilDateModel();
	JDatePanelImpl datePanel = new JDatePanelImpl(model);
	JDatePickerImpl datePicker;
	JButton update = new JButton("Update");
	JLabel studentId = new JLabel("Student Id");
	JTextField studentText = new JTextField(10);
	JComboBox appList = new JComboBox();
	JButton go = new JButton("Go");
	JButton back = new JButton("Back");
	public void updateAppointment(Model model, View view, Features features) {
		hoursComboBox = new JComboBox(hours);
		minutesComboBox = new JComboBox(minutes);
		datePicker = new JDatePickerImpl(datePanel);

		updateAppointmentPanel = new JPanel();
		updateAppointmentPanel.add(studentId);
		updateAppointmentPanel.add(studentText);
		updateAppointmentPanel.add(go);
		updateAppointmentPanel.add(back);
		go.addActionListener(e -> getAppointments(model, view));
		back.addActionListener(e -> features.showOriginalMenu());
		add(updateAppointmentPanel);
		setSize(320, 500);
		setLocation(200, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void getAppointments(Model model, View view) {
		updateAppointmentPanel.remove(go);
		updateAppointmentPanel.remove(back);
		repaint();
		revalidate();

		String student = studentText.getText();
		List<Appointment> appointmentOfStudent = null;
		try {
			appointmentOfStudent = model.getAppointmentsByStudentId(student);
			List<String> appointmentString = new ArrayList<>();

			for (Appointment a : appointmentOfStudent) {
				appointmentString.add(a.getAppointmentId());
			}

			appList = new JComboBox(appointmentString.toArray());

			updateAppointmentPanel.add(appList);
			updateAppointmentPanel.add(newDate);
			updateAppointmentPanel.add(hoursComboBox);
			updateAppointmentPanel.add(minutesComboBox);

			updateAppointmentPanel.add(newTime);
			updateAppointmentPanel.add(datePicker);

			updateAppointmentPanel.add(update);
			updateAppointmentPanel.add(back);
			add(updateAppointmentPanel);
			setSize(320, 500);
			setLocation(200, 200);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setVisible(true);

			update.addActionListener(e -> getUpdateAppointmentInfo(model, view));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	private void getUpdateAppointmentInfo(Model model, View view) {
		String hrs = (String) hoursComboBox.getSelectedItem();
		String mins = (String) minutesComboBox.getSelectedItem();
		String timeConcatenate = hrs + ":" + mins + ":00";
		LocalTime localTime = view.convertStringToLocalTime(timeConcatenate);

		Date selectedDate = (Date) datePicker.getModel().getValue();
		LocalDate localDate = view.convertDateToLocalDate(selectedDate);
	}
}
