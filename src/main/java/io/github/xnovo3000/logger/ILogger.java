package io.github.xnovo3000.logger;

import io.github.xnovo3000.LogTarget2;
import io.github.xnovo3000.Logger2;

import java.util.ArrayList;
import java.util.List;

public class ILogger implements Logger2 {
	
	private final List<LogTarget2> targets;
	
	public ILogger(List<LogTarget2> targets) {
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
