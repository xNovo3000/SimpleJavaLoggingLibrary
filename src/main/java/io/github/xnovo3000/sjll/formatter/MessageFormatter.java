package io.github.xnovo3000.sjll.formatter;

import io.github.xnovo3000.sjll.data.LogMessage2;

public class MessageFormatter implements LogFormatter2 {
	
	public static final MessageFormatter INSTANCE = new MessageFormatter();
	
	@Override
	public void onFormat(StringBuilder current, LogMessage2 logMessage) {
		current.append(logMessage.getMessage());
	}
	
}
