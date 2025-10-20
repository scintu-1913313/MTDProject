package JTressette;

import controller.Controller;
import model.Model;
import view.View;

public class JTressette{

	public static void main(String[] args) {
		System.out.println("MAIN");
		View view = new View();
		Controller c = new Controller(new Model(), view);
		view.setController(c);
		c.start();
	}
}
