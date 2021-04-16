package prog.pathFinding.exception;

public class DistanceImpossibleACalculerException extends PathFindingException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String MESSAGE = "Aucun chemin ne peut être trouvé avec cette carte et cette stratégie de pathfinding.";
	private static final String TITLE = "Erreur sur le chemin";
	public static final String HEADER = "Chemin impossible à tracer";
	
	public DistanceImpossibleACalculerException() {
		super(MESSAGE, TITLE, HEADER);
	}

}
