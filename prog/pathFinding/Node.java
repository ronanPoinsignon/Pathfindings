package prog.pathFinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import prog.map.caseMap.Case;
import prog.map.observator.ObsCase;
import prog.map.observator.ObsEtat;
import prog.map.observator.ObsValeur;

public class Node {

	private Node parent;
	private Case caseM;
	private static java.util.Map<Case, Node> map = new HashMap<>();

	List<ObsEtat> listeObservateurEtat = new ArrayList<>();
	List<ObsCase> listeObservateurCase = new ArrayList<>();
	List<ObsValeur> listeObservateurValeur = new ArrayList<>();
	
	private double f = -1;
	private double g = -1;
	private double h = - 1;
	
	public Node(Case caseM, boolean setToMap) {
		this.caseM = caseM;
		if(setToMap)
			map.put(caseM, this);
	}
	
	public Node(Case caseM, Node parent) {
		this.caseM = caseM;
		this.parent = parent;
	}
	
	public Case getCase() {
		return caseM;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	public static Node getNode(Case c) {
		return map.get(c);
	}
	
	public void setF(double f) {
		this.f = f;
		double ft = f, gt = g, ht = h;
		if(ft == Double.POSITIVE_INFINITY)
			ft = -1;
		if(gt == Double.POSITIVE_INFINITY)
			gt = -1;
		if(ht == Double.POSITIVE_INFINITY)
			ht = -1;
		for(ObsValeur obs : listeObservateurValeur) {
			obs.notifierValeur(ft, gt, ht);
		}
	}
	
	public double getG() {
		return g;
	}
	
	public void setG(double g) {
		this.g = g;
		double ft = f, gt = g, ht = h;
		if(ft == Double.POSITIVE_INFINITY)
			ft = -1;
		if(gt == Double.POSITIVE_INFINITY)
			gt = -1;
		if(ht == Double.POSITIVE_INFINITY)
			ht = -1;
		for(ObsValeur obs : listeObservateurValeur) {
			obs.notifierValeur(ft, gt, ht);
		}
	}
	
	public double getH() {
		return h;
	}
	
	public void setH(double h) {
		this.h = h;
		double ft = f, gt = g, ht = h;
		if(ft == Double.POSITIVE_INFINITY)
			ft = -1;
		if(gt == Double.POSITIVE_INFINITY)
			gt = -1;
		if(ht == Double.POSITIVE_INFINITY)
			ht = -1;
		for(ObsValeur obs : listeObservateurValeur) {
			obs.notifierValeur(ft, gt, ht);
		}
	}
	
	public double getF() {
		return f;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((caseM == null) ? 0 : caseM.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (caseM == null) {
			if (other.caseM != null)
				return false;
		} else if (!caseM.equals(other.caseM))
			return false;
		return true;
	}
}
