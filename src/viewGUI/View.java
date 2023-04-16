package viewGUI;

import java.util.List;

import controller.Control;
import controller.Features;
import model.DataTypes.Specialization;

public interface View {
	void addFeatures(Features features);

	void setupForNewAppointment(Features features, List<Specialization> specializations);

	void showMenu();

	void createNewAppointment();

	String getUsername();

	char[] getPassword();

	void showErrorMessage(String errorMessage);

	void setupForShowAppointments(Features features);
}
