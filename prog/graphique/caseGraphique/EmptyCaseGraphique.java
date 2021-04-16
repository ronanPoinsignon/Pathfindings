package prog.graphique.caseGraphique;

import javafx.scene.paint.Color;
import prog.graphique.caseGraphique.etatChemin.EstPasChemin;

public class EmptyCaseGraphique extends CaseGraphique {
	
	public EmptyCaseGraphique() {
		this.bg = Color.GRAY;
		this.etat = new EstPasChemin(this);
		this.setFill(this.getShownColor());
	}
	
	@Override
	public CaseGraphique changeCase() {
		return new WallGraphique();
	}
}
