package io.github.xnovo3000.sjll.formatter;

import io.github.xnovo3000.sjll.data.LogMessage;

/**
 * <p>Appends the thread name</p>
 *
 * @since 1.0
 * @author xNovo3000
 */
public class ThreadNameFormatter implements LogFormatter {
	
	/* Singleton pattern */
	
	private static ThreadNameFormatter threadNameFormatter;
	
	public static synchronized ThreadNameFormatter getInstance() {
		if (threadNameFormatter == null) {
			threadNameFormatter = new ThreadNameFormatter();
		}
		return threadNameFormatter;
	}
	
	/* Class */
	
	private ThreadNameFormatter() {}
	
	@Override
	public void format(StringBuilder current, LogMessage logMessage) {
		current.append(logMessage.getThreadName());
	}
	
}
