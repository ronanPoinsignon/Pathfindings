package prog.pathFinding;

import java.util.List;

import prog.map.Map;
import prog.map.caseMap.Case;
import prog.map.caseMap.CaseArrivee;
import prog.map.caseMap.CaseDepart;
import prog.map.caseMap.VisiteImpossibleException;
import prog.pathFinding.algorithm.Strategy;
import prog.pathFinding.exception.CoordsOutOfMapException;
import prog.pathFinding.exception.DistanceImpossibleACalculerException;
import prog.pathFinding.exception.NoSolutionFoundException;
import prog.pathFinding.findNeighbor.StrategyFind;

/**
 * Classe utilisant un algorithme donné pour trouver le chemin le plus court entre 2 points donnés de la {@link Map}.
 * @author ronan
 *
 */
public class PathFinding {

	protected Strategy strategy;
	private CaseDepart caseD;
	private CaseArrivee caseA;
	
	/**
	 * Permet de choisir la manière de trouver le chemin parmis les algorithmes donnés.
	 * @param strategy
	 */
	public PathFinding(Strategy strategy, CaseDepart caseD, CaseArrivee caseA) {
		this.strategy = strategy;
		this.caseD = caseD;
		this.caseA = caseA;
	}
	
	public PathFinding(Strategy strategy, CaseDepart caseD, CaseArrivee caseA, StrategyFind strat) {
		this.strategy = strategy;
		this.caseD = caseD;
		this.caseA = caseA;
		this.strategy.setFindStrat(strat);
	}
	
	public List<Case> find(Map map, CaseDepart caseD, CaseArrivee caseA) throws CoordsOutOfMapException, NoSolutionFoundException, VisiteImpossibleException, DistanceImpossibleACalculerException {
		return strategy.find(map, caseD, caseA);
	}
}
