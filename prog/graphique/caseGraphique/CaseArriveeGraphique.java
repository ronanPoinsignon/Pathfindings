package prog.graphique.caseGraphique;

import javafx.scene.paint.Color;
import prog.graphique.caseGraphique.etatChemin.EstPasChemin;

public class CaseArriveeGraphique extends EmptyCaseGraphique {

	public CaseArriveeGraphique() {
		this.bg = Color.RED;
		this.etat = new EstPasChemin(this);
		this.setFill(this.getShownColor());
	}
	
	@Override
	public Color getShownColor() {
		return this.getBaseColor();
	}
	
}
