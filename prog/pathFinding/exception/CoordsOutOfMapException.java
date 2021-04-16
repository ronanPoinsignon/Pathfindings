package prog.pathFinding.exception;

public class CoordsOutOfMapException extends PathFindingException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "Les coordonnées de la case de départ ou d'arrivée sont incorrectes car hors de la carte.";
	private static final String TITLE = "Coordonnées impossibles";
	public static final String HEADER = "Les coordonnées sont en dehors du plateau";
	
	public CoordsOutOfMapException() {
		super(MESSAGE, TITLE, HEADER);
	}

}
