package io.github.xnovo3000.sjll.implementation.v1;

import io.github.xnovo3000.sjll.LogFactory;
import io.github.xnovo3000.sjll.LogFormatter;
import io.github.xnovo3000.sjll.LogTarget;
import io.github.xnovo3000.sjll.Logger;

import java.util.List;
import java.util.Map;

public class ILogFactory extends LogFactory {
	
	public ILogFactory(Map<?, ?> configurations) {
		super(configurations);
	}
	
	@Override
	protected Logger createLogger(Map<?, ?> configuration) {
		return null;
	}
	
	@Override
	protected LogTarget createLogTarget(Map<?, ?> configuration) {
		return null;
	}
	
	@Override
	protected List<LogFormatter> createLogFormatters(String formatString) {
		return null;
	}
	
}
