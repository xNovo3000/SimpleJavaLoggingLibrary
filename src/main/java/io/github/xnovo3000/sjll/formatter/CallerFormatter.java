package io.github.xnovo3000.sjll.formatter;

import io.github.xnovo3000.sjll.data.LogMessage;

public class CallerFormatter implements LogFormatter {
	
	public static final CallerFormatter INSTANCE = new CallerFormatter();
	
	@Override
	public void onFormat(StringBuilder current, LogMessage logMessage) {
		current.append(logMessage.getCaller());
	}
	
}
