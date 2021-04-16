package prog.map.caseMap;

import prog.graphique.caseGraphique.CaseDepartGraphique;
import prog.graphique.caseGraphique.CaseGraphique;
import prog.map.Coordonnees;
import prog.pathFinding.algorithm.Visiteur;

public class CaseDepart extends EmptyCase {

	private CaseGraphique caseG = new CaseDepartGraphique();
	
	public CaseDepart(Coordonnees coords) {
		super(coords);
	}
	
	@Override
	public String toString() {
		return "[D" + this.getCoordX() + ";" + this.getCoordY() + "]";
	}
	
	@Override
	public CaseGraphique getGraphicCase() {
		return caseG;
	}

	@Override
	public void accepte(Visiteur v) throws VisiteImpossibleException {
		throw new VisiteImpossibleException();
	}

}
