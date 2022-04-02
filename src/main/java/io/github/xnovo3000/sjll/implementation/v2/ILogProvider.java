package io.github.xnovo3000.sjll.implementation.v2;

import io.github.xnovo3000.sjll.LogFormatter;
import io.github.xnovo3000.sjll.LogProvider;
import io.github.xnovo3000.sjll.Logger;
import io.github.xnovo3000.sjll.outputprovider.OutputProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ILogProvider extends LogProvider {
	
	private final List<ILogger> loggers;
	private final List<ILogTarget> logTargets;
	private final List<LogFormatter> formatters;
	private final List<OutputProvider> outputProviders;
	
	public ILogProvider(Map<?, ?> configurations) {
		// Call super
		super(configurations);
		// Generate containers
		this.loggers = new ArrayList<>();
		this.logTargets = new ArrayList<>();
		this.formatters = new ArrayList<>();
		this.outputProviders = new ArrayList<>();
		// Initialize
		generateLogTargets();
		generateLoggers();
	}
	
	private void generateLogTargets() {
		// Get the targets from the configurations
		List<?> configurationTargets = (List<?>) configurations.get("targets");
		// Create each target
		for (Object objectConfigurationTarget : configurationTargets) {
			Map<?, ?> configurationTarget = (Map<?, ?>) objectConfigurationTarget;
			String name = (String) configurationTarget.get("name");
			String provider = (String) configurationTarget.get("console");
			String socket = (String) configurationTarget.get("socket");
			Integer level = (Integer) configurationTarget.get("level");
		}
	}
	
	private void generateLoggers() {
	
	}
	
	@Override
	public Logger getLoggerImpl(String name) {
		// Try to get the corresponding logger or copy the default logger with a different caller
		return loggers.stream()
			.filter(logger -> logger.getCaller().equals(name))
			.findFirst()
			.orElse(loggers.get(0).copyWithCaller(name));
	}
	
}
