package de.stoeckel.tictactoe;

/** TicTacToe.java
 *  Hauptklasse für Tic Tac Toe mit Java AWT
 */

import java.awt.*;
import java.awt.event.*;

public class TicTacToe extends Frame implements ActionListener {

	// Deklaration der MenuItems 
    private MenuItem neu, laden, speichern, beenden, ueber, spielhilfe;
    // Deklaration des Buttons "Neues Spiel"
    private Button neuesSpiel;

    // Deklaration einer Dropdown-Liste für Spielerauswahl
    private Choice spielerWahl;

    // Nummerierung der Felder von links oben nach rechts unten mit 0 bis 8 
    // Reihenfolge der Spielfelder (Arrayindizes)
 	// |0|1|2|
 	// |3|4|5|
 	// |6|7|8|
    public int[] spielfeld;

    // Codierung der Spielfeldeinträge
    public static final int SPIELER_X = 10;
    public static final int SPIELER_O = 1;
    public static final int LEER = 0;
    public static final int UNENTSCHIEDEN = -1;
    public static final int UNKLAR = 0;
    public int spielerAktuell, pcSpieler;
    public int anzahlZuege;
    
    public static final int BEREIT = 0;
    public static final int FERTIG = 1;
    public int status;
    
    public Spielfenster spielfenster;
    
    /**
     * Konstruktor
     * 
     * @param s
     */
    public TicTacToe(String s) {
    	// Aufruf von Super-Konstruktor 
    	super(s);
		// Initialisierungen von Instanzvariablen
		spielfeld = new int[9];
		
		// GUI aufbauen - LayoutManager definieren
		setLayout(new BorderLayout());
		
		// Menu definieren
		// MenuBar erzeugen, Menus und MenuItems erzeugen
		MenuBar menubar = new MenuBar();
		Menu spiel = new Menu("Spiel");
		neu       = new MenuItem("Neu");
		laden     = new MenuItem("Laden ...");
		speichern = new MenuItem("Speichern ...");
		beenden   = new MenuItem("Beenden");
		
		Menu hilfe = new Menu("Hilfe");
		spielhilfe = new MenuItem("Wie funktioniert es?");
		ueber = new MenuItem("Über ...");

		// Ereignisbehandlung hinzufügen zu MenuItems
		neu.addActionListener(this);
		laden.addActionListener(this);
		neu.addActionListener(this);
		speichern.addActionListener(this);
		beenden.addActionListener(this);
		ueber.addActionListener(this);
		spielhilfe.addActionListener(this);
				
		// Hinzufügen der Menupunkte zu Menus 
		spiel.add(neu);
		spiel.addSeparator();
		spiel.add(laden);
		spiel.add(speichern);
		spiel.addSeparator();
		spiel.add(beenden);
		
		hilfe.add(spielhilfe);
		hilfe.add(ueber);
		
		menubar.add(spiel);
		menubar.add(hilfe);
		setMenuBar(menubar);
		
		// Board definieren
		spielfenster = new Spielfenster(this);
		add(spielfenster, BorderLayout.CENTER);
		
		// DropDown-Liste, Neues-Spiel-Button definieren
		Panel panel = new Panel(new BorderLayout());
		neuesSpiel = new Button("Neues Spiel");
		
		// Ereignisbehandlung für Button "Neues Spiel"
		neuesSpiel.addActionListener(this);
		panel.add(neuesSpiel, BorderLayout.EAST);
		
		// DropDown-Liste füllen
		spielerWahl = new Choice();
		spielerWahl.add("X beginnt");
		spielerWahl.add("O beginnt");
		spielerWahl.add("X beginnt gegen Computer");
		spielerWahl.add("O beginnt gegen Computer");
		panel.add(spielerWahl, BorderLayout.WEST);
		add(panel, BorderLayout.SOUTH);

		// Fenster schließen
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
    }
		
    /**
     * Methode erstellt Fenster und die Größe wird gesetzt
     * 
     * @param args[] 
     */	
	public static void main(String[] args) {
		TicTacToe spiel_ttt = new TicTacToe("Tic Tac Toe");
		spiel_ttt.setSize(500, 550);
		spiel_ttt.setVisible(true);
		spiel_ttt.neuesSpiel();
	}

	/**
	 * Methode gibt aktuellen Spieler zurück
	 * 
	 * @return
	 */
    public int getSpielerAktuell() {
        return spielerAktuell;
    }

    /**
     * Methode gibt den Gegner zum aktuellen Spieler zurück
     * @param spielerAktuell 
     * @return
     */
    public int getGegner(int spielerAktuell) {
        if (spielerAktuell == SPIELER_X) 
        	return SPIELER_O;
        else
        	return SPIELER_X;
    }

    /**
     * Initialisierungen für neues Spiel 
     * 
     */
    public void neuesSpiel() {
    	pcSpieler = 0; // kein pcSpieler als Standard
		anzahlZuege = 0;
		spielfeldLoeschen();
		
		// Auslesen des Startspielers aus DropDown
		int spielerAktuellIndex = spielerWahl.getSelectedIndex();
		if (spielerAktuellIndex == 0) { 
			spielerAktuell = SPIELER_X; 
		} else if (spielerAktuellIndex == 1) { 
			spielerAktuell = SPIELER_O; 
		} else if (spielerAktuellIndex == 2) { 
			spielerAktuell = SPIELER_X; 
			pcSpieler = SPIELER_O; 
		} else if (spielerAktuellIndex == 3) { 
			spielerAktuell = SPIELER_O; 
			pcSpieler = SPIELER_X; 
		}
		
		status = BEREIT;
    }

