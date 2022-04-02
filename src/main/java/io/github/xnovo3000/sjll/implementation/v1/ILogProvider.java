package io.github.xnovo3000.sjll.implementation.v1;

import io.github.xnovo3000.sjll.LogProvider;
import io.github.xnovo3000.sjll.Logger;

import java.util.Map;

public class ILogProvider extends LogProvider {
	
	public ILogProvider(Map<?, ?> configurations) {
		super(configurations);
	}
	
	@Override
	public Logger getLoggerImpl(String name) {
		return null;
	}
	
}
