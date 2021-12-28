package io.github.xnovo3000.sjll.implementation;

import io.github.xnovo3000.sjll.LogTarget;
import io.github.xnovo3000.sjll.Logger;
import io.github.xnovo3000.sjll.data.Level;
import io.github.xnovo3000.sjll.data.LogMessage;

import java.util.List;
import java.util.Objects;

public class ILogger implements Logger {
	
	private final List<LogTarget> targets;
	
	public ILogger(List<LogTarget> targets) {
		this.targets = targets;
	}
	
	@Override
	public void d(Object object) {
		partialLogImpl(Level.DEBUG, object);
	}
	
	@Override
	public void w(Object object) {
		partialLogImpl(Level.INFO, object);
	}
	
	@Override
	public void i(Object object) {
		partialLogImpl(Level.WARNING, object);
	}
	
	@Override
	public void e(Object object) {
		partialLogImpl(Level.ERROR, object);
	}
	
	private void partialLogImpl(Level level, Object object) {
		Objects.requireNonNull(object);
		completeLogImpl(new LogMessage(level, object.toString()));
	}
	
	private void completeLogImpl(LogMessage message) {
		targets.forEach(t -> t.put(message));
	}
	
}
