package io.github.xnovo3000.sjll.error;

public class LevelNotFoundError extends Error {
	
	public LevelNotFoundError() {
		super();
	}
	
	public LevelNotFoundError(String message) {
		super(message);
	}
	
	public LevelNotFoundError(String message, Throwable cause) {
		super(message, cause);
	}
	
	public LevelNotFoundError(Throwable cause) {
		super(cause);
	}
	
	public LevelNotFoundError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}
