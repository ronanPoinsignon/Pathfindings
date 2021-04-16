package prog.pathFinding.algorithm;

import java.util.List;

import prog.map.Map;
import prog.map.caseMap.Case;
import prog.map.caseMap.CaseArrivee;
import prog.map.caseMap.CaseDepart;
import prog.map.caseMap.VisiteImpossibleException;
import prog.pathFinding.exception.CoordsOutOfMapException;
import prog.pathFinding.exception.DistanceImpossibleACalculerException;
import prog.pathFinding.exception.NoSolutionFoundException;

/**
 * Interface décrivant la recherche du chemin.
 * @author ronan
 *
 */
public interface PathFindingStrategy {

	public List<Case> find(Map map, CaseDepart caseD, CaseArrivee caseA) throws CoordsOutOfMapException, NoSolutionFoundException, VisiteImpossibleException, DistanceImpossibleACalculerException;
}
