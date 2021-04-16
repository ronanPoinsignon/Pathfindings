package prog.graphique.caseGraphique.etatChemin;

import javafx.scene.paint.Color;
import prog.graphique.caseGraphique.CaseGraphique;

public abstract class EtatChemin {
	
	protected CaseGraphique c;
	
	public EtatChemin(CaseGraphique c) {
		this.c = c;
	}

	public abstract Color getColor();
}
