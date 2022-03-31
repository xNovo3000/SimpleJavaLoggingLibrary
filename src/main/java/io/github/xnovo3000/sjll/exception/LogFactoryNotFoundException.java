package io.github.xnovo3000.sjll.exception;

public class LogFactoryNotFoundException extends RuntimeException {
	
	public LogFactoryNotFoundException(String key) {
		super("The factory at " + key + " has not been found");
	}
	
}
