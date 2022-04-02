package io.github.xnovo3000.sjll.error;

public class LogTargetNotFoundError extends Error {
	
	public LogTargetNotFoundError(String logTargetName, String loggerName) {
		super("The log target with name '" + loggerName + "' in logger '" + loggerName + "' has not been found");
	}
	
}