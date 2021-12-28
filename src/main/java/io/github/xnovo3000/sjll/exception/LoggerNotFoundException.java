package io.github.xnovo3000.sjll.exception;

public class LoggerNotFoundException extends RuntimeException {
	
	public LoggerNotFoundException(String key) {
		super("The logger with the key \"" + key + "\" does not exists");
	}
	
}
