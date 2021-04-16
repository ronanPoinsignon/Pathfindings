package prog.graphique.caseGraphique;

import javafx.scene.paint.Color;
import prog.graphique.caseGraphique.etatChemin.EstPasChemin;

public class WallGraphique extends CaseGraphique {
	
	public WallGraphique() {
		this.bg = Color.BLACK;
		this.etat = new EstPasChemin(this);
		this.setFill(this.getShownColor());
	}

	@Override
	public CaseGraphique changeCase() {
		return new EmptyCaseGraphique();
	}

}
