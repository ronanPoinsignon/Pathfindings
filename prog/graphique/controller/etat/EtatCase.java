package prog.graphique.controller.etat;

import prog.map.Coordonnees;
import prog.map.caseMap.Case;

public abstract class EtatCase {

	public abstract Case getCase(Coordonnees coords);
	public abstract EtatCase changeEtat();
}