    /**
     * Löschen des gesamten Spielfeldes inkl. GUI
     * 
     */
    public void spielfeldLoeschen() {
    	for (int i = 0; i < 9; i++) {
        	spielfeld[i] = 0;
        	spielfenster.feldSetzen(i, LEER);
        }
    }

    /**
     * Methode gibt Gewinner SPIELER_X  bzw. SPIELER_O zurück 
     * oder UNENTSCHIEDEN, falls UNENTSCHIEDEN ist
     * oder UNKLAR, falls Spiel noch nicht beendet ist
     * 
     * @return
     */
    public int spielPruefen() {
    	int summe;
		
		// Prüfen, ob es 3 gleiche Symbole in einer Zeile gibt
		for (int i = 0; i <= 6; i = i + 3) {
			summe = spielfeld[i] + spielfeld[i+1] + spielfeld[i+2];
			if ((summe == 3) | (summe == 30)) {
				return summe/3;
			}
		}
		
		// Prüfen, ob es 3 gleiche Symbole in einer Spalte gibt	
		for (int j = 0; j < 3; j++) {
			summe = spielfeld[j] + spielfeld[j+3] + spielfeld[j+6];
			if ((summe == 3) | (summe == 30)) {
				return summe/3;
			}
		}

		// Prüfen, ob es 3 gleiche Symbole in einer Diagonale gibt	
		summe = spielfeld[0] + spielfeld[4] + spielfeld[8];
		if ((summe == 3) | (summe == 30)) {
			return summe/3;
		}
		summe = spielfeld[2] + spielfeld[4] + spielfeld[6];
		if ((summe == 3) | (summe == 30)) {
			return summe/3;
		}
		
		// Prüfen, ob unentschieden oder ob das Spiel noch nicht beendet ist
		if (anzahlZuege == 9) {
			return UNENTSCHIEDEN;
		} else {
			return UNKLAR;
		}
    }

    /**
     * Methode führt Zug auf Feld 'feld' aus und gibt 'true' bei Erfolg zurück.
     * Falls Zug nicht ausgeführt werden kann, wird 'false' zurückgegeben.
     * 
     * @param feld 
     * @return
     */
    public boolean zugDurchfuehren(int feld) {
    	
		if (spielfeld[feld] == LEER && status != FERTIG) {
			// Zug erlaubt, deshalb ausführen
			spielfeld[feld] = spielerAktuell;
			spielfenster.feldSetzen(feld, spielerAktuell);
			anzahlZuege++;
			
			// anschließend ist Gegner an der Reihe
			spielerAktuell = getGegner(spielerAktuell); 
			
			// Überprüfen, ob das Spiel fertig ist und wer vielleicht der Sieger ist
			int ergebnis;
			if ((ergebnis = spielPruefen()) != UNKLAR)	{
				String text;
				status = FERTIG;
					
				switch (ergebnis) {
					case SPIELER_X: 
						text = new String("Spieler X hat gewonnen!"); 
						break;
					case SPIELER_O: 
						text = new String("Spieler O hat gewonnen!"); 
						break;
					default: 
						text = new String("Unentschieden!"); 
						break;
				}		
				
				// Meldung über Ergebnis als Dialogbox anzeigen
				Dialogbox sieger = new Dialogbox(this, "Spielende", true, text, "OK");
				sieger.setVisible(true);
				
				return false;
			}
			
			// Erweiterung für Computergegner
			if (spielerAktuell == pcSpieler) {
				PCGegner pcg = new PCGegner();
				zugDurchfuehren(pcg.derBesteZug(this));
			}
		
			return true;
		}
		
		return false;
    }

    /**
     * Methode behandelt Menu- und Buttonereignisse
     * @param event 
     */
    public void actionPerformed(ActionEvent event) {
    	
		if ((event.getSource() == neu) | (event.getSource() == neuesSpiel)) { 
			neuesSpiel(); 
		}
		
		if (event.getSource() == laden) { 
			Spielstand ls = new Spielstand();
			ls.spielLaden(this);
		}
		
		if (event.getSource() == speichern) { 
			Spielstand ls = new Spielstand();
			ls.spielSpeichern(this);	
		}
		
		if (event.getSource() == beenden) { 
			System.exit(0); 
		}
		
		if (event.getSource() == spielhilfe) { 
			// Spielhilfe-Dialog anzeigen
			Dialogbox dialogHilfe = new Dialogbox(this, "Hilfe", true, "Abwechselnd klickt jeder Spieler auf ein Feld und versucht "
					+ "drei gleiche Symbole horizontal, vertikal oder diagonal zu erreichen. Viel Spaß.", "OK");
			// Größe des Dialoges festlegen
			dialogHilfe.setSize(780, 120);
			dialogHilfe.setVisible(true);
		}
		
		if (event.getSource() == ueber) { 
			// Über-Dialog anzeigen
			Dialogbox dialogUeber = new Dialogbox(this, "Über Tic Tac Toe ...", true, "(c) 2019", "OK");
			dialogUeber.setVisible(true);
		}
		
		
    }
}
