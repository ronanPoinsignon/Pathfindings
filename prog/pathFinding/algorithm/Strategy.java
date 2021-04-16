package prog.pathFinding.algorithm;

import java.util.List;

import javafx.concurrent.Task;
import prog.map.Map;
import prog.map.caseMap.Case;
import prog.map.caseMap.CaseArrivee;
import prog.map.caseMap.CaseDepart;
import prog.map.caseMap.VisiteImpossibleException;
import prog.map.caseMap.state.EtatCalcule;
import prog.map.caseMap.state.EtatInconnu;
import prog.pathFinding.PathFinding;
import prog.pathFinding.exception.CoordsOutOfMapException;
import prog.pathFinding.exception.DistanceImpossibleACalculerException;
import prog.pathFinding.exception.NoSolutionFoundException;
import prog.pathFinding.findNeighbor.FindNeighbor;
import prog.pathFinding.findNeighbor.StrategyFind;
import prog.pathFinding.findNeighbor.StrategyStar;

/**
 * Classe abstraite servant à décrire le comportement de l'algorithme de {@link PathFinding}.
 * @author ronan
 *
 */
public abstract class Strategy extends Task<List<Case>> implements PathFindingStrategy, Visiteur {

	protected FindNeighbor findStrat;
	private volatile long sleepTime = 0;
	
	private Map map;
	private CaseDepart caseD;
	private CaseArrivee caseA;
	
	public Strategy(Map map, CaseDepart caseD, CaseArrivee caseA) {
		this.map = map;
		this.caseD = caseD;
		this.caseA = caseA;
		this.findStrat = new StrategyStar();
	}
	
	public Strategy(Map map, CaseDepart caseD, CaseArrivee caseA, long sleepTime) {
		this.map = map;
		this.caseD = caseD;
		this.caseA = caseA;
		this.findStrat = new StrategyStar();
		this.sleepTime = sleepTime;
	}
	
	public Strategy(StrategyFind strat) {
		this.findStrat = strat;
	}
	
	public Strategy(StrategyFind strat, long sleepTime) {
		this.findStrat = strat;
		this.sleepTime = sleepTime;
	}
	
	@Override
	public void visite(Case caseV) {
		if(caseV.getEtat().getClass().equals(EtatInconnu.class)) //TODO a modifier
			caseV.setVisite(new EtatCalcule());
	}
	
	public void sleep(long duration) {
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e1) {
			
		}
	}
	
	public void setFindStrat(FindNeighbor findStrat) {
		this.findStrat = findStrat;
	}
	
	public FindNeighbor getFindStrat() {
		return findStrat;
	}
	
	public long getSleepTime() {
		return sleepTime;
	}
	
	public void setSleepTime(long sleepTime) {
		this.sleepTime = sleepTime;
	}
	
	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public CaseArrivee getCaseA() {
		return caseA;
	}

	public void setCaseA(CaseArrivee caseA) {
		this.caseA = caseA;
	}

	public CaseDepart getCaseD() {
		return caseD;
	}

	public void setCaseD(CaseDepart caseD) {
		this.caseD = caseD;
	}

	@Override
	public List<Case> call() throws CoordsOutOfMapException, NoSolutionFoundException, VisiteImpossibleException, DistanceImpossibleACalculerException{
		return this.find(map, caseD, caseA);
	}
	
	@Override
	public abstract Strategy clone();
}
