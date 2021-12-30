package io.github.xnovo3000.sjll.formatter;

import io.github.xnovo3000.sjll.data.LogMessage2;
import io.github.xnovo3000.sjll.old.Formatter;
import io.github.xnovo3000.sjll.old.LogMessage;

public class ThreadNameFormatter implements LogFormatter2 {
	
	public static final ThreadNameFormatter INSTANCE = new ThreadNameFormatter();
	
	@Override
	public void onFormat(StringBuilder current, LogMessage2 logMessage) {
		current.append(logMessage.getThreadName());
	}
	
}
