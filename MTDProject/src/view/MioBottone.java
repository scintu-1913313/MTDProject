package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MioBottone extends JButton {
	public static final Color TRASPARENTE = new Color(0, 0, 0, 0);
	public static final Color ARANCIONE = Color.decode("#f08c00");
	
    static {
    	UIManager.put("Button.arc", 20);
	}
    public MioBottone(String text) {
        super(text);
    }
}
