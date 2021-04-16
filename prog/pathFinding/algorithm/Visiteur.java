package prog.pathFinding.algorithm;

import prog.map.caseMap.AcceptVisite;
import prog.map.caseMap.Case;

/**
 * Interface de visite des {@link AcceptVisite}.
 * @author ronan
 *
 */
public interface Visiteur {

	public void visite(Case object);
}
