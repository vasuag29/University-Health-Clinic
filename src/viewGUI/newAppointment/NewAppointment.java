package viewGUI.newAppointment;

import controller.Features;
import javax.swing.*;

public class NewAppointment extends JFrame {
	JPanel newAppointmentPanel;
	JTextField firstName = new JTextField(10);
	JTextField lastName = new JTextField(10);
	JLabel firstNameLabel = new JLabel("First Name");
	JLabel lastNameLabel = new JLabel("Last Name");
	JButton createAppointment = new JButton("Create Appointment");
	JButton back = new JButton("Back");


	public void showNewAppointment(Features features) {
		newAppointmentPanel = new JPanel();
		setSize(500, 300);
		setLocation(200, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		newAppointmentPanel.add(firstNameLabel);
		newAppointmentPanel.add(firstName);
		newAppointmentPanel.add(lastNameLabel);
		newAppointmentPanel.add(lastName);

		newAppointmentPanel.add(createAppointment);
		newAppointmentPanel.add(back);

		add(newAppointmentPanel);
		setVisible(true);

		back.addActionListener(e -> features.showOriginalMenu());
	}

	public void disableNewAppointmentWindow() {
		setVisible(false);
		dispose();
	}
}
