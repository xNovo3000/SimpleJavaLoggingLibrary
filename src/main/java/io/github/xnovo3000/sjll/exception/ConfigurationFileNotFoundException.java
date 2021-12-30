package io.github.xnovo3000.sjll.exception;

import static io.github.xnovo3000.LogFactory2.CONFIGURATION_FILE_PATH;

public class ConfigurationFileNotFoundException extends RuntimeException {
	
	public ConfigurationFileNotFoundException() {
		super("The configuration file located at \"" + CONFIGURATION_FILE_PATH + "\" does not exists");
	}
	
}
