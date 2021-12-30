package io.github.xnovo3000;

import io.github.xnovo3000.logger.ILogFactory;
import io.github.xnovo3000.sjll.exception.LoggerNotFoundException;

public interface LogFactory {
	
	/* Static fields */

	String CONFIGURATION_FILE_PATH = "SJLL/Config.json";
	
	/* Static functions */
	
	static Logger getLogger(Class<?> clazz) throws LoggerNotFoundException {
		return ILogFactory.INSTANCE.getLoggerImpl(clazz.getCanonicalName());
	}
	
	static Logger getLogger(String key) throws LoggerNotFoundException {
		return ILogFactory.INSTANCE.getLoggerImpl(key);
	}
	
	/* Implementations */
	
	Logger getLoggerImpl(String key);
	
}
