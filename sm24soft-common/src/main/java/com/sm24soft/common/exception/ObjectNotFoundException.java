package com.sm24soft.common.exception;

/**
 * The ObjectNotFoundException exception is thrown by a finder method to
 * indicate that the specified object does not exist.
 * 
 * @author sondn
 *
 */
public class ObjectNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7768990994799133636L;

	/**
	 * Constructs a new exception with the specified detail message. The cause
	 * is not initialized, and may subsequently be initialized by a call to
	 * {@link #initCause}.
	 * 
	 * @param message
	 *            The detail message which is saved for later retrieval by the
	 *            {@link #getMessage()} method.
	 */
	public ObjectNotFoundException(final String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with the specified cause and a detail message
	 * of <code>(cause==null ?
	 * null : cause.toString())</code> (which typically contains the class and
	 * detail message of <code>cause</code>). This constructor is useful for
	 * exceptions that are little more than wrappers for other throwables.
	 * 
	 * @param cause
	 *            The cause which is saved for later retrieval by the
	 *            {@link #getCause()} method. A {@code null} value is permitted,
	 *            and indicates that the cause is nonexistent or unknown.
	 * @since 1.4
	 */
	public ObjectNotFoundException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a new exception with the specified detail message and cause.
	 * <p>
	 * Note that the detail message associated with <code>cause</code> is not
	 * automatically incorporated into this exception's detail message.
	 * 
	 * @param message
	 *            The detail message which is saved for later retrieval by the
	 *            {@link #getMessage()} method.
	 * @param cause
	 *            The cause which is saved for later retrieval by the
	 *            {@link #getCause()} method. A {@code null} value is permitted,
	 *            and indicates that the cause is nonexistent or unknown.
	 * @since 1.4
	 */
	public ObjectNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
}
