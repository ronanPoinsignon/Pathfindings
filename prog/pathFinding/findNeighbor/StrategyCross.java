package prog.pathFinding.findNeighbor;

import java.util.ArrayList;
import java.util.List;

import prog.map.Map;
import prog.map.caseMap.Case;
import prog.pathFinding.exception.CoordsOutOfMapException;
import prog.pathFinding.exception.DistanceImpossibleACalculerException;

public class StrategyCross extends StrategyFind {

	@Override
	public List<Case> findNeighbor(Map map, Case caseD) {
		List<Case> liste = new ArrayList<>();
		for(int i = -1; i < 2; i += 2) {
			for(int j = -1; j < 2; j += 2) {
				try {
					liste.add(map.getCase(caseD.getCoordX() + i, caseD.getCoordY() + j));
				} catch (CoordsOutOfMapException e) {
					
				}
			}
		}
		return liste;
	}

	@Override
	public double getHeuristic(Case caseD, Case caseA) throws DistanceImpossibleACalculerException {
		if(Math.abs((caseD.getCoordX() - caseA.getCoordX()) + Math.abs(caseD.getCoordY() - caseA.getCoordY()))%2 != 0){
			throw new DistanceImpossibleACalculerException();
		}
		return Math.abs(caseD.getCoordX() - caseA.getCoordX()) + Math.abs(caseD.getCoordY() - caseA.getCoordY()) / 2;
	}

	@Override
	public double getDistance(Case caseD, Case caseA) throws DistanceImpossibleACalculerException {
		if(Math.abs((caseD.getCoordX() - caseA.getCoordX()) + Math.abs(caseD.getCoordY() - caseA.getCoordY()))%2 != 0){
			throw new DistanceImpossibleACalculerException();
		}
		return Math.abs(caseD.getCoordX() - caseA.getCoordX()) + Math.abs(caseD.getCoordY() - caseA.getCoordY()) / 2;
	}

}
