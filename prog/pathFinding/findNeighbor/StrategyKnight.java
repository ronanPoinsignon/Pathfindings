package prog.pathFinding.findNeighbor;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import prog.map.Map;
import prog.map.caseMap.Case;
import prog.pathFinding.exception.CoordsOutOfMapException;
import prog.pathFinding.exception.DistanceImpossibleACalculerException;

public class StrategyKnight extends StrategyFind {
	
	@Override
	public List<Case> findNeighbor(Map map, Case caseD) {
		List<Case> liste = new ArrayList<>();
		double distance = Math.sqrt(Math.pow(2, 2) + Math.pow(1, 2));
		for(int i = -2; i < 3; i++) {
			for(int j = -2; j < 3; j++) {
				if(Math.sqrt(Math.pow(i, 2) + Math.pow(j, 2)) == distance) {
					try {
						liste.add(map.getCase(caseD.getCoordX() + i, caseD.getCoordY() + j));
					} catch (CoordsOutOfMapException e) {
						
					}
				}
			}
		}
		return liste;
	}

	@Override
	public double getHeuristic(Case caseD, Case caseA) throws DistanceImpossibleACalculerException {
		return getDistance(caseD, caseA);
	}

	@Override
	public double getDistance(Case caseD, Case caseA) {
		return distance(caseD, caseA, 10); //TODO gérer la taille
	}
	
	private int distance(Case caseD, Case caseA, int N) {
		
		int[] row = { 2, 2, -2, -2, 1, 1, -1, -1 };
		int[] col = { -1, 1, 1, -1, 2, -2, 2, -2 };
		
        Set<Node> visited = new HashSet<>();
        
        Queue<Node> q = new ArrayDeque<>();
        q.add(new Node(caseD.getCoordX(), caseD.getCoordY()));
        
        while (!q.isEmpty()) {
            Node node = q.poll();
            int x = node.x;
            int y = node.y;
            int dist = node.dist;
            if (x == caseA.getCoordX() && y == caseA.getCoordY())
                return dist;
            if (!visited.contains(node)) {
                visited.add(node);
                for (int i = 0; i < row.length; ++i) {
                    int x1 = x + row[i];
                    int y1 = y + col[i];
                    q.add(new Node(x1, y1, dist + 1));
                }
            }
        }
        return Integer.MAX_VALUE;
    }

	private static class Node {
		
		private int x, y, dist;
		
		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public Node(int x, int y, int dist) {
			this.x = x;
			this.y = y;
			this.dist = dist;
		}

		@Override
		public boolean equals(Object o) {
			if(this == o) 
				return true;
			if(o == null || getClass() != o.getClass()) 
				return false;
			Node node = (Node) o;
			if (x != node.x) return false;
			if (y != node.y) return false;
			return dist == node.dist;
		}

		@Override
		public int hashCode() {
			int result = x;
			result = 31 * result + y;
			result = 31 * result + dist;
			return result;
		}
	}
}
