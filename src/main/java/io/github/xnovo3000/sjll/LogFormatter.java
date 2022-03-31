package io.github.xnovo3000.sjll;

import io.github.xnovo3000.sjll.data.LogMessage;

/**
 * <p>Used to format a log string</p>
 *
 * @since 1.0
 * @author xNovo3000
 */
public interface LogFormatter {
	
	/**
	 * <p>Appends to the string a formatted string</p>
	 *
	 * @param current The current value
	 * @param logMessage The message
	 */
	void format(StringBuilder current, LogMessage logMessage);
	
}
