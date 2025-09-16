package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MioBottone extends JButton {
	/*public static final Color TRASPARENTE = new Color(0, 0, 0, 0);
	public static final Color ARANCIONE = Color.decode("#f08c00");
	
    static {
    	UIManager.put("Button.arc", 20);
	}
    public MioBottone(String text) {
        super(text);
    }*/
	
	public static final Color VERDE_TAVOLO = new Color(2, 86, 14);
    public static final Color VERDE_HOVER = new Color(0, 128, 0);
    public static final Color BORDO_CHIARO = new Color(255, 255, 255);
    public static final Font FONT_GIOCO = new Font("SansSerif", Font.BOLD, 16);
    
    static {
        UIManager.put("Button.arc", 20); // se usi FlatLaf o Look&Feel moderno
    }

    public MioBottone(String text) {
        super(text);
        setFocusPainted(false);
        //setFont(FONT_GIOCO);
        setBackground(VERDE_TAVOLO);
        setForeground(Color.BLACK);
        setBorder(BorderFactory.createLineBorder(BORDO_CHIARO, 2));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        setFont(new Font("Georgia", Font.BOLD, 14));
        setPreferredSize(new Dimension(90, 30));
        setMargin(new Insets(5,5,5,5));

     // Effetto hover
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(VERDE_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(VERDE_TAVOLO);
            }
        });
    }
    
    
}

