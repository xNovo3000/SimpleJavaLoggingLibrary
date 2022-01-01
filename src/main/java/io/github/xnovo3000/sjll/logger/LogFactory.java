package io.github.xnovo3000.sjll.logger;

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
import java.util.stream.Collectors;

public final class LogFactory {
	
	/* Static fields and performance optimizations */
	
	private static final LogFormatter FORMATTER_CALLER = new CallerFormatter();
	private static final LogFormatter FORMATTER_INSTANT = new InstantFormatter();
	private static final LogFormatter FORMATTER_MULTI_LEVEL = new LevelFormatter();
	private static final LogFormatter FORMATTER_MESSAGE = new MessageFormatter();
	private static final LogFormatter FORMATTER_SINGLE_LEVEL = new SingleCharacterLevelFormatter();
	private static final LogFormatter FORMATTER_THREAD_NAME = new ThreadNameFormatter();
	
	private static final OutputProvider OUTPUT_PROVIDER_CONSOLE = new ConsoleOutputProvider();
	
	public static final String CONFIGURATION_FILE_PATH = "SJLL/Config.json";
	
	/* Singleton pattern */
	
	private static final LogFactory INSTANCE = new LogFactory();
	
	public static synchronized Logger getLogger(String key) throws LoggerNotFoundException {
		return INSTANCE.getLoggerImpl(key);
	}
	
	/* Class implementation */
	
	private final Map<String, IILogTarget> logTargets = new HashMap<>();
	private final Map<String, IILogger> loggers = new HashMap<>();
	private final List<Thread> threads = new ArrayList<>();
	
	private boolean initialized = false;
	
	private LogFactory() {}
	
	public Logger getLoggerImpl(String key) throws LoggerNotFoundException {
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
		for (Map.Entry<String, IILogTarget> entry : logTargets.entrySet()) {
			Thread t1 = new Thread(entry.getValue(), entry.getKey() + "Thread");
			t1.start();
			threads.add(t1);
		}
		// Set shutdown hook for stopping threads and closing targets
		Runtime.getRuntime().addShutdownHook(new ShutdownHookThread(new ArrayList<>(logTargets.values()), threads));
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
				outputProvider = OUTPUT_PROVIDER_CONSOLE;
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
		logTargets.put(name, new IILogTarget(getFormattersByString(format), outputProvider));
	}
	
	private void generateLogger(JSONObject jsonLoggerObject) throws ConfigurationErrorException, JSONException {
		String name = jsonLoggerObject.getString("name");
		List<IILogTarget> loggerTargets = new ArrayList<>();
		JSONArray jsonTargetStringArray = jsonLoggerObject.getJSONArray("targets");
		for (Object jsonTargetObject : jsonTargetStringArray) {
			if (jsonTargetObject.getClass() != String.class) {
				throw new ConfigurationErrorException("Targets in the logger \"" + name + "\" must be strings");
			}
			IILogTarget logTargetFound = logTargets.get(jsonTargetObject);
			if (logTargetFound == null) {
				throw new ConfigurationErrorException(
					"Target \"" + jsonTargetObject + "\" in the logger \"" + name + "\" not found");
			}
			loggerTargets.add(logTargetFound);
		}
		loggers.put(name, new IILogger(loggerTargets));
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
						formatters.add(FORMATTER_INSTANT);
						break;
					case 't':
						formatters.add(FORMATTER_THREAD_NAME);
						break;
					case 'l':
						formatters.add(FORMATTER_SINGLE_LEVEL);
						break;
					case 'L':
						formatters.add(FORMATTER_MULTI_LEVEL);
						break;
					case 'c':
						formatters.add(FORMATTER_CALLER);
						break;
					case 'm':
						formatters.add(FORMATTER_MESSAGE);
						break;
					default:
						throw new ConfigurationErrorException("Format error");
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
	
}
