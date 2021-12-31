package io.github.xnovo3000.sjll.logger;

import io.github.xnovo3000.sjll.data.Level;
import io.github.xnovo3000.sjll.data.LogMessage;
import io.github.xnovo3000.sjll.data.Null;

import java.util.List;
import java.util.Objects;

class IILogger implements Logger {
	
	private final List<IILogTarget> targets;
	
	public IILogger(List<IILogTarget> targets) {
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
		Objects.requireNonNull(caller);
		if (obj == null) {
			obj = Null.INSTANCE;
		}
		LogMessage logMessage = new LogMessage(level, caller, obj.toString());
		for (IILogTarget target : targets) {
			target.put(logMessage);
		}
	}
	
}
