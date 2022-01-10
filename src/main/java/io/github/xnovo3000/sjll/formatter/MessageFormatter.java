package io.github.xnovo3000.sjll.formatter;

import io.github.xnovo3000.sjll.data.LogMessage;

/**
 * <p>Appends the message of the log message</p>
 *
 * @since 1.0
 * @author xNovo3000
 */
public class MessageFormatter implements LogFormatter {
	
	@Override
	public void format(StringBuilder current, LogMessage logMessage) {
		current.append(logMessage.getMessage());
	}
	
}
