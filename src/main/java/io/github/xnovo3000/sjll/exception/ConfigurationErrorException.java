package io.github.xnovo3000.sjll.exception;

/**
 * <p>Thrown when there is a wrong configuration</p>
 *
 * @since 1.0
 * @author xNovo3000
 */
public class ConfigurationErrorException extends RuntimeException {
	
	/**
	 * <p>Default constructor</p>
	 *
	 * @param message The message to print in the stacktrace
	 */
	public ConfigurationErrorException(String message) {
		super(message);
	}
	
}
