package Main;

import carte.*;

public class JTressette {

	public static void main(String[] args) {
		
		//Prova mazzo
		Mazzo mazzo = new Mazzo.MazzoBuilder().generaCarte().mescola().build();
		mazzo.getCarte().forEach(System.out::println);
	}

}
