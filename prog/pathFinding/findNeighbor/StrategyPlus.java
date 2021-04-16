package prog.pathFinding.findNeighbor;

import java.util.ArrayList;
import java.util.List;

import prog.map.Map;
import prog.map.caseMap.Case;
import prog.pathFinding.exception.CoordsOutOfMapException;

public class StrategyPlus extends StrategyFind {

	@Override
	public List<Case> findNeighbor(Map map, Case caseD) {
		List<Case> liste = new ArrayList<>();
		for(int i = -1; i < 2; i += 2) {
			int j = 0;
			try {
				Case caseS = map.getCase(caseD.getCoordX() + i, caseD.getCoordY() + j);
				liste.add(caseS);
			} catch (CoordsOutOfMapException e) {
				
			}
		}
		for(int j = -1; j < 2; j += 2) {
			int i = 0;
			try {
				Case caseS = map.getCase(caseD.getCoordX() + i, caseD.getCoordY() + j);
				liste.add(caseS);
			} catch (CoordsOutOfMapException e) {
				
			}
		}
		return liste;
	}

	@Override
	public double getHeuristic(Case caseD, Case caseA) {
		return Math.abs(caseD.getCoordX() - caseA.getCoordX()) + Math.abs(caseD.getCoordY() - caseA.getCoordY());
	}

	@Override
	public double getDistance(Case caseD, Case caseA) {
		return Math.abs(caseD.getCoordX() - caseA.getCoordX()) + Math.abs(caseD.getCoordY() - caseA.getCoordY());
	}

}
