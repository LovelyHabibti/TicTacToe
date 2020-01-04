package de.stoeckel.tictactoe;

/**
 * Spielstand.java
 * Klasse zum Laden und Speichern von Spielständen
 */ 

import java.awt.*; // FileDialog
import java.io.*;  // FileInputStream, ...
 
public class Spielstand {

	public Spielstand() {
	}

	/**
	 * Methode zum Laden eines Spielstandes
	 * 
	 * @param spiel_ttt
	 */
	public static void spielLaden(TicTacToe spiel_ttt) {
		// Spiel laden
		File file;
		// erzeugen und anzeigen des FileDialogs zum Laden
		FileDialog fd = new FileDialog(spiel_ttt, "Spiel laden ...", FileDialog.LOAD);
		fd.show();
		
		try {
			// FileInputStream, BufferedInputStream zur Datei f.getPath() erzeugen
			if (fd.getFile() == null) {
				return; // Keine Datei ausgewählt 
			}
			else {
				file = new File(fd.getDirectory(), fd.getFile());
			}
			FileInputStream input = new FileInputStream(file.getPath());
			BufferedInputStream bufferedI = new BufferedInputStream(input);
			
			
			// Einlesen des Spielstands in String spielstand 
			String spielstand = new String("");
			byte teil[] = new byte[256];
			int anzahlBytes = 0;
			while (bufferedI.available() > 0) {
				anzahlBytes = bufferedI.read(teil);
				spielstand += new String(teil, 0, anzahlBytes);
			}
				
			// Spielfeld entsprechend des geladenen Spielstandes vorbereiten
			// Initialisieren als neues Spiel
			spiel_ttt.neuesSpiel();
			
			// Felder mit spiel_ttt.spielfenster.feldSetzen(...) auf "X", "O" oder "" setzen
			int anzahlX = 0;
			int anzahlO = 0;
			for (int feld = 0; feld < 9; feld++) {
				if (spielstand.charAt(feld) == 'X') {
					spiel_ttt.spielfeld[feld] = spiel_ttt.SPIELER_X;
					spiel_ttt.spielfenster.feldSetzen(feld, spiel_ttt.SPIELER_X);
					anzahlX++;
				} else 
					if (spielstand.charAt(feld) == 'O') {
					spiel_ttt.spielfeld[feld] = spiel_ttt.SPIELER_O;
					spiel_ttt.spielfenster.feldSetzen(feld, spiel_ttt.SPIELER_O);
					anzahlO++;
				} else {
					spiel_ttt.spielfeld[feld] = spiel_ttt.LEER;
					spiel_ttt.spielfenster.feldSetzen(feld, spiel_ttt.LEER);
				}
			}
			
			// spiel_ttt.anzahlZuege aktualisieren
			spiel_ttt.anzahlZuege = anzahlX + anzahlO;
			
			// spiel_ttt.spielerAktuell aktualisieren
			if (anzahlO > anzahlX) {
				spiel_ttt.spielerAktuell = spiel_ttt.SPIELER_X;
			}
			else 
				if (anzahlO < anzahlX) {
					spiel_ttt.spielerAktuell = spiel_ttt.SPIELER_O;
				}
			
			// prüfen, ob Spiel schon fertig ist und dann verhindern, dass ein fertiges Spiel 
			// weitergespielt werden kann
			if (spiel_ttt.spielPruefen() != spiel_ttt.UNKLAR) {				
				spiel_ttt.status = spiel_ttt.FERTIG;
			}
		} catch (IOException ioEvent) {
			// Fehlermeldung als Dialogbox anzeigen
			Dialogbox dateifehler = new Dialogbox(spiel_ttt, "Dateifehler", true , ioEvent.toString(), "OK");
			dateifehler.setSize(400, 120);
			dateifehler.show();
		}
	}
	
	/**
	 * Methode zum Speichern eines Spielstandes
	 * 
	 * @param spiel_ttt
	 */
	public void spielSpeichern(TicTacToe spiel_ttt) {
		// Spiel speichern
		File file;
		// erzeugen und anzeigen des FileDialogs zum Speichern
		FileDialog fd = new FileDialog(spiel_ttt, "Spiel speichern ...", FileDialog.SAVE);
		fd.show();
			
		if (fd.getFile() == null) {
			return; // Keine Datei ausgewählt 
		}
		else {
			file = new File(fd.getDirectory(), fd.getFile());			
		}
		
		// Codieren des Spielstandes der Felder 0 bis 8 im String spielstand 
		// z.B. spielstand=="XXOOXX O ", wenn ein X oben links steht und in unterer
		// Zeile noch links und rechts außen ein freies Feld ist
		String spielstand = new String("");
		for (int feld = 0; feld < 9; feld++) {
			if (spiel_ttt.spielfeld[feld] == spiel_ttt.SPIELER_X) {
				spielstand += new String("X");
			} else 
				if (spiel_ttt.spielfeld[feld] == spiel_ttt.SPIELER_O) {
				spielstand += new String("O");
			} else {
				spielstand += new String(" ");
			}
		}
			
		try {
			// FileOutputStream, BufferedOutputStream zur Datei f.getPath() erzeugen
			FileOutputStream output = new FileOutputStream(file.getPath());
			BufferedOutputStream bufferedO = new BufferedOutputStream(output);
			
			// String spielstand speichern und File schließen
			bufferedO.write(spielstand.getBytes());
			bufferedO.flush();
			bufferedO.close();
		} catch (IOException ioEvent) {
			// Fehlermeldung als Dialogbox anzeigen
			Dialogbox dateifehler = new Dialogbox(spiel_ttt, "Dateifehler", true, ioEvent.toString(), "OK");
			dateifehler.setSize(400, 120);
			dateifehler.show();
		}
	}
}
