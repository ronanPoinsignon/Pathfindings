package prog.pathFinding.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import prog.map.Map;
import prog.map.caseMap.Case;
import prog.map.caseMap.CaseArrivee;
import prog.map.caseMap.CaseDepart;
import prog.map.caseMap.ComparatorF;
import prog.map.caseMap.VisiteImpossibleException;
import prog.map.caseMap.state.EtatChemin;
import prog.map.caseMap.state.EtatVisite;
import prog.map.observator.ObsCase;
import prog.map.observator.ObsEtat;
import prog.map.observator.ObsValeur;
import prog.pathFinding.Node;
import prog.pathFinding.exception.CoordsOutOfMapException;
import prog.pathFinding.exception.DistanceImpossibleACalculerException;
import prog.pathFinding.exception.NoSolutionFoundException;
import prog.pathFinding.findNeighbor.FindNeighbor;

/**
 * Algorithme A*.
 * @author ronan
 *
 */
public class BidirectionnalAStar extends Strategy {
	
	protected double weight = 1;

	public BidirectionnalAStar() {
		super(null, null, null);
	}
	
	public BidirectionnalAStar(Map map, CaseDepart caseD, CaseArrivee caseA) {
		super(map, caseD, caseA);
	}

	
	@Override
	public List<Case> find(Map map, CaseDepart caseD, CaseArrivee caseA) throws CoordsOutOfMapException, NoSolutionFoundException, VisiteImpossibleException, DistanceImpossibleACalculerException {
		findStrat.getHeuristic(caseD, caseA);
		List<Node> listeD = new ArrayList<>();
		List<Node> listeA = new ArrayList<>();
		listeD.add(getNode(caseD));
		listeA.add(getNode(caseA));
		while(!listeD.isEmpty() && !listeA.isEmpty()){
			if(!listeD.isEmpty()) {
				Node n = listeD.remove(0);
				if(n.getCase() == caseA || listeA.contains(n)) {
					System.out.println("succes");
					return null;
				}
				List<Case> liste = findStrat.findNeighbor(map, n.getCase());
			}
		}
	}
	
	public Node getNode(Case c) {
		Node n = Node.getNode(c);
		if(n == null)
			n = new Node(c, true);
		return n;
	}

	@Override
	public Strategy clone() {
		return new BidirectionnalAStar();
	}
	
	private static class Node {
		private Node parent;
		private Case caseM;
		private static java.util.Map<Case, Node> map = new HashMap<>();
		
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
}

