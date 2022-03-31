package io.github.xnovo3000.sjll;

import io.github.xnovo3000.sjll.data.LogMessage;

/**
 * <p>Used to print a log message in a separate thread</p>
 *
 * @since 1.0
 * @author xNovo3000
 */
public interface LogTarget extends Runnable, AutoCloseable {
	
	/**
	 * <p>Appends a new message to the target</p>
	 *
	 * @param logMessage The message to append
	 */
	void enqueue(LogMessage logMessage);
	
}
