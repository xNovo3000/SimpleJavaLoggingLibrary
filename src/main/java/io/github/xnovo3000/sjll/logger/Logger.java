package io.github.xnovo3000.sjll.logger;

import io.github.xnovo3000.sjll.exception.LoggerNotFoundException;

public interface Logger {
	
	static Logger getLogger(String key) throws LoggerNotFoundException {
		return PLogFactory.INSTANCE.getLogger(key);
	}
	
	void d(String caller, Object obj);
	
	void w(String caller, Object obj);
	
	void i(String caller, Object obj);
	
	void e(String caller, Object obj);
	
}
