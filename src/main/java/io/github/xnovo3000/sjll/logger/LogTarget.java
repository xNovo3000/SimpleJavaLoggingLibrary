package io.github.xnovo3000.sjll.logger;

import io.github.xnovo3000.sjll.data.LogMessage;

public interface LogTarget extends Runnable, AutoCloseable {
	
	void enqueue(LogMessage logMessage);
	
}
