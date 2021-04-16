package prog.map.caseMap;

import prog.graphique.caseGraphique.CaseArriveeGraphique;
import prog.graphique.caseGraphique.CaseGraphique;
import prog.map.Coordonnees;

public class CaseArrivee extends EmptyCase {

	private CaseGraphique caseG = new CaseArriveeGraphique();
	
	public CaseArrivee(Coordonnees coords) {
		super(coords);
	}

	@Override
	public String toString() {
		return "[A" + this.getCoordX() + ";" + this.getCoordY() + "]";
	}
	
	@Override
	public CaseGraphique getGraphicCase() {
		return caseG;
	}
}
