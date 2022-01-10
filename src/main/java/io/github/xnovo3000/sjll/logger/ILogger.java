package io.github.xnovo3000.sjll.logger;

import io.github.xnovo3000.sjll.data.Level;
import io.github.xnovo3000.sjll.data.LogMessage;

import java.util.List;

/**
 * <p>Standard implementation of the logger</p>
 *
 * @since 1.0
 * @author xNovo3000
 */
public final class ILogger implements Logger {
	
	private static final String NULL_STRING = "null";
	
	private final List<LogTarget> targets;
	
	ILogger(List<LogTarget> targets) {
		this.targets = targets;
	}
	
	@Override
	public void d(String caller, Object obj) {
		internalLogImpl(Level.DEBUG, caller, obj);
	}
	
	@Override
	public void i(String caller, Object obj) {
		internalLogImpl(Level.INFO, caller, obj);
	}
	
	@Override
	public void w(String caller, Object obj) {
		internalLogImpl(Level.WARNING, caller, obj);
	}
	
	@Override
	public void e(String caller, Object obj) {
		internalLogImpl(Level.ERROR, caller, obj);
	}

	private void internalLogImpl(Level level, String caller, Object obj) {
		if (caller == null) {
			caller = NULL_STRING;
		}
		if (obj == null) {
			obj = NULL_STRING;
		}
		LogMessage logMessage = new LogMessage(level, caller, obj.toString());
		for (LogTarget target : targets) {
			target.enqueue(logMessage);
		}
	}
	
}
