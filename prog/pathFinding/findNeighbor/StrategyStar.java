package prog.pathFinding.findNeighbor;

import java.util.ArrayList;
import java.util.List;

import prog.map.Map;
import prog.map.caseMap.Case;
import prog.pathFinding.exception.CoordsOutOfMapException;
import prog.pathFinding.exception.DistanceImpossibleACalculerException;

public class StrategyStar extends StrategyFind {

	@Override
	public List<Case> findNeighbor(Map map, Case caseD) {
		List<Case> liste = new ArrayList<>();
		for(int i = -1; i < 2; i++) {
			for(int j = -1; j < 2; j++) {
				try {
					if(!(j == i && i == 0)) {
						Case caseS = map.getCase(caseD.getCoordX() + i, caseD.getCoordY() + j);
						liste.add(caseS);
					}
				} catch (CoordsOutOfMapException e) {
					
				}
			}
		}
		return liste;
	}

	@Override
	public double getHeuristic(Case caseD, Case caseA) throws DistanceImpossibleACalculerException {
		return Math.sqrt(Math.pow(caseD.getCoordX() - caseA.getCoordX(), 2) + Math.pow(caseD.getCoordY() - caseA.getCoordY(), 2));
		//return Math.max(Math.abs(caseD.getCoordX() - caseA.getCoordX()), Math.abs(caseD.getCoordY() - caseA.getCoordY()));
	}

	@Override
	public double getDistance(Case caseD, Case caseA) {
		return Math.sqrt(Math.pow(caseD.getCoordX() - caseA.getCoordX(), 2) + Math.pow(caseD.getCoordY() - caseA.getCoordY(), 2));
		//return Math.max(Math.abs(caseD.getCoordX() - caseA.getCoordX()), Math.abs(caseD.getCoordY() - caseA.getCoordY()));
	}

}
