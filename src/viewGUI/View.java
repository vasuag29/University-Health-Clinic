package viewGUI;

import controller.Control;
import controller.Features;

public interface View {
	void addFeatures(Features features);

	void setupForNewAppointment(Features features);

	void showMenu();

	void createNewAppointment();

	String getUsername();

	char[] getPassword();

	void showErrorMessage(String errorMessage);
}
