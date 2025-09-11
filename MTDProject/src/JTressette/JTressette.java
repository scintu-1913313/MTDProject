package JTressette;

import controller.Controller;
import model.GiocoJTressette;
import view.View;

public class JTressette {

	public static void main(String[] args) {
		new Controller(new GiocoJTressette(), new View());
	}

}
