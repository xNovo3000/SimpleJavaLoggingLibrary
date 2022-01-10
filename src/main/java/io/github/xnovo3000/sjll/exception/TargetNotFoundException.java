package io.github.xnovo3000.sjll.exception;

/**
 * <p>Thrown when the target has not been found</p>
 *
 * @since 1.0
 * @author xNovo3000
 */
public class TargetNotFoundException extends RuntimeException {
	
	public TargetNotFoundException(String key) {
		super("The target with the key \"" + key + "\" has not been found");
	}
	
}
