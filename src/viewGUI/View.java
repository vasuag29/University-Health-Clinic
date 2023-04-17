package viewGUI;

import java.util.List;

import controller.Control;
import controller.Features;
import model.DataTypes.Specialization;
import model.Model;

public interface View {
	void addFeatures(Features features);

	void setupForNewAppointment(Features features, List<Specialization> specializations, Model model);

	void showMenu();

	void createNewAppointment(Model model);

	String getUsername();

	char[] getPassword();

	void showErrorMessage(String errorMessage);

	void setupForShowAppointments(Features features);

	void showStudentAppointments(Model model, Features features);
}
