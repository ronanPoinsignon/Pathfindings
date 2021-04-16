package prog.pathFinding.exception;

public class PathFindingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String title, header;
	
	public PathFindingException(String message, String title, String header) {
		super(message);
		this.title = title;
		this.header = header;
	}

	public String getTitle() {
		return title;
	}

	public String getHeader() {
		return header;
	}
	
}
