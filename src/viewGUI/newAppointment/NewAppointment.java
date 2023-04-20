package viewGUI.newAppointment;


import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import java.sql.Connection;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import controller.Features;
import model.DataTypes.Doctor;
import model.DataTypes.Specialization;
import model.Model;
import viewGUI.View;

import javax.swing.*;

public class NewAppointment extends JFrame {
	JPanel newAppointmentPanel;
	JLabel studentIdLabel = new JLabel("Student ID");
	JTextField studentId = new JTextField(15);
	JButton createAppointment = new JButton("Create Appointment");
	JButton back = new JButton("Back");
	String[] hours = {"09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};
	String[] minutes = {"00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"};
	JComboBox hoursComboBox;
	JLabel hoursLabel = new JLabel("Select Time");
	JComboBox minutesComboBox;

	JComboBox specializationComboBox;

	UtilDateModel model = new UtilDateModel();
	JDatePanelImpl datePanel = new JDatePanelImpl(model);
	JDatePickerImpl datePicker;
	JLabel dateLabel = new JLabel("Select Date");
	JLabel doctorLabel = new JLabel("Available Doctors");
	JLabel specializationLabel = new JLabel("Specializations");
	JComboBox doctorList = new JComboBox();

	Connection con;
	Model mainModel;
	View view;
	/*
	Window to display get inputs for new appointment.
	 */
	public void showNewAppointment(Features features, List<Specialization> specializations, Model model, View view) {
		hoursComboBox = new JComboBox(hours);
		minutesComboBox = new JComboBox(minutes);
		datePicker = new JDatePickerImpl(datePanel);

		mainModel = model;
		newAppointmentPanel = new JPanel();
		setSize(300, 500);
		setLocation(200, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		List<String> specializationAvailable = new ArrayList<>();

		for (Specialization name : specializations) {
			specializationAvailable.add(name.getSpecializationName());
		}

		String[] specializationArray = specializationAvailable.toArray(new String[0]);

		specializationComboBox = new JComboBox(specializationArray);

		newAppointmentPanel.add(studentIdLabel);
		newAppointmentPanel.add(studentId);
		newAppointmentPanel.add(hoursLabel);
		newAppointmentPanel.add(hoursComboBox);
		newAppointmentPanel.add(minutesComboBox);
		newAppointmentPanel.add(dateLabel);
		newAppointmentPanel.add(datePicker);
		newAppointmentPanel.add(specializationLabel);
		newAppointmentPanel.add(specializationComboBox);
		newAppointmentPanel.add(back);
		add(newAppointmentPanel);
		setVisible(true);

		back.addActionListener(e -> features.showOriginalMenu());
		createAppointment.addActionListener(e -> features.getAppointmentInfo());
		specializationComboBox.addActionListener(e -> showDoctorList(view));
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
	public void createAppointment(Model model, View view) {
		try {
			Date selectedDate = (Date) datePicker.getModel().getValue();
			LocalDate localDate = view.convertDateToLocalDate(selectedDate);

			String studentUniqueId = studentId.getText();

			String hrs = (String) hoursComboBox.getSelectedItem();
			String mins = (String) minutesComboBox.getSelectedItem();
			String timeConcatenate = hrs + ":" + mins + ":00";
			LocalTime localTime = view.convertStringToLocalTime(timeConcatenate);

			String doctorName = (String) doctorList.getSelectedItem();
			String[] doctorNameSplit = doctorName.split(" ");
			String doctorId = model.getDoctorByName(doctorNameSplit[0], doctorNameSplit[1]);
			model.createNewAppointment(studentUniqueId, doctorId, localDate, localTime);
			showMessage("Appointment Created Successfully");
			view.showMenu();
		} catch (Exception e) {
			showMessage(e.getMessage());
			view.showMenu();
		}
	}

	public void showDoctorList(View view) {
		try {
			String[] temp;
			Date selectedDate = (Date) datePicker.getModel().getValue();
			LocalDate localDate = view.convertDateToLocalDate(selectedDate);
			String specialization = (String) specializationComboBox.getSelectedItem();

			String hrs = (String) hoursComboBox.getSelectedItem();
			String mins = (String) minutesComboBox.getSelectedItem();


			String timeConcatenate = hrs + ":" + mins + ":00";
			LocalTime time = view.convertStringToLocalTime(timeConcatenate);

			List<Doctor> doctors = mainModel.getAvailableDoctors(specialization, localDate, time);
			List<String> doctorsAvailable = new ArrayList<>();
			for (Doctor d : doctors) {
				doctorsAvailable.add(d.getFirstName() + " " + d.getLastName());
			}

			newAppointmentPanel.remove(doctorList);

			doctorList = new JComboBox(doctorsAvailable.toArray());
			newAppointmentPanel.add(doctorLabel);
			newAppointmentPanel.add(doctorList);
			newAppointmentPanel.add(back);
			newAppointmentPanel.add(createAppointment);
			setVisible(true);
		} catch (Exception e) {
			showMessage(e.getMessage());
		}
	}

	public void showMessage(String message) {
		JOptionPane.showMessageDialog(newAppointmentPanel, message);
		setVisible(true);
	}
}
