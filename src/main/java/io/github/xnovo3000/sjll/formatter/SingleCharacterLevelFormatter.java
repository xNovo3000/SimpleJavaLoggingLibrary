package io.github.xnovo3000.sjll.formatter;

import io.github.xnovo3000.sjll.data.LogMessage;

/**
 * <p>Appends a single character level indicator of the log message</p>
 *
 * @since 1.0
 * @author xNovo3000
 */
public class SingleCharacterLevelFormatter implements LogFormatter {
	
	@Override
	public void format(StringBuilder current, LogMessage logMessage) {
		current.append(logMessage.getLevel().getSingleChar());
	}
	
}
