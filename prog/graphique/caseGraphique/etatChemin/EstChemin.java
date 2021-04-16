package prog.graphique.caseGraphique.etatChemin;

import javafx.scene.paint.Color;
import prog.graphique.caseGraphique.CaseGraphique;

public class EstChemin extends EtatChemin {
	
	public EstChemin(CaseGraphique c) {
		super(c);
	}

	@Override
	public Color getColor() {
		return Color.ROYALBLUE;
	}

}
