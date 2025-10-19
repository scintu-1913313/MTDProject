package view;

import javax.swing.*;
import java.awt.*;

public abstract class MioDialog extends JDialog {

    public MioDialog(Frame owner, String titolo,int larghezza,int altezza) {
        super(owner, titolo, true); // true = modalità bloccante
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(larghezza, altezza);
        setLocationRelativeTo(owner);
        setResizable(false);

        // Imposta lo stile coerente con PannelloMenu
        getContentPane().setBackground(Pannello.VERDE_PANNELLO);
        getContentPane().setLayout(new BorderLayout());

        // Bottone di chiusura
        MioBottone bottoneOK = new MioBottone("OK");
        bottoneOK.setPreferredSize(new Dimension(100, 40));
        bottoneOK.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottoneOK.addActionListener(e -> dispose());

        JPanel pannelloBottoni = new JPanel();
        pannelloBottoni.setBackground(Pannello.VERDE_PANNELLO);
        pannelloBottoni.setLayout(new FlowLayout(FlowLayout.CENTER));
        pannelloBottoni.add(bottoneOK);

        getContentPane().add(pannelloBottoni, BorderLayout.SOUTH);
    }
}
