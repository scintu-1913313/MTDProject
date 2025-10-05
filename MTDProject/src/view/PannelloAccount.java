package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;

public class PannelloAccount extends Pannello{
	
	public PannelloAccount(View view) {
		super(new BorderLayout());
		//setBounds(50, 50, 600, 400);
		setBackground(Pannello.VERDE_PANNELLO);
		setOpaque(false);
		
		// Bottone in basso a destra
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		topPanel.setBackground(TRASPARENTE);
				
		MioBottone bottoneRitornoMenu = new MioBottone("Menu'");
		bottoneRitornoMenu.setPreferredSize(new Dimension(140, 40));
		bottoneRitornoMenu.setMaximumSize(new Dimension(140, 40));
		bottoneRitornoMenu.addActionListener(e -> view.showPannelloMenu());
		
		topPanel.add(bottoneRitornoMenu);
		add(topPanel, BorderLayout.SOUTH);


	}
}
