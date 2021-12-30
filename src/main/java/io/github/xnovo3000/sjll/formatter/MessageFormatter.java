package io.github.xnovo3000.sjll.formatter;

import io.github.xnovo3000.sjll.data.LogMessage;

public class MessageFormatter implements LogFormatter {
	
	public static final MessageFormatter INSTANCE = new MessageFormatter();
	
	@Override
	public void onFormat(StringBuilder current, LogMessage logMessage) {
		current.append(logMessage.getMessage());
	}
	
}
