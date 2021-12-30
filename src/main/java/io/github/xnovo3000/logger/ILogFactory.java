package io.github.xnovo3000.logger;

import io.github.xnovo3000.LogFactory;
import io.github.xnovo3000.LogTarget;
import io.github.xnovo3000.Logger;
import io.github.xnovo3000.sjll.exception.ConfigurationErrorException;
import io.github.xnovo3000.sjll.exception.ConfigurationFileNotFoundException;
import io.github.xnovo3000.sjll.exception.LoggerNotFoundException;
import io.github.xnovo3000.sjll.formatter.*;
import io.github.xnovo3000.sjll.outputprovider.ConsoleOutputProvider;
import io.github.xnovo3000.sjll.outputprovider.FileOutputProvider;
import io.github.xnovo3000.sjll.outputprovider.OutputProvider;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ILogFactory implements LogFactory {
	
	/* Singleton pattern */
	
	public static final ILogFactory INSTANCE = new ILogFactory();
	
	/* Class implementation */
	
	private ILogFactory() {}
	
	private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
	private final List<ScheduledFuture<?>> runningFutures = new ArrayList<>();
	private final Map<String, Logger> loggers = new HashMap<>();
	private final Map<String, LogTarget> logTargets = new HashMap<>();
	
	boolean initialized = false;
	
	public synchronized Logger getLoggerImpl(String key) throws LoggerNotFoundException {
		// Initialize if not. This method is called on static synchronized: no problem at all!
		if (!initialized) {
			initialize();
		}
		// Try to get the logger
		Logger logger = loggers.get(key);
		if (logger == null) {
			throw new LoggerNotFoundException(key);
		}
		return logger;
	}
	
	// TODO: Initialize the LogFactory
	private void initialize() throws ConfigurationFileNotFoundException, ConfigurationErrorException, JSONException {
		// Get the configuration
		JSONObject configurationRoot = loadConfiguration();
		// Initialize the targets
		JSONArray jsonTargetArray = configurationRoot.getJSONArray("targets");
		for (Object targetObject : jsonTargetArray) {
			if (targetObject.getClass() != JSONObject.class) {
				throw new ConfigurationErrorException("A target must be a JSON object");
			}
			generateTarget((JSONObject) targetObject);
		}
		// Initialize the loggers
		JSONArray jsonLoggerArray = configurationRoot.getJSONArray("loggers");
		for (Object loggerObject : jsonLoggerArray) {
			if (loggerObject.getClass() != JSONObject.class) {
				throw new ConfigurationErrorException("A logger must be a JSON object");
			}
			generateLogger((JSONObject) loggerObject);
		}
		// Start the services
		for (Map.Entry<String, LogTarget> entry : logTargets.entrySet()) {
			runningFutures.add(executorService.scheduleAtFixedRate(entry.getValue(), 0, 1, TimeUnit.SECONDS));
		}
		// Set initialized
		initialized = true;
	}
	
	private JSONObject loadConfiguration() throws ConfigurationFileNotFoundException, JSONException {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CONFIGURATION_FILE_PATH);
		if (inputStream == null) {
			throw new ConfigurationFileNotFoundException();
		}
		String stringJson = new BufferedReader(new InputStreamReader(inputStream))
			.lines().parallel().collect(Collectors.joining("\n"));
		return new JSONObject(stringJson);
	}
	
	private void generateTarget(JSONObject jsonTargetObject) throws ConfigurationErrorException, JSONException {
		// Get JSON data
		String name = jsonTargetObject.getString("name");
		String type = jsonTargetObject.getString("type");
		String format = jsonTargetObject.getString("format");
		// Generate the OutputProvider
		final OutputProvider outputProvider;
		switch (type) {
			case "console":
				outputProvider = ConsoleOutputProvider.INSTANCE;
				break;
			case "file":
				String fileName = jsonTargetObject.getString("filename");
				try {
					outputProvider = new FileOutputProvider(fileName);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					throw new ConfigurationErrorException("The file at path \"" + fileName + "\" cannot be opened");
				}
				break;
			case "network":
				throw new ConfigurationErrorException("The network output is not supported yet");
			default:
				throw new ConfigurationErrorException("The \"type\" key in the target \"" + name + "\" is invalid");
		}
		// Generate the formats
		logTargets.put(name, new ILogTarget(getFormattersByString(format), outputProvider));
	}
	
	private void generateLogger(JSONObject jsonLoggerObject) throws ConfigurationErrorException, JSONException {
		String name = jsonLoggerObject.getString("name");
		List<LogTarget> loggerTargets = new ArrayList<>();
		JSONArray jsonTargetStringArray = jsonLoggerObject.getJSONArray("targets");
		for (Object jsonTargetObject : jsonTargetStringArray) {
			if (jsonTargetObject.getClass() != String.class) {
				throw new ConfigurationErrorException("Targets in the logger \"" + name + "\" must be strings");
			}
			LogTarget logTargetFound = logTargets.get(jsonTargetObject);
			if (logTargetFound == null) {
				throw new ConfigurationErrorException(
					"Target \"" + jsonTargetObject + "\" in the logger \"" + name + "\" not found");
			}
			loggerTargets.add(logTargetFound);
		}
		loggers.put(name, new ILogger(loggerTargets));
	}
	
	private List<LogFormatter> getFormattersByString(String value) {
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
						formatters.add(DateTimeFormatter.INSTANCE);
						break;
					case 't':
						formatters.add(ThreadNameFormatter.INSTANCE);
						break;
					case 'l':
						formatters.add(new LevelFormatter(true));
						break;
					case 'L':
						formatters.add(new LevelFormatter(false));
						break;
					case 'c':
						formatters.add(CallerFormatter.INSTANCE);
						break;
					case 'm':
						formatters.add(MessageFormatter.INSTANCE);
						break;
					default:
						throw new ConfigurationErrorException("Format error");
				}
			} else {
				activeStaticString.append(x);
			}
			oldChar = x;
		}
		return formatters;
	}
	
}
