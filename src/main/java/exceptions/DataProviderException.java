package exceptions;

public class DataProviderException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataProviderException(String message) {
        super(message);
    }
    
    public DataProviderException(String message, Throwable cause) {
        super(message, cause);
    }
}
