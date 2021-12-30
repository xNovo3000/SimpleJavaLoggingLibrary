package io.github.xnovo3000;

import io.github.xnovo3000.sjll.data.LogMessage;

public interface LogTarget extends Runnable, AutoCloseable {
	
	void put(LogMessage logMessage);
	
}
