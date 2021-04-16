package prog.pathFinding.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import prog.map.Map;
import prog.map.caseMap.Case;
import prog.map.caseMap.CaseArrivee;
import prog.map.caseMap.CaseDepart;
import prog.map.caseMap.ComparatorF;
import prog.map.caseMap.VisiteImpossibleException;
import prog.map.caseMap.state.EtatVisite;
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
public class AStar extends Strategy {
	
	protected double weight = 1;

	public AStar() {
		super(null, null, null);
	}
	
	public AStar(Map map, CaseDepart caseD, CaseArrivee caseA) {
		super(map, caseD, caseA);
	}

	
	@Override
	public List<Case> find(Map map, CaseDepart caseD, CaseArrivee caseA) throws CoordsOutOfMapException, NoSolutionFoundException, VisiteImpossibleException, DistanceImpossibleACalculerException {
		findStrat.getHeuristic(caseD, caseA);
		List<Node> openList = new ArrayList<>();
		List<Node> closedList = new ArrayList<>();
		openList.add(getNode(caseD));
		for(int i = 0; i < map.getTailleX(); i++) {
			for(int j = 0; j < map.getTailleY(); j++) {
				Case c;
				try {
					c = map.getCase(i, j);
					getNode(c).setG(Double.POSITIVE_INFINITY);
					getNode(c).setH(Double.POSITIVE_INFINITY);
					getNode(c).setF(Double.POSITIVE_INFINITY);
				} catch (CoordsOutOfMapException e) {
					
				}
			}
		}
		getNode(caseD).setG(0);
		getNode(caseD).setH(0);
		getNode(caseD).setF(0);
		while(!openList.isEmpty()) {
			sleep(AStar.this.getSleepTime());
			Collections.sort(openList, new ComparatorF());
			Node current = openList.remove(0);
			closedList.add(current);
			if(current.getCase().equals(caseA)) {
				List<Case> l = getPath(current);
				return l;
			}
			current.getCase().setVisite(new EtatVisite());
			List<Case> voisinsTemp = findStrat.findNeighbor(map, current.getCase());
			List<Case> voisins = new ArrayList<>();
			voisins.addAll(voisinsTemp);
			for(Case caseVoisin : voisinsTemp) {
				try {
					caseVoisin.accepte(AStar.this);
					Node neighborVoisin = new Node(caseVoisin, false);
					if(!closedList.contains(neighborVoisin)) {
						double g = current.getG() + findStrat.getDistance(current.getCase(), caseVoisin);
						double h = findStrat.getHeuristic(caseVoisin, caseA) * getW();
						double f = g + h;
						neighborVoisin.setG(g);
						neighborVoisin.setH(h);
						neighborVoisin.setF(f);
						double distance = current.getG() + findStrat.getDistance(current.getCase(), caseVoisin);
						if(distance < neighborVoisin.getG() || !(openList.contains(neighborVoisin) && neighborVoisin.getG() > openList.get(openList.indexOf(neighborVoisin)).getG())) {
							Node nodeV = getNode(caseVoisin);
							nodeV.setParent(current);
							nodeV.setG(distance);
							nodeV.setH(findStrat.getHeuristic(caseVoisin, caseA) * getW());
							nodeV.setF(distance + nodeV.getH());
							nodeV.getCase().setG(distance); //asuppr
							nodeV.getCase().setH(findStrat.getHeuristic(caseVoisin, caseA) * getW()); //asuppr
							nodeV.getCase().setF(distance + nodeV.getH()); //asuppr
							if(!openList.contains(nodeV))
								openList.add(nodeV);
						}
					}
				}
				catch(VisiteImpossibleException e) {
					voisins.remove(caseVoisin);
				} catch (DistanceImpossibleACalculerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		
		throw new NoSolutionFoundException();
	}
	
	public double getW() {
		return weight;
	}
	
	public void setW(double w) {
		this.weight = w;
	}
	
	public List<Case> getPath(Node current){
		List<Case> liste = new ArrayList<>();
		while(current != null) {
			liste.add(current.getCase());
			current = current.getParent();
		}
		Collections.reverse(liste);
		return liste;
	}

	public FindNeighbor getFindStrat() {
		return findStrat;
	}

	public void setFindStrat(FindNeighbor findStrat) {
		this.findStrat = findStrat;
	}
	
	public Node getNode(Case c) {
		Node n = Node.getNode(c);
		if(n == null)
			n = new Node(c, true);
		return n;
	}
	
	@Override
	public Strategy clone() {
		return new AStar();
	}
}
