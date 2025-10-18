package view;

import javax.swing.*;

import model.GestoreAudio;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MioBottone extends JButton {
	    
    static {
        UIManager.put("Button.arc", 20); // se usi FlatLaf o Look&Feel moderno
    }

    public MioBottone(String text) {
        super(text);
        initBottone();
    }
    
    public MioBottone(Icon icon) {
        super(icon);
        initBottone();
    }
    
    
    private void initBottone() {
    	setFocusPainted(false);
        setOpaque(false);
        setContentAreaFilled(false);
        setForeground(Color.BLACK);
        setBorder(BorderFactory.createLineBorder(View.BORDO_CHIARO, 2));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setFont(View.FONT_GIOCO);
        setPreferredSize(new Dimension(30, 30));
        setMargin(new Insets(5,5,5,5));
        
        resetToDefault();
        
        // Effetto hover
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            	if(isEnabled())
            	{
            		setBackground(View.VERDE_HOVER);
            	}
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(View.VERDE_TAVOLO);
            }
        });

        addActionListener(e -> {
        	GestoreAudio audioManager = GestoreAudio.getInstance();
        	audioManager.playBottone();
        });
    }
    
    public void resetToDefault() {
        setBackground(View.VERDE_TAVOLO);
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

