package io.github.xnovo3000.sjll.outputprovider;

import java.io.OutputStream;

public class ConsoleOutputProvider implements OutputProvider {
	
	public static final ConsoleOutputProvider INSTANCE = new ConsoleOutputProvider();
	
	@Override
	public OutputStream getOutputStream() {
		return System.out;
	}
	
	@Override
	public void close() {
		System.out.close();
	}
	
}
