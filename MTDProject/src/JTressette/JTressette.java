package JTressette;

import controller.Controller;
import model.GiocoJTressette;
import view.View;

public class JTressette {

	public static void main(String[] args) {
		System.out.println("MAIN");
		Controller c = new Controller(new GiocoJTressette(), new View());
		c.start();
	}
	

}
