package prog.pathFinding;

import java.util.List;

import javafx.concurrent.Task;
import prog.map.Map;
import prog.map.caseMap.Case;
import prog.map.caseMap.CaseArrivee;
import prog.map.caseMap.CaseDepart;
import prog.pathFinding.algorithm.Strategy;

public class TacheFind extends Task<List<Case>> {
		
	private Strategy strat;
	private Map map;
	private CaseDepart caseD;
	private CaseArrivee caseA;
	
	public TacheFind(Strategy strat, Map map, CaseDepart caseD, CaseArrivee caseA) {
		this.map = map;
		this.caseD = caseD;
		this.caseA = caseA;
	}

	@Override
	protected List<Case> call() throws Exception {
		System.out.println("début");
		return strat.find(map, caseD, caseA);
	}

}
