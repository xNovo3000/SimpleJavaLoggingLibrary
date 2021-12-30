package io.github.xnovo3000.sjll.formatter;

import io.github.xnovo3000.sjll.data.LogMessage;

public class ThreadNameFormatter implements LogFormatter {
	
	public static final ThreadNameFormatter INSTANCE = new ThreadNameFormatter();
	
	@Override
	public void onFormat(StringBuilder current, LogMessage logMessage) {
		current.append(logMessage.getThreadName());
	}
	
}
