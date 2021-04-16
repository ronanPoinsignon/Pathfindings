package prog.graphique.caseGraphique.etatChemin;

import javafx.scene.paint.Color;
import prog.graphique.caseGraphique.CaseGraphique;

public class EtatVisite extends EtatChemin {

	public EtatVisite(CaseGraphique c) {
		super(c);
	}

	@Override
	public Color getColor() {
		return Color.INDIANRED;
	}

}
