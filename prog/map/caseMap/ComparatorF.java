package prog.map.caseMap;

import java.util.Comparator;

import prog.pathFinding.Node;

public class ComparatorF implements Comparator<Node> {

	@Override
	public int compare(Node o1, Node o2) {
		if(o1.getF() > o2.getF())
			return 1;
		else if(o1.getF() < o2.getF())
			return -1;
		else {
			if(o1.getH() > o2.getH())
				return 1;
			else if(o1.getH() < o2.getH())
				return -1;
			else {
				if(o1.getG() > o2.getG())
					return 1;
				else if(o1.getG() < o2.getG())
					return -1;
				else
					return 0;
			}
		}
	}

}
