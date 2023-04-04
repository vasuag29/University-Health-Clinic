package viewGUI;

import java.awt.event.ActionListener;

import javax.swing.*;

import controller.Features;
import viewGUI.newAppointment.NewAppointment;

public class MainView extends JFrame implements View {
	NewAppointment appointment = new NewAppointment();
	JButton newAppointment = new JButton("Create New Appointment");
	JPanel panel;
	public MainView(String caption) {
		super(caption);
		setSize(700, 300);
		setLocation(200, 200);
		panel = new JPanel();
		panel.add(newAppointment);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(panel);
		setVisible(true);
		setResizable(false);
	}

	@Override
	public void addFeatures(Features features) {
		newAppointment.addActionListener(e -> features.makeNewAppointment(features));
	}

	@Override
	public void setupForNewAppointment(Features features) {
		setVisible(false);
		appointment.showNewAppointment(features);
	}

	@Override
	public void showMenu() {
		appointment.disableNewAppointmentWindow();
		setVisible(true);
	}
}
