package exceptions;
public class ProductNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProductNotFoundException(String identifier) {
        super("Product not found: " + identifier);
    }
}