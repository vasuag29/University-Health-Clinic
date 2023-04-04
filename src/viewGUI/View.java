package viewGUI;

import controller.Control;
import controller.Features;

public interface View {
	void addFeatures(Features features);

	void setupForNewAppointment(Features features);

	void showMenu();
}
