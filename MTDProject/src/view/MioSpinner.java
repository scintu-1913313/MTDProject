package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MioSpinner extends JPanel {

    // Editor personalizzato con frecce orizzontali
	private final JButton leftButton;
    private final JButton rightButton;
    private final JTextField textField;
    
    public MioSpinner(){
    		
            setLayout(new BorderLayout(5, 0));
            setBackground(new Color(0, 0, 0, 0));

            textField = new JTextField("1", 4);
            textField.setHorizontalAlignment(JTextField.CENTER);
            textField.setFont(new Font("SansSerif", Font.BOLD, 14));
            textField.setBackground(new Color(240, 240, 255));
            textField.setBorder(null);
            textField.setEditable(false);

            leftButton = new JButton("<");
            rightButton = new JButton(">");

            leftButton.addActionListener((ActionEvent e) -> textField.setText("ciao"));
            rightButton.addActionListener((ActionEvent e) -> textField.setText("no ciao"));

            add(leftButton, BorderLayout.WEST);
            add(textField, BorderLayout.CENTER);
            add(rightButton, BorderLayout.EAST);
        }
    }

