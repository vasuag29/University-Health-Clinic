import controller.Control;
import viewGUI.MainView;
import model.Model;
import model.MainModel;
import viewGUI.View;

public class Starter {
	public static void main(String[] args) {
		View view = new MainView("University Healthcare");
		Model model = new MainModel();
		Control controller = new Control(model);
		controller.setView(view);
	}
}
