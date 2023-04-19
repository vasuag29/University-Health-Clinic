package viewGUI;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import controller.Control;
import controller.Features;
import model.DataTypes.Specialization;
import model.Model;

public interface View {
	void addFeatures(Features features);

	void setupForNewAppointment(Features features, List<Specialization> specializations, Model model, View view);

	void showMenu();

	void createNewAppointment(Model model);

	String getUsername();

	char[] getPassword();

	void showErrorMessage(String errorMessage);

	void setupForShowAppointments(Features features, Model model);

	void showStudentAppointments(Model model, Features features);

	void cancelAppointment(Model model, View view);

	void updateAppointment(Model model, View view,  Features features);
	LocalTime convertStringToLocalTime(String time);

	public LocalDate convertDateToLocalDate(Date date);
}
