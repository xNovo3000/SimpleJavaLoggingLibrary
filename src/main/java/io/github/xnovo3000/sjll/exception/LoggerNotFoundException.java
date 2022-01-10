package io.github.xnovo3000.sjll.exception;

/**
 * <p>Thrown when the logger has not been found</p>
 *
 * @since 1.0
 * @author xNovo3000
 */
public class LoggerNotFoundException extends RuntimeException {
	
	/**
	 * <p>Default constructor</p>
	 *
	 * @param key The key of the logger
	 */
	public LoggerNotFoundException(String key) {
		super("The logger with the key \"" + key + "\" does not exists");
	}
	
}
