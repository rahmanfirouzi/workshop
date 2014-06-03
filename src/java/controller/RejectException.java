package controller;

/**
 * <code>RejectException</code> is an exception class for rejecting a request.
 * 
 *  @author Rahman Firouzi
 */
@SuppressWarnings("serial")
public class RejectException extends Exception {

	/**
	 * Constructs an empty exception
	 */
	public RejectException() {
	}

	/**
	 * Constructs an exception with a message.
	 * 
	 * @param message
	 *            - The error message
	 */
	public RejectException(String message) {
		super(message);
	}

	/**
	 * Constructs an exception with a cause
	 * 
	 * @param cause
	 *            - Cause of the error
	 */
	public RejectException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs an exception with a message and cause.
	 * 
	 * @param message
	 *            - The error message.
	 * @param cause
	 *            - The cause of the error
	 */
	public RejectException(String message, Throwable cause) {
		super(message, cause);
	}
}
