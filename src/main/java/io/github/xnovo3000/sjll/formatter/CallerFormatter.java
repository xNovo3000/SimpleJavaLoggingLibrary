package io.github.xnovo3000.sjll.formatter;

import io.github.xnovo3000.sjll.data.LogMessage2;

public class CallerFormatter implements LogFormatter2 {
	
	public static final CallerFormatter INSTANCE = new CallerFormatter();
	
	@Override
	public void onFormat(StringBuilder current, LogMessage2 logMessage) {
		current.append(logMessage.getCaller());
	}
	
}
