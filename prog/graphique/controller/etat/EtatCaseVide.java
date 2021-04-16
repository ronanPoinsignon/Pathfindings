package prog.graphique.controller.etat;

import prog.map.Coordonnees;
import prog.map.caseMap.Case;
import prog.map.caseMap.EmptyCase;

public class EtatCaseVide extends EtatCase {

	@Override
	public Case getCase(Coordonnees coords) {
		return new EmptyCase(coords);
	}

	@Override
	public EtatCase changeEtat() {
		return new EtatMur();
	}

}
