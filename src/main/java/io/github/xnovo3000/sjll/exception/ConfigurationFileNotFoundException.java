package io.github.xnovo3000.sjll.exception;

public class ConfigurationFileNotFoundException extends RuntimeException {
	
	public ConfigurationFileNotFoundException(String filePath) {
		super("The configuration file located at \"" + filePath + "\" does not exists");
	}
	
}
