package prog.graphique.caseGraphique;

import javafx.scene.paint.Color;
import prog.graphique.caseGraphique.etatChemin.EstPasChemin;

public class CaseDepartGraphique extends EmptyCaseGraphique {

	public CaseDepartGraphique() {
		this.bg = Color.DEEPSKYBLUE;
		this.etat = new EstPasChemin(this);
		this.setFill(this.getShownColor());
	}
	
	@Override
	public Color getShownColor() {
		return this.getBaseColor();
	}
	
}
