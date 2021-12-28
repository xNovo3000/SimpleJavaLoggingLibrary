package io.github.xnovo3000.sjll;

import io.github.xnovo3000.sjll.exception.ConfigurationFileNotFoundException;
import io.github.xnovo3000.sjll.exception.LoggerNotFoundException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class LogFactory {
	
	/* Thread-safe singleton pattern */
	
	private static LogFactory INSTANCE;
	
	static {
		try {
			INSTANCE = new LogFactory();
		} catch (ConfigurationFileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Logger getLogger(String key) throws LoggerNotFoundException {
		return INSTANCE.getLoggerImpl(key);
	}
	
	/* Utils */
	
	private static final String CONFIG_FILE_PATH = "sjll/config.json";
	
	/* The class implementation */
	
	private final Map<String, Logger> loggers;
	private final Map<String, LogTarget> targets;
	
	private LogFactory() throws ConfigurationFileNotFoundException {
		// Create the references
		loggers = new HashMap<>();
		targets = new HashMap<>();
		// Get the JSON
		InputStream is = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE_PATH);
		if (is == null) {
			throw new ConfigurationFileNotFoundException();
		}
		String stringJson = new BufferedReader(new InputStreamReader(is))
			.lines().parallel().collect(Collectors.joining("\n"));
		JSONObject jsonObject = new JSONObject(stringJson);
		// TODO: Create the targets using information in the json
		// TODO: Create the loggers
		// TODO: Link the loggers with the targets
		// TODO: Enable the target threads
	}
	
	private Logger getLoggerImpl(String key) throws LoggerNotFoundException {
		Logger logger = loggers.get(key);
		if (logger == null) {
			throw new LoggerNotFoundException(key);
		}
		return logger;
	}
	
}
