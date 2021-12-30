package io.github.xnovo3000.logger;

import io.github.xnovo3000.LogFactory2;
import io.github.xnovo3000.LogTarget2;
import io.github.xnovo3000.Logger2;
import io.github.xnovo3000.sjll.exception.ConfigurationErrorException;
import io.github.xnovo3000.sjll.exception.ConfigurationFileNotFoundException;
import io.github.xnovo3000.sjll.exception.LoggerNotFoundException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

public class ILogFactory implements LogFactory2 {
	
	/* Singleton pattern */
	
	public static final ILogFactory INSTANCE = new ILogFactory();
	
	/* Class implementation */
	
	private ILogFactory() {}
	
	private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
	private final List<ScheduledFuture<?>> runningFutures = new ArrayList<>();
	private final Map<String, Logger2> loggers = new HashMap<>();
	private final Map<String, LogTarget2> logTargets = new HashMap<>();
	
	boolean initialized = false;
	
	public synchronized Logger2 getLoggerImpl(String key) throws LoggerNotFoundException {
		// Initialize if not. This method is called on static synchronized: no problem at all!
		if (!initialized) {
			initialize();
		}
		// Try to get the logger
		Logger2 logger = loggers.get(key);
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
		// TODO: Implement
		String name = jsonTargetObject.getString("name");
		String type = jsonTargetObject.getString("type");
		String format = jsonTargetObject.getString("format");
	}
	
	private void generateLogger(JSONObject jsonLoggerObject) throws ConfigurationErrorException, JSONException {
		// TODO: Implement
		String name = jsonLoggerObject.getString("name");
		List<LogTarget2> loggerTargets = new ArrayList<>();
		JSONArray jsonTargetStringArray = jsonLoggerObject.getJSONArray("targets");
		for (Object jsonTargetObject : jsonTargetStringArray) {
			if (jsonTargetObject.getClass() != String.class) {
				throw new ConfigurationErrorException("Targets in the logger \"" + name + "\" must be strings");
			}
			LogTarget2 logTargetFound = logTargets.get(jsonTargetObject);
			if (logTargetFound == null) {
				throw new ConfigurationErrorException(
					"Target \"" + jsonTargetObject + "\" in the logger \"" + name + "\" not found");
			}
			loggerTargets.add(logTargetFound);
		}
		
	}
	
}
