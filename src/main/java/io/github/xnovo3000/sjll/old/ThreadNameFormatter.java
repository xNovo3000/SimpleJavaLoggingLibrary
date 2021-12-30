package io.github.xnovo3000.sjll.old;

public class ThreadNameFormatter implements Formatter {
	
	@Override
	public StringBuilder onFormatRequired(StringBuilder stringBuilder, LogMessage logMessage) {
		return stringBuilder.append(logMessage.getThreadName());
	}
	
}
