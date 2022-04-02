package io.github.xnovo3000.sjll.error;

public class LogProviderNotFoundError extends Error {
	
	public LogProviderNotFoundError(String className) {
		super("The log provider with class name '" + className + "' has not been found");
	}
	
}