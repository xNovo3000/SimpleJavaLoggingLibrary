package io.github.xnovo3000.sjll;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.github.xnovo3000.sjll.error.LogConfigurationError;
import io.github.xnovo3000.sjll.implementation.v2.ILogProvider;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public abstract class LogProvider {
	
	/* Singleton/Provider pattern */
	
	private static LogProvider PROVIDER;
	
	public static Logger getLogger(Class<?> clazz) {
		return getLogger(clazz.getSimpleName());
	}
	
	public static synchronized Logger getLogger(String name) {
		// Singleton init
		if (PROVIDER == null) {
			Map<?, ?> configurations = getConfigurations();
			try {
				PROVIDER = getLogProvider(configurations);
			} catch (Exception e) {
				PROVIDER = new ILogProvider(configurations);
			}
		}
		// Return required logger
		return PROVIDER.getLoggerImpl(name);
	}
	
	private static Map<?, ?> getConfigurations() {
		// Get configurations from SJLL.yaml
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		InputStream resource = LogProvider.class.getClassLoader().getResourceAsStream("SJLL.yaml");
		try {
			return mapper.readValue(resource, Map.class);
		} catch (IOException e) {
			throw new LogConfigurationError("The configuration file is not a valid YAML file");
		}
	}
	
	private static LogProvider getLogProvider(Map<?, ?> configurations) throws ClassNotFoundException,
		NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException
	{
		String factoryClassName = (String) configurations.get("provider");
		Class<?> factoryClass = Class.forName(factoryClassName);
		Constructor<?> constructor = factoryClass.getConstructor(Map.class);
		return (LogProvider) constructor.newInstance(configurations);
	}
	
	/* Class definition */
	
	protected Map<?, ?> configurations;
	
	public LogProvider(Map<?, ?> configurations) {
		this.configurations = configurations;
	}
	
	public abstract Logger getLoggerImpl(String name);
	
}
