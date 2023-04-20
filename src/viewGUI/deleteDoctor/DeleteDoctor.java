package viewGUI.deleteDoctor;

import javax.swing.*;

import controller.Features;
import model.Model;
import viewGUI.View;

public class DeleteDoctor extends JFrame {
	JButton delete;
	JTextField doctorId;
	JButton back;
	JPanel deleteDoctorPanel;
	JLabel doctorLabel;

	public void deleteDoctorInfo(Features features, Model model, View view) {
		delete = new JButton("Delete");
		doctorId = new JTextField(10);
		back = new JButton("Back to Main Menu");
		doctorLabel = new JLabel("Doctor Id");
		deleteDoctorPanel = new JPanel();
		deleteDoctorPanel.add(doctorLabel);
		deleteDoctorPanel.add(doctorId);
		deleteDoctorPanel.add(delete);
		deleteDoctorPanel.add(back);
		add(deleteDoctorPanel);
		setSize(320, 500);
		setLocation(200, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		back.addActionListener(e -> view.showMenu());
		delete.addActionListener(e -> deleteDoctorFromDb(view, model));
	}

	private void deleteDoctorFromDb(View view, Model model) {
		try {
			String doctor = doctorId.getText();
			if (model.deleteDoctor(doctor)) {
				showMessage("Doctor Information Deleted Successfully!");
				view.showMenu();
			}
		} catch (Exception e) {
			showMessage(e.getMessage());
			view.showMenu();
		}
	}

	private void showMessage(String message) {
		JOptionPane.showMessageDialog(deleteDoctorPanel, message);
		setVisible(true);
	}

	public void disableDeleteDoctorWindow() {
		setVisible(false);
		dispose();
	}
}
