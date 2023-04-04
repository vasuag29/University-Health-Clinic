package controller;

import javax.swing.*;

import model.Model;
import viewGUI.View;
import controller.Features;

import viewGUI.newAppointment.NewAppointment;

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
		view.setupForNewAppointment(features);
	}

	@Override
	public void showOriginalMenu() {
		view.showMenu();
	}
}
