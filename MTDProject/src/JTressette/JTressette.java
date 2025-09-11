package JTressette;

import JTressette.controller.Controller;
import JTressette.model.GiocoJTressette;
import JTressette.view.View;

public class JTressette {

	public static void main(String[] args) {
		new Controller(new GiocoJTressette(), new View());
	}

}
