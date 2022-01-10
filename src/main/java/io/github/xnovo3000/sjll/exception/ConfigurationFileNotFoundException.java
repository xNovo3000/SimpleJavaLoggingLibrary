package io.github.xnovo3000.sjll.exception;

/**
 * <p>Thrown when the configuration file has not been found</p>
 *
 * @since 1.0
 * @author xNovo3000
 */
public class ConfigurationFileNotFoundException extends RuntimeException {
	
	public ConfigurationFileNotFoundException(String filePath) {
		super("The configuration file located at \"" + filePath + "\" does not exists");
	}
	
}
