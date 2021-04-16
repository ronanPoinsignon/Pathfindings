package prog.map.caseMap;

import prog.graphique.caseGraphique.CaseGraphique;
import prog.graphique.caseGraphique.EmptyCaseGraphique;
import prog.map.Coordonnees;
import prog.map.observator.ObsCase;
import prog.pathFinding.algorithm.Visiteur;

/**
 * {@link Case} vide pouvant être visitée.
 * @author ronan
 *
 */
public class EmptyCase extends Case {

	private CaseGraphique caseG = new EmptyCaseGraphique();
	
	public EmptyCase(Coordonnees coords) {
		super(coords);
	}

	@Override
	public void accepte(Visiteur v) throws VisiteImpossibleException {
		v.visite(this);
	}
	
	@Override
	public String toString() {
		return "[E" + this.getCoordX() + ";" + this.getCoordY() + "]";
	}

	@Override
	public Case clone() {
		Case c = new EmptyCase(new Coordonnees(getCoordX(), getCoordX()));
		c.listeObservateurEtat = this.listeObservateurEtat;
		return c;
	}

	@Override
	public Case changeCase() {
		Case c = new Wall(new Coordonnees(this.getCoordX(), this.getCoordY()));
		for(ObsCase obs : listeObservateurCase)
			obs.notifierCase(c);
		return c;
	}

	@Override
	public CaseGraphique getGraphicCase() {
		return caseG;
	}
	
	

}
