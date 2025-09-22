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
    public static final Color VERDE_HOVER = new Color(70, 180, 70);
    public static final Color BORDO_CHIARO = new Color(255, 255, 255);
    public static final Font FONT_GIOCO = new Font("Georgia", Font.BOLD, 14);
    
    static {
        UIManager.put("Button.arc", 20); // se usi FlatLaf o Look&Feel moderno
    }

    public MioBottone(String text) {
        super(text);
        setFocusPainted(false);
        setOpaque(false);
        setContentAreaFilled(false);
        setBackground(VERDE_TAVOLO);
        setForeground(Color.BLACK);
        setBorder(BorderFactory.createLineBorder(BORDO_CHIARO, 2));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setFont(FONT_GIOCO);
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
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
        g2.setClip(new java.awt.geom.RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 30, 30));
        super.paintComponent(g);
        g2.dispose();
    }
    
    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getForeground());
        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 30,30);
        g2.dispose();
    }
}

