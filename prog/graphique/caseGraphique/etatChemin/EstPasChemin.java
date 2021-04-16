package prog.graphique.caseGraphique.etatChemin;

import javafx.scene.paint.Color;
import prog.graphique.caseGraphique.CaseGraphique;

public class EstPasChemin extends EtatChemin {
	
	public EstPasChemin(CaseGraphique c) {
		super(c);
	}

	@Override
	public Color getColor() {
		return c.getBaseColor();
	}

}
