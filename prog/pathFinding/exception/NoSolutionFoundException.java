package prog.pathFinding.exception;

public class NoSolutionFoundException extends PathFindingException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "Aucun chemin n'a pu être trouvé avec ce choix de stratégie et de map.";
	private static final String TITLE = "Erreur sur le chemin";
	public static final String HEADER = "Aucun chemin n'a pu être trouvé.";
	
	public NoSolutionFoundException() {
		super(MESSAGE,TITLE, HEADER);
	}

}
