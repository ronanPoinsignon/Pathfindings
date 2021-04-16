package prog.map.caseMap;

/**
 * Exception permettant de donner l'information comme quoi la {@link Case} ne peut être visitée.
 * @author ronan
 *
 */
public class VisiteImpossibleException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "Impossible d'aller sur cette case.";
	
	
	public VisiteImpossibleException() {
		super(MESSAGE);
	}
}
