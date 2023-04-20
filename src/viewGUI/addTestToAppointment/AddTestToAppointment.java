package viewGUI.addTestToAppointment;

import javax.swing.*;

import controller.Features;
import model.Model;
import viewGUI.View;

public class AddTestToAppointment extends JFrame {
	JPanel addTestPanel;

	public void addTestToAppointment(Features features, View view, Model model) {

	}

	private void showMessage(String message) {
		JOptionPane.showMessageDialog(addTestPanel, message);
		setVisible(true);
	}

	public void disableDoctorInfoWindow() {
		setVisible(false);
		dispose();
	}

}
