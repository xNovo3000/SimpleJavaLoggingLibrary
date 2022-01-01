package io.github.xnovo3000.sjll.outputprovider;

import java.io.OutputStream;

public class ConsoleOutputProvider implements OutputProvider {
	
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
