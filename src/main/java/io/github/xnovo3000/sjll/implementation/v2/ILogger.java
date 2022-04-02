package io.github.xnovo3000.sjll.implementation.v2;

import io.github.xnovo3000.sjll.LogTarget;
import io.github.xnovo3000.sjll.Logger;
import io.github.xnovo3000.sjll.data.Level;
import io.github.xnovo3000.sjll.data.LogMessage;

import java.util.List;
import java.util.Objects;

public class ILogger implements Logger {
	
	private final List<LogTarget> logTargets;
	private final int minimumImportance;
	private final String caller;
	
	public ILogger(List<LogTarget> logTargets, int minimumImportance, String caller) {
		this.logTargets = logTargets;
		this.minimumImportance = minimumImportance;
		this.caller = caller;
	}
	
	public ILogger copyWithCaller(String newCaller) {
		return new ILogger(logTargets, minimumImportance, newCaller);
	}
	
	@Override
	public void d(Object object) {
		if (Level.DEBUG.getImportance() >= minimumImportance) {
			for (LogTarget logTarget : logTargets) {
				logTarget.enqueue(new LogMessage(Level.DEBUG, caller, Objects.toString(object)));
			}
		}
	}
	
	@Override
	public void i(Object object) {
		if (Level.INFO.getImportance() >= minimumImportance) {
			for (LogTarget logTarget : logTargets) {
				logTarget.enqueue(new LogMessage(Level.INFO, caller, Objects.toString(object)));
			}
		}
	}
	
	@Override
	public void w(Object object) {
		if (Level.WARNING.getImportance() >= minimumImportance) {
			for (LogTarget logTarget : logTargets) {
				logTarget.enqueue(new LogMessage(Level.WARNING, caller, Objects.toString(object)));
			}
		}
	}
	
	@Override
	public void e(Object object) {
		if (Level.ERROR.getImportance() >= minimumImportance) {
			for (LogTarget logTarget : logTargets) {
				logTarget.enqueue(new LogMessage(Level.ERROR, caller, Objects.toString(object)));
			}
		}
	}
	
}
