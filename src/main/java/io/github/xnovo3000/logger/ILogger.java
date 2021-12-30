package io.github.xnovo3000.logger;

import io.github.xnovo3000.LogTarget;
import io.github.xnovo3000.Logger;
import io.github.xnovo3000.sjll.data.Level;
import io.github.xnovo3000.sjll.data.LogMessage;
import io.github.xnovo3000.sjll.data.Null;

import java.util.List;
import java.util.Objects;

public class ILogger implements Logger {
	
	private final List<LogTarget> targets;
	
	public ILogger(List<LogTarget> targets) {
		this.targets = targets;
	}
	
	@Override
	public void d(String caller, Object obj) {
		partialLogImpl(Level.DEBUG, caller, obj);
	}
	
	@Override
	public void i(String caller, Object obj) {
		partialLogImpl(Level.INFO, caller, obj);
	}
	
	@Override
	public void w(String caller, Object obj) {
		partialLogImpl(Level.WARNING, caller, obj);
	}
	
	@Override
	public void e(String caller, Object obj) {
		partialLogImpl(Level.ERROR, caller, obj);
	}
	
	private void partialLogImpl(Level level, String caller, Object obj) {
		Objects.requireNonNull(caller);
		if (obj == null) {
			obj = Null.INSTANCE;
		}
		completeLogImpl(new LogMessage(level, caller, obj.toString()));
	}
	
	private void completeLogImpl(LogMessage logMessage) {
		for (LogTarget target : targets) {
			target.put(logMessage);
		}
	}
	
}
