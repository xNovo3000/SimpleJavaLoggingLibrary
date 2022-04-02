package io.github.xnovo3000.sjll.outputprovider;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * <p>Console output implementation of the provider</p>
 *
 * @since 1.0
 * @author xNovo3000
 */
public class ConsoleOutputProvider implements OutputProvider {
	
	/* Singleton pattern */
	
	private static ConsoleOutputProvider stdoutOutputProvider;
	private static ConsoleOutputProvider stderrOutputProvider;
	
	public static synchronized ConsoleOutputProvider getStdoutInstance() {
		if (stdoutOutputProvider == null) {
			stdoutOutputProvider = new ConsoleOutputProvider(System.out);
		}
		return stdoutOutputProvider;
	}
	
	public static synchronized ConsoleOutputProvider getStderrInstance() {
		if (stderrOutputProvider == null) {
			stderrOutputProvider = new ConsoleOutputProvider(System.err);
		}
		return stderrOutputProvider;
	}
	
	/* Class */
	
	private final PrintStream printStream;
	
	private ConsoleOutputProvider(PrintStream printStream) {
		this.printStream = printStream;
	}
	
	@Override
	public OutputStream getOutputStream() {
		return printStream;
	}
	
	@Override
	public void close() {
		printStream.close();
	}
	
	@Override
	public boolean equals(Object obj) {
		return this == obj;  // Singleton class
	}
	
	@Override
	public int hashCode() {
		return printStream.hashCode();
	}
	
}
