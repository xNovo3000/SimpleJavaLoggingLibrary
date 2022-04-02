package io.github.xnovo3000.sjll.implementation.v2;

import io.github.xnovo3000.sjll.LogProvider;
import io.github.xnovo3000.sjll.Logger;
import io.github.xnovo3000.sjll.data.Level;
import io.github.xnovo3000.sjll.error.LogConfigurationError;
import io.github.xnovo3000.sjll.exception.ConfigurationErrorException;
import io.github.xnovo3000.sjll.formatter.*;
import io.github.xnovo3000.sjll.outputprovider.ConsoleOutputProvider;
import io.github.xnovo3000.sjll.outputprovider.FileOutputProvider;
import io.github.xnovo3000.sjll.outputprovider.OutputProvider;

import java.io.FileNotFoundException;
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
			// Get the configuration
			Map<?, ?> configurationTarget = (Map<?, ?>) objectConfigurationTarget;
			// Create the required fields
			String name = (String) configurationTarget.get("name");
			String provider = (String) configurationTarget.get("provider");
			String socket = (String) configurationTarget.get("socket");
			Integer level = (Integer) configurationTarget.get("level");
			List<LogFormatter> formats = generateFormattersByString((String) configurationTarget.get("format"));
			// Create the provider
			OutputProvider outputProvider;
			switch (provider) {
				case "console":
					switch (socket) {
						case "stdout":
							outputProvider = ConsoleOutputProvider.getStdoutInstance();
							break;
						case "stderr":
							outputProvider = ConsoleOutputProvider.getStderrInstance();
							break;
						default:
							throw new LogConfigurationError("Target '" + name + "' -> Provider '" + provider + "' -> Socket '" + socket + "' is invalid");
					}
					break;
				case "file":
					try {
						outputProvider = new FileOutputProvider(socket);
					} catch (FileNotFoundException e) {
						throw new LogConfigurationError("Target '" + name + "' -> Provider '" + provider + "' -> Socket '" + socket + "': cannot create/use this file");
					}
					break;
				case "network":
					throw new LogConfigurationError("Target '" + name + "' -> Provider '" + provider + "' has not been implemented already");
				default:
					throw new LogConfigurationError("Target '" + name + "' -> Provider '" + provider + "' is invalid");
			}
			// Add the target to the list
			logTargets.add(new ILogTarget(formatters, outputProvider, level));
		}
	}
	
	private void generateLoggers() {
	
	}
	
	/* TODO: Create better formatter generator */
	private List<LogFormatter> generateFormattersByString(String value) {
		final List<LogFormatter> formatters = new ArrayList<>();
		StringBuilder activeStaticString = new StringBuilder();
		char oldChar = '\0';
		for (char x : value.toCharArray()) {
			if (x == '%') {
				// Push the string formatter and start custom formatter
				if (activeStaticString.length() > 0) {
					formatters.add(new StaticStringFormatter(activeStaticString.toString()));
				}
				activeStaticString.setLength(0);
			} else if (oldChar == '%') {
				switch (x) {
					case 'd':
						formatters.add(InstantFormatter.getInstance());
						break;
					case 't':
						formatters.add(ThreadNameFormatter.getInstance());
						break;
					case 'l':
						formatters.add(SingleCharacterLevelFormatter.getInstance());
						break;
					case 'L':
						formatters.add(LevelFormatter.getInstance());
						break;
					case 'c':
						formatters.add(CallerFormatter.getInstance());
						break;
					case 'm':
						formatters.add(MessageFormatter.getInstance());
						break;
					default:
						throw new LogConfigurationError("%" + x + " in formats not found");  // TODO: Make better
				}
			} else {
				activeStaticString.append(x);
			}
			oldChar = x;
		}
		if (activeStaticString.length() > 0) {
			formatters.add(new StaticStringFormatter(activeStaticString.toString()));
		}
		return formatters;
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
