package io.github.xnovo3000.sjll.outputprovider;

import java.io.OutputStream;

/**
 * <p>Console output implementation of the provider</p>
 *
 * @since 1.0
 * @author xNovo3000
 */
public class ConsoleOutputProvider implements OutputProvider {
	
	/* Singleton pattern */
	
	private static ConsoleOutputProvider consoleOutputProvider;
	
	public static synchronized ConsoleOutputProvider getInstance() {
		if (consoleOutputProvider == null) {
			consoleOutputProvider = new ConsoleOutputProvider();
		}
		return consoleOutputProvider;
	}
	
	/* Class */
	
	private ConsoleOutputProvider() {}
	
	@Override
	public OutputStream getOutputStream() {
		return System.out;
	}
	
	@Override
	public void close() {
		System.out.close();
	}
	
	@Override
	public boolean equals(Object obj) {
		return this == obj;  // Singleton class
	}
	
	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
	
}
