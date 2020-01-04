package de.stoeckel.tictactoe;

/**
 * Dialogbox.java
 * Klasse, die eine Dialogbox mit einem vorgegebenen Titel, einem Meldungstext 
 * und einem Button mit vorgegebenem Labeltext darstellt.
 * Dialog schließt sich bei Klick auf den Button.
 */ 

import java.awt.*;
import java.awt.event.*;

class Dialogbox extends Dialog implements ActionListener {

	private Button ok;
    private Label ergebnis;

    /**
     * Konstruktor
     * Dialog gehört zum Frame Inhaber (wichtig wegen Fokus)
     * titel = Fenstertitel, dialogModal = true bei modalem Dialog
     * test = Meldungstext, buttonLabel = Beschriftung des ok-Buttons
     * 		
     * @param inhaber 
     * @param titel 
     * @param dialogModal 
     * @param text 
     * @param buttonLabel
     */
    Dialogbox(Frame inhaber, String titel, boolean dialogModal, String text, String buttonLabel) {
    	
		// Super-Konstruktor aufrufen
		super(inhaber, titel, dialogModal);
		
		// GUI-Elemente erzeugen
		ok = new Button(buttonLabel);
		ergebnis = new Label(text);
		
		// GUI-Elemente zum Dialog hinzufügen
		add(ergebnis, BorderLayout.NORTH);
		add(ok, BorderLayout.SOUTH);
		
		// Größe des Dialoges festlegen
		this.setSize(300, 120);
		
		// Ereignisbehandlung für ok-Button installieren
		ok.addActionListener(this);
    }

    /**
     * Ereignisbehandlung: Ausblenden des Dialogs und Freigabe der Ressourcen; 
     * bei Klick auf OK-Button
     * 
     * @param event 
     * @return
     */
    public void actionPerformed(ActionEvent event) {
		if (event.getSource() == ok) {
			dispose();
		}
    }

}
