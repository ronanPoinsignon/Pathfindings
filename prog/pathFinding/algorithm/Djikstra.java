package prog.pathFinding.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import prog.map.Map;
import prog.map.caseMap.Case;
import prog.map.caseMap.CaseArrivee;
import prog.map.caseMap.CaseDepart;
import prog.map.caseMap.VisiteImpossibleException;
import prog.map.caseMap.state.EtatVisite;
import prog.pathFinding.exception.CoordsOutOfMapException;
import prog.pathFinding.exception.DistanceImpossibleACalculerException;
import prog.pathFinding.exception.NoSolutionFoundException;

public class Djikstra extends Strategy {
	
	public Djikstra() {
		super(null, null, null);
	}
	
	public Djikstra(Map map, CaseDepart caseD, CaseArrivee caseA) {
		super(map, caseD, caseA);
	}

	@Override
	public List<Case> find(Map map, CaseDepart caseD, CaseArrivee caseA)
			throws CoordsOutOfMapException, NoSolutionFoundException, VisiteImpossibleException, DistanceImpossibleACalculerException {
		findStrat.getHeuristic(caseD, caseA);
		Node.removeNeighbor();
		List<Node> nodes = new ArrayList<>();
		
		for(int i = 0; i < map.getTailleX(); i++) {
			for(int j = 0; j < map.getTailleY(); j++) {
				Case c = map.getCase(i, j);
				Node n = getNode(c);
				nodes.add(n);
			}
		}
		for(Node n : nodes) {
			n.setDistance(Double.POSITIVE_INFINITY);
		}
		Node start = getNode(caseD);
		double distance_min = 0;
		start.setDistance(distance_min);

		while(!nodes.isEmpty()) {
			List<Node> sortedList = nodes.stream().sorted().collect(Collectors.toList());
			Node noeudCourant = sortedList.get(0);
			noeudCourant.getCase().setVisite(new EtatVisite());
			sleep(getSleepTime());
			List<Case> voisins = findStrat.findNeighbor(map, noeudCourant.getCase());
			for(Case c : voisins) {
				try {
					c.accepte(this);
					Node n = getNode(c);
					double distance = noeudCourant.getDistance() + findStrat.getHeuristic(noeudCourant.getCase(), c);
					if(distance < n.getDistance()) {
						n.setDistance(distance);
						n.setParent(noeudCourant);
					}
					if(n.getCase().equals(caseA)) {
						if(checkSolution(n, caseD))
							return getListe(n);
					}
				}
				catch(VisiteImpossibleException e) {
					
				}
			}
			nodes.remove(noeudCourant);
		}
		throw new NoSolutionFoundException();
	}
	
	public List<Case> getListe(Node noeud) {
		List<Case> liste = new ArrayList<>();
		while(noeud != null) {
			liste.add(noeud.getCase());
			noeud = noeud.getParent();
		}
		Collections.reverse(liste);
		return liste;
	}
	
	public Node getNode(Case c) {
		Node n = Node.getNeighbor(c);
		if(n == null)
			n = new Node(c);
		return n;
	}
	
	public boolean checkSolution(Node n, Case caseD) {
		boolean stop = false;
		Node parent = null;
		while(!stop) {
			parent = n.getParent();
			if(parent == null)
				stop = true;
			else
				n = parent;
		}
		return n.getCase() == caseD;
	}
	
	@Override
	public Strategy clone() {
		return new Djikstra();
	}
	
	static class Node implements Comparable<Node> {
		
		private static java.util.Map<Case, Node> mapNode = new HashMap<>();
		
		private Case c;
		private Node parent = null;
		private double distance;
		
		public Node(Case c) {
			this.c = c;
			mapNode.put(c, this);
		}
		
		public Case getCase() {
			return c;
		}
		
		public Node getParent() {
			return parent;
		}
		
		public void setParent(Node parent) {
			this.parent = parent;
		}
		
		public double getDistance() {
			return distance;
		}
		
		public void setDistance(double distance) {
			this.distance = distance;
		}
		
		public static Node getNeighbor(Case c) {
			return mapNode.get(c);
		}
		
		public static void removeNeighbor() {
			mapNode.clear();
		}

		@Override
		public int compareTo(Node o) {
			if(this.distance > o.distance)
				return 1;
			else if(this.distance < o.distance)
				return -1;
			else
				return 0;
		}
		
		@Override
		public String toString() {
			return this.distance + "";
		}
	}
}

