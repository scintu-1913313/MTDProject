package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JTextField;

public class MioIntSpinner extends MioSpinner {

	// Editor personalizzato con frecce orizzontali
    private final JTextField textField;
    private int valoreCorrente;
    private int minimo;
    private int massimo;
    private int step;

public MioIntSpinner(String titolo, int init, int min, int max, int step){
		super(titolo);
		this.valoreCorrente = init;
		this.minimo = min;
		this.massimo = max;
		this.step = step;

        textField = new JTextField(String.valueOf(this.valoreCorrente));
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setFont(new Font("SansSerif", Font.BOLD, 14));
        textField.setBackground(new Color(240, 240, 255));
        textField.setBorder(null);
        textField.setEditable(false);

        leftButton.addActionListener((ActionEvent e) -> decrementaValore());
        rightButton.addActionListener((ActionEvent e) -> incrementaValore());
        
        leftButton.setEnabled(false);
        add(textField, BorderLayout.CENTER);
    }

	private void decrementaValore() {
		rightButton.setEnabled(true);
		if(valoreCorrente > minimo)
		{
			valoreCorrente-=step;
			textField.setText(String.valueOf(this.valoreCorrente));
		}
		if(valoreCorrente == minimo)
		{
			leftButton.setEnabled(false);
			leftButton.setBackground(View.VERDE_TAVOLO);
		}
	}

	private void incrementaValore() {
		leftButton.setEnabled(true);

		if(valoreCorrente < massimo)
		{
			valoreCorrente+=step;
			textField.setText(String.valueOf(this.valoreCorrente));
		}
		if(valoreCorrente == massimo)
		{
			rightButton.setEnabled(false);
			rightButton.setBackground(View.VERDE_TAVOLO);
		}
	}
	
	public int getValoreCorrente() {
		return this.valoreCorrente;
	}
}

