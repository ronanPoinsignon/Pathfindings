package prog.map;

import prog.map.caseMap.Case;
import prog.map.caseMap.EmptyCase;
import prog.map.caseMap.state.EtatInconnu;
import prog.pathFinding.exception.CoordsOutOfMapException;

/**
 * Carte où l'algorithme de recherche fera son travail.
 * @author ronan
 *
 */
public class Map {

	public static final int DEFAULT_TAILLE_X = 10;
	public static final int DEFAULT_TAILLE_Y = 10;
	
	private Case[][] plateau;
	private int tailleX, tailleY;
	
	public Map() {
		this.tailleX = DEFAULT_TAILLE_X;
		this.tailleY = DEFAULT_TAILLE_Y;
		plateau = new Case[DEFAULT_TAILLE_X][DEFAULT_TAILLE_Y];
	}
	
	public Map(int tailleX, int tailleY) {
		this.tailleX = tailleX;
		this.tailleY = tailleY;
		plateau = new Case[tailleX][tailleY];
	}
	
	public void initEmptyMap() {
		for(int i = 0; i < tailleX; i++) {
			for(int j = 0; j < tailleY; j++) {
				plateau[i][j] = new EmptyCase(new Coordonnees(i, j));
			}
		}
	}
	
	public void setCase(int x, int y, Case c) {
		this.plateau[x][y] = c; 
	}
	
	public Case getCase(int x, int y) throws CoordsOutOfMapException {
		if(x < 0 || x >= tailleX || y < 0 || y >= tailleY)
			throw new CoordsOutOfMapException();
		return plateau[x][y];
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for(int j = 0; j < tailleY; j++) {
			for(int i = 0; i < tailleX; i++) {
				try {
					builder.append(this.getCase(i, j));
				} catch (CoordsOutOfMapException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			builder.append("\n");
		}
		return builder.toString();
	}
	
	public Map clone() {
		Map map = new Map(tailleX, tailleY);
		for(int i = 0; i < tailleX; i++) {
			for(int j = 0; j < tailleY; j++) {
				map.plateau[i][j] = plateau[i][j].clone();
			}
		}
		return map;
	}
	
	public void resetPath() {
		for(int i = 0; i < tailleX; i++) {
			for(int j = 0; j < tailleY; j++) {
				plateau[i][j].setVisite(new EtatInconnu());
			}
		}
	}
	
	public void resetCase() {
		for(int i = 0; i < tailleX; i++) {
			for(int j = 0; j < tailleY; j++) {
				plateau[i][j].setF(-1);
				plateau[i][j].setG(-1);
				plateau[i][j].setH(-1);
			}
		}
	}
	
	public int getTailleX() {
		return tailleX;
	}
	
	public int getTailleY() {
		return tailleY;
	}
}
