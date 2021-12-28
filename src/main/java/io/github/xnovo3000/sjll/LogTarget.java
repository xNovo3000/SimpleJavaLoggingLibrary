package io.github.xnovo3000.sjll;

import io.github.xnovo3000.sjll.data.LogMessage;

public interface LogTarget extends Runnable, AutoCloseable {
	
	void put(LogMessage message);
	
}
