package prog.map.caseMap;

import prog.graphique.caseGraphique.CaseGraphique;
import prog.graphique.caseGraphique.WallGraphique;
import prog.map.Coordonnees;
import prog.map.observator.ObsCase;
import prog.pathFinding.algorithm.Visiteur;

/**
 * {@link Case} impossible à visiter.
 * @author ronan
 *
 */
public class Wall extends Case {

	private CaseGraphique caseG = new WallGraphique();
	
	public Wall(Coordonnees coords) {
		super(coords);
	}

	@Override
	public void accepte(Visiteur v) throws VisiteImpossibleException {
		throw new VisiteImpossibleException();
	}
	
	@Override
	public String toString() {
		return "[W" + this.getCoordX() + ";" + this.getCoordY() + "]";
	}

	@Override
	public Case clone() {
		return new Wall(new Coordonnees(getCoordX(), getCoordY()));
	}

	@Override
	public Case changeCase() {
		Case c = new EmptyCase(new Coordonnees(getCoordX(), getCoordY()));
		for(ObsCase obs : listeObservateurCase)
			obs.notifierCase(c);
		return c;
	}

	@Override
	public CaseGraphique getGraphicCase() {
		return caseG;
	}

}
