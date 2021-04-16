package prog.graphique.caseGraphique.etatChemin;

import javafx.scene.paint.Color;
import prog.graphique.caseGraphique.CaseGraphique;

public class EtatCalcule extends EtatChemin {

	public EtatCalcule(CaseGraphique c) {
		super(c);
	}

	@Override
	public Color getColor() {
		return Color.LIGHTGREEN;
	}

}
