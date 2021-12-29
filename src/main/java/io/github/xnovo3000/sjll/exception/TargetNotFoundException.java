package io.github.xnovo3000.sjll.exception;

public class TargetNotFoundException extends RuntimeException {
	
	public TargetNotFoundException(String key) {
		super("The target with the key \"" + key + "\" has not been found");
	}
	
}
