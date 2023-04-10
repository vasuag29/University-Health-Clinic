package viewGUI.newAppointment;


import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import java.util.Date;

import controller.Features;
import javax.swing.*;

public class NewAppointment extends JFrame {
	JPanel newAppointmentPanel;
	JLabel studentIdLabel = new JLabel("Student ID");
	JTextField studentId = new JTextField(15);
	JButton createAppointment = new JButton("Create Appointment");
	JButton back = new JButton("Back");
	String[] hours = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
	String[] minutes = {"00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"};
	String[] amPm = {"AM", "PM"};
	JComboBox hoursComboBox = new JComboBox(hours);
	JLabel hoursLabel = new JLabel("Select Time");
	JComboBox minutesComboBox = new JComboBox(minutes);
	JComboBox timeComboBox = new JComboBox(amPm);

	UtilDateModel model = new UtilDateModel();
	JDatePanelImpl datePanel = new JDatePanelImpl(model);
	JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
	JLabel dateLabel = new JLabel("Select Date");

	/*
	Window to display get inputs for new appointment.
	 */
	public void showNewAppointment(Features features) {
		newAppointmentPanel = new JPanel();
		setSize(320, 500);
		setLocation(200, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		newAppointmentPanel.add(studentIdLabel);
		newAppointmentPanel.add(studentId);
		newAppointmentPanel.add(hoursLabel);
		newAppointmentPanel.add(hoursComboBox);
		newAppointmentPanel.add(minutesComboBox);
		newAppointmentPanel.add(timeComboBox);
		newAppointmentPanel.add(dateLabel);
		newAppointmentPanel.add(datePicker);
		newAppointmentPanel.add(createAppointment);
		newAppointmentPanel.add(back);

		add(newAppointmentPanel);
		setVisible(true);

		back.addActionListener(e -> features.showOriginalMenu());
		createAppointment.addActionListener(e -> features.getAppointmentInfo());
	}

	/*
	Hide create new appointment window
	 */
	public void disableNewAppointmentWindow() {
		setVisible(false);
		dispose();
	}

	/*
	Get appointment info
	 */
	public void createAppointment() {
		Date selectedDate = (Date) datePicker.getModel().getValue();
		String studentUniqueId = studentId.getText();
		String hrs = hoursComboBox.getName();
		String mins = minutesComboBox.getName();
		String time = timeComboBox.getName();
	}
}
