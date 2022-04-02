package io.github.xnovo3000.sjll.error;

public class LogConfigurationError extends Error {
	
	public LogConfigurationError() {
		super();
	}
	
	public LogConfigurationError(String message) {
		super(message);
	}
	
	public LogConfigurationError(String message, Throwable cause) {
		super(message, cause);
	}
	
	public LogConfigurationError(Throwable cause) {
		super(cause);
	}
	
	public LogConfigurationError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}
