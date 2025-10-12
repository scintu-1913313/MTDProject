package JTressette;

import controller.Controller;
import model.Model;
import view.View;

public class JTressette {

	public static void main(String[] args) {
		System.out.println("MAIN");
		Controller c = new Controller(new Model(), new View());
		c.start();
	}
	

}
