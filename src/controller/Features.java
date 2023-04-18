package controller;


public interface Features {
	void makeNewAppointment(Features features);
	void showOriginalMenu();

	void getAppointmentInfo();

	void connectToDB();

	void showAppointmentsChart(Features features);

	void showStudentAppointments(Features features);

	void cancelAppointment();
}
