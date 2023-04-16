package controller;

import java.util.List;

import model.DataTypes.Specialization;
import model.Model;
import viewGUI.View;


public class Control implements Features {
	private Model model;
	private View view;

	public Control(Model model) {
		this.model = model;
	}

	public void setView(View v) {
		view = v;
		//provide view with all the callbacks
		view.addFeatures(this);
	}

	@Override
	public void makeNewAppointment(Features features) {
		List<Specialization> specializations = model.getSpecializationList();
		view.setupForNewAppointment(features, specializations);
	}

	@Override
	public void showOriginalMenu() {
		view.showMenu();
	}

	@Override
	public void getAppointmentInfo() {
		view.createNewAppointment();
	}

	@Override
	public void connectToDB() {
		try {
			String username = view.getUsername();
			char[] password = view.getPassword();
			model.openDbConnection("jdbc:mysql://localhost:3306/university_health_clinic",
							username, String.valueOf(password));
			view.showMenu();
		} catch (Exception e) {
			view.showErrorMessage(e.getMessage());
		}
	}

	@Override
	public void showAppointmentsChart(Features features) {
		view.setupForShowAppointments(features);
	}
}
