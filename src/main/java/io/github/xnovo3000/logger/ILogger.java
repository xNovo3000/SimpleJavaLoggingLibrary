package io.github.xnovo3000.logger;

import io.github.xnovo3000.LogTarget;
import io.github.xnovo3000.Logger;

import java.util.ArrayList;
import java.util.List;

public class ILogger implements Logger {
	
	private final List<LogTarget> targets;
	
	public ILogger(List<LogTarget> targets) {
		this.targets = new ArrayList<>();
	}
	
	@Override
	public void d(Object obj) {
	
	}
	
	@Override
	public void i(Object obj) {
	
	}
	
	@Override
	public void w(Object obj) {
	
	}
	
	@Override
	public void e(Object obj) {
	
	}
	
}
