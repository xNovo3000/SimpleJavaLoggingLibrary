package io.github.xnovo3000.sjll.old;

import io.github.xnovo3000.sjll.formatter.DateTimeFormatter;
import io.github.xnovo3000.sjll.formatter.StaticStringFormatter;
import io.github.xnovo3000.sjll.formatter.ThreadNameFormatter;
import io.github.xnovo3000.sjll.outputprovider.ConsoleOutputProvider;
import io.github.xnovo3000.sjll.outputprovider.FileOutputProvider;
import io.github.xnovo3000.sjll.outputprovider.OutputProvider;
import io.github.xnovo3000.sjll.exception.ConfigurationErrorException;
import io.github.xnovo3000.sjll.exception.ConfigurationFileNotFoundException;
import io.github.xnovo3000.sjll.exception.LoggerNotFoundException;
import io.github.xnovo3000.sjll.exception.TargetNotFoundException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class LogFactory implements AutoCloseable {
	
	/* Thread-safe singleton pattern */
	
	private static LogFactory INSTANCE;
	
	public static synchronized Logger getLogger(String key) throws LoggerNotFoundException {
		// The first call initializes it
		if (INSTANCE == null) {
			try {
				INSTANCE = new LogFactory();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// Get the required logger
		return INSTANCE.getLoggerImpl(key);
	}
	
	/* Utils */
	
	private static final String CONFIG_FILE_PATH = "SimpleJavaLoggingLibrary/Config.json";
	
	/* The class implementation */
	
	private final Map<String, Logger> loggers;
	private final Map<String, LogTarget> targets;
	private final List<Thread> activatedThreads;
	
	private LogFactory() throws ConfigurationFileNotFoundException, ConfigurationErrorException, FileNotFoundException {
		// Create the references
		loggers = new HashMap<>();
		targets = new HashMap<>();
		activatedThreads = new ArrayList<>();
		// Get the JSON
		InputStream is = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE_PATH);
		if (is == null) {
			throw new ConfigurationFileNotFoundException();
		}
		String stringJson = new BufferedReader(new InputStreamReader(is))
			.lines().parallel().collect(Collectors.joining("\n"));
		JSONObject jsonObject = new JSONObject(stringJson);
		JSONArray myTargets = jsonObject.getJSONArray("targets");
		// Create the targets using information in the json
		for (Object oTarget : myTargets) {
			JSONObject target = (JSONObject) oTarget;
			String name = target.getString("name");
			if (targets.containsKey(name)) {
				throw new ConfigurationErrorException("Duplicate name in \"targets\": " + name);
			}
			OutputProvider outputProvider;
			switch (target.getString("type")) {
				case "console":
					outputProvider = new ConsoleOutputProvider();
					break;
				case "file":
					outputProvider = new FileOutputProvider(target.getString("filename"));
					break;
				/* TODO: Add NetworkOutputProvider */
				default:
					throw new ConfigurationErrorException("Wrong configuration type for key: " + name);
			}
			targets.put(name, new LogTarget(outputProvider, getFormattersByString(target.getString("format"))));
		}
		// Create the loggers
		JSONArray myLoggers = jsonObject.getJSONArray("loggers");
		for (Object oLogger : myLoggers) {
			JSONObject logger = (JSONObject) oLogger;
			String name = logger.getString("name");
			if (loggers.containsKey(name)) {
				throw new ConfigurationErrorException("Duplicate name in \"loggers\": " + name);
			}
			JSONArray targetsInLogger = logger.getJSONArray("targets");
			List<LogTarget> finalTargets = new ArrayList<>();
			for (int j = 0; j < targetsInLogger.length(); j++) {
				String targetName = targetsInLogger.getString(0);
				if (!targets.containsKey(targetName)) {
					throw new TargetNotFoundException(targetName);
				}
				finalTargets.add(targets.get(targetName));
			}
			loggers.put(name, new ILogger(finalTargets));
		}
		// Enable the target threads
		targets.forEach((n, t) -> {
			Thread t1 = new Thread(t);
			t1.start();
			activatedThreads.add(t1);
		});
	}
	
	private Logger getLoggerImpl(String key) throws LoggerNotFoundException {
		Logger logger = loggers.get(key);
		if (logger == null) {
			throw new LoggerNotFoundException(key);
		}
		return logger;
	}
	
	private List<Formatter> getFormattersByString(String value) {
		// TODO: Add the formatters
		final List<Formatter> formatters = new ArrayList<>();
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
				// TODO: Check for some formats
				switch (x) {
					case 'd':
						formatters.add(new DateTimeFormatter());
						break;
					case 't':
						formatters.add(new ThreadNameFormatter());
						break;
				}
			} else {
				activeStaticString.append(x);
			}
			oldChar = x;
		}
		return formatters;
	}
	
	@Override
	public void close() throws Exception {
		for (Map.Entry<String, LogTarget> target : targets.entrySet()) {
			target.getValue().close();
		}
		for (Thread activatedThread : activatedThreads) {
			activatedThread.join();
		}
		activatedThreads.clear();
		targets.clear();
		loggers.clear();
	}
	
}
