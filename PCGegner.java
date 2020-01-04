package de.stoeckel.tictactoe;

/**
 * PCGegner.java
 * Einfacher Computerspieler zu Tic Tac Toe mit kleiner Intelligenz:
 * Computer gewinnt immer, wenn er nur noch einen Zug machen muss.
 * Wenn der Gegner noch einen Zug zum Gewinnen braucht, macht der
 * Computer diesen Zug, um den Gegner zu blockieren.
 * Sonst wählt er einen zufälligen Zug.
 */ 

public class PCGegner {

	public PCGegner() {
		
	}
	
	/**
	 * Methode gibt Zug (Feld) zum Sieg zurück oder -1 falls kein Sieg im 1 Zug möglich war
     * @param spiel_ttt 
     * @param spielerAktuell 
     * @return
     */
    public int inEinemZugZumSieg(TicTacToe spiel_ttt, int spielerAktuell) {
		for (int i = 0; i < 9; i++) {	
			if (spiel_ttt.spielfeld[i] == spiel_ttt.LEER) {
				// wenn Feld leer ist, mache einen Zug darauf und teste, ob gewonnen 
				spiel_ttt.spielfeld[i] = spielerAktuell;
				
				if (spiel_ttt.spielPruefen() == spielerAktuell) {
					spiel_ttt.spielfeld[i] = spiel_ttt.LEER; 
					return i;
				}
				spiel_ttt.spielfeld[i] = spiel_ttt.LEER;
			}
		}
		return -1;
    }

    /**
     * Methode teste, welcher Zug der beste ist
     * 
     * @param spiel_ttt 
     * @return
     */
    public int derBesteZug(TicTacToe spiel_ttt) {
    	int feld;
    	
		// Testen, ob Computer in einem Zug gewinnen kann
		if ((feld = inEinemZugZumSieg(spiel_ttt, spiel_ttt.getSpielerAktuell())) != -1) {
			return feld;
		}
		
		// Testen, ob Gegner in einem Zug gewinnen kann (und ziehe ggf. dorthin)
		if ((feld = inEinemZugZumSieg(spiel_ttt, spiel_ttt.getGegner(spiel_ttt.getSpielerAktuell()))) != -1) {
			return feld;
		}

		// wenn Computer nicht in einem Zug gewinnen kann, mache einen Zufallszug
		do { 
			feld = (int) (Math.random() * 9); // 0.0 <= Math.random() < 1		
		} while (spiel_ttt.spielfeld[feld] != spiel_ttt.LEER);
		
		return feld;
    }

}
