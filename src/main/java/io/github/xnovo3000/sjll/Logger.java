package io.github.xnovo3000.sjll;

/**
 * <p>Used to log messages</p>
 *
 * @since 1.0
 * @author xNovo3000
 */
public interface Logger {
	
	/**
	 * <p>Prints a log message with DEBUG level</p>
	 *
	 * @param object The object to log
	 */
	void d(Object object);
	
	/**
	 * <p>Prints a log message with INFO level</p>
	 *
	 * @param object The object to log
	 */
	void i(Object object);
	
	/**
	 * <p>Prints a log message with WARNING level</p>
	 *
	 * @param object The object to log
	 */
	void w(Object object);
	
	/**
	 * <p>Prints a log message with ERROR level</p>
	 *
	 * @param object The object to log
	 */
	void e(Object object);
	
}
