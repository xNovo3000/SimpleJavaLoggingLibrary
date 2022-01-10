package io.github.xnovo3000.sjll.logger;

import io.github.xnovo3000.sjll.exception.LoggerNotFoundException;

/**
 * <p>Used to log messages</p>
 *
 * @since 1.0
 * @author xNovo3000
 */
public interface Logger {
	
	/**
	 * <p>Returns a logger with the name same as specified in the configuration file</p>
	 *
	 * @param key The name of the required Logger instance
	 * @return The logger, if exists
	 * @throws LoggerNotFoundException If the logger does not exists
	 */
	static Logger getLogger(String key) throws LoggerNotFoundException {
		return PLogFactory.INSTANCE.getLogger(key);
	}
	
	/**
	 * <p>Prints a log message with DEBUG level</p>
	 *
	 * @param caller Who printed this log
	 * @param message The message
	 */
	void d(String caller, Object message);
	
	/**
	 * <p>Prints a log message with INFO level</p>
	 *
	 * @param caller Who printed this log
	 * @param message The message
	 */
	void i(String caller, Object message);
	
	/**
	 * <p>Prints a log message with WARNING level</p>
	 *
	 * @param caller Who printed this log
	 * @param message The message
	 */
	void w(String caller, Object message);
	
	/**
	 * <p>Prints a log message with ERROR level</p>
	 *
	 * @param caller Who printed this log
	 * @param message The message
	 */
	void e(String caller, Object message);
	
}
