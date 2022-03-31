package io.github.xnovo3000.sjll;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.github.xnovo3000.sjll.exception.ConfigurationErrorException;
import io.github.xnovo3000.sjll.exception.ConfigurationFileNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public abstract class LogFactory {
	
	/* Static definitions */
	
	private static LogFactory FACTORY;
	
	public static synchronized Logger getLogger(String name) {
		// One-time init
		if (FACTORY == null) {
			// Load configurations
			Map<?, ?> configurations = getConfigurations();
			// Try to load custom logger if exists
			try {
				FACTORY = getLogFactory(configurations);
			} catch (Exception e) {
				// TODO: Instantiate to the default class
			}
		}
		// Return the desired logger
		return FACTORY.getInnerLogger(name);
	}
	
	public static synchronized Logger getLogger(Class<?> clazz) {
		return getLogger(clazz.getSimpleName());
	}
	
	private static Map<?, ?> getConfigurations() {
		// Get configurations from SJLL.yaml
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		InputStream resource = LogFactory.class.getClassLoader().getResourceAsStream("SJLL.yaml");
		if (resource == null) {
			throw new ConfigurationFileNotFoundException();
		}
		try {
			return mapper.readValue(resource, Map.class);
		} catch (IOException e) {
			throw new ConfigurationErrorException("The configuration file is not a valid YAML file");
		}
	}
	
	private static LogFactory getLogFactory(Map<?, ?> configurations) throws ClassNotFoundException,
		NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException
	{
		String factoryClassName = (String) configurations.get("factory");
		Class<?> factoryClass = Class.forName(factoryClassName);
		Constructor<?> constructor = factoryClass.getConstructor(Map.class);
		return (LogFactory) constructor.newInstance(configurations);
	}
	
	/* Class definitions */
	
	private final Map<?, ?> configurations;
	
	protected LogFactory(Map<?, ?> configurations) {
		this.configurations = configurations;
	}
	
	protected abstract Logger createLogger(String name);
	
	protected abstract LogTarget createLogTarget(String name);
	
	protected abstract List<LogFormatter> createLogFormatters(String formatString);
	
	public final Logger getInnerLogger(String name) {
		return null;
	}
	
}
