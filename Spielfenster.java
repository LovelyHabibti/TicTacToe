package de.stoeckel.tictactoe;

/**
 *  Spielfenster.java
 *  Klasse, die die Benutzeroberfläche Spielfeld als 3x3 Buttons anlegt
 *  und auf Button-Clicks reagiert, um Spielzüge auszuführen
 */

import java.awt.*;
import java.awt.event.*;

public class Spielfenster extends Panel implements ActionListener {

	public static char SPIELER_X = 'X';
    public static char SPIELER_O = 'O';
    public TicTacToe spiel_ttt;
    private Button[] button;
    private Font schriftart;

    /**
     * Konstruktor
     * 
     * @param spiel_ttt
     */
	public Spielfenster(TicTacToe spiel_ttt) {
		// Aufruf von Super-Konstruktor mit LayoutManager als Argument
    	super(new GridLayout(3,3));

		// Initialisierungen von Instanzvariablen
		this.spiel_ttt = spiel_ttt;
    	schriftart = new Font("Helvetica", Font.BOLD, 20);

		// Buttons fürs Spielfeld erzeugen
    	button = new Button[9];
    	
    	// Initialisieren der Buttons
    	for (int i = 0; i < 9; i++) {
    		// Erzeugen der Buttons und Festlegen der Schriftart
    		button[i] = new Button("");
    		button[i].setFont(schriftart);

			// Ereignisbehandlung 
    		button[i].setActionCommand(i + "");
    		button[i].addActionListener(this);
    		
    		// Hinzufügen des Buttons zu Panel
    		add(button[i]);	
    	}	
	}

	/**
     * Methode fürs Setzen der Felder durch Spieler 'spieler' 
     * 
     * @param i 
     * @param spieler 
     */
    public void feldSetzen(int i, int spieler) {
    	// markieren mit "X", "O" oder ""
    	if (spieler == spiel_ttt.SPIELER_X ) {
    		button[i].setLabel("X");
    	} else if (spieler == spiel_ttt.SPIELER_O) {
    		button[i].setLabel("O");
    	} else if (spieler == spiel_ttt.LEER) {
    		button[i].setLabel("");
    	}
    }
    
	/**
     * Ereignisbehandlung für Klicks auf Buttons
     * 
     * @param event 
     */
    public void actionPerformed(ActionEvent event) {
    	// Zug ausführen mit spiel_ttt.zugDurchfuehren(i), wenn Feld-Button i geklickt wurde	
    	if (event.getSource() instanceof Button) {
    		spiel_ttt.zugDurchfuehren(Integer.parseInt(event.getActionCommand()));
    	}
    }
}
