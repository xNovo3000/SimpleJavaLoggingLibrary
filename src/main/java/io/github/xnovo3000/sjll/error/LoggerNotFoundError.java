package io.github.xnovo3000.sjll.error;

public class LoggerNotFoundError extends Error {
	
	public LoggerNotFoundError(String loggerName) {
		super("The logger with name '" + loggerName + "' has not been found");
	}
	
}
