package prog.graphique.controller.etat;

import prog.map.Coordonnees;
import prog.map.caseMap.Case;
import prog.map.caseMap.Wall;

public class EtatMur extends EtatCase {

	@Override
	public Case getCase(Coordonnees coords) {
		return new Wall(coords);
	}

	@Override
	public EtatCase changeEtat() {
		return new EtatCaseVide();
	}

}
