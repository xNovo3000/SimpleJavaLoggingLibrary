package io.github.xnovo3000;

import io.github.xnovo3000.logger.ILogFactory;
import io.github.xnovo3000.sjll.exception.LoggerNotFoundException;

public interface LogFactory2 {
	
	/* Static fields */

	String CONFIGURATION_FILE_PATH = "SJLL/Config.json";
	
	/* Static functions */
	
	default Logger2 getLogger(Class<?> clazz) throws LoggerNotFoundException {
		return ILogFactory.INSTANCE.getLoggerImpl(clazz.getCanonicalName());
	}
	
	default Logger2 getLogger(String key) throws LoggerNotFoundException {
		return ILogFactory.INSTANCE.getLoggerImpl(key);
	}
	
	/* Implementations */
	
	Logger2 getLoggerImpl(String key);
	
}
