package prog.pathFinding.findNeighbor;

import java.util.List;

import prog.map.Map;
import prog.map.caseMap.Case;
import prog.pathFinding.exception.DistanceImpossibleACalculerException;

public interface FindNeighbor {

	public List<Case> findNeighbor(Map map, Case caseD);
	public double getHeuristic(Case caseD, Case caseA) throws DistanceImpossibleACalculerException;
	public double getDistance(Case caseD, Case caseA) throws DistanceImpossibleACalculerException;
}
