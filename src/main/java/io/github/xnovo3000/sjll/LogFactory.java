package io.github.xnovo3000.sjll;

import java.util.List;

public abstract class LogFactory {
	
	public static synchronized Logger getLogger(String name) {
		return null;
	}
	
	public static synchronized Logger getLogger(Class<?> clazz) {
		return getLogger(clazz.getSimpleName());
	}
	
	protected abstract Logger createLogger(String name);
	
	protected abstract LogTarget createLogTarget(String name);
	
	protected abstract List<LogFormatter> createLogFormatters(String formatString);
	
}
