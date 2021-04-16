package prog.pathFinding.algorithm;

import prog.map.Map;
import prog.map.caseMap.CaseArrivee;
import prog.map.caseMap.CaseDepart;

public class WAStar extends AStar {
	
	public WAStar() {
		this.weight = 2;
	}
	
	public WAStar(Map map, CaseDepart caseD, CaseArrivee caseA) {
		super(map, caseD, caseA);
		this.weight = 2;
	}
	
	@Override
	public Strategy clone() {
		return new WAStar();
	}
}

