package io.github.xnovo3000;

import io.github.xnovo3000.sjll.data.LogMessage2;

public interface LogTarget2 extends Runnable, AutoCloseable {
	
	void put(LogMessage2 logMessage);
	
}
