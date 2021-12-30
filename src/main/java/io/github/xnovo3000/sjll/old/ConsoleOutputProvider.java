package io.github.xnovo3000.sjll.old;

import io.github.xnovo3000.sjll.outputprovider.OutputProvider;

import java.io.OutputStream;

public class ConsoleOutputProvider implements OutputProvider {
	
	@Override
	public OutputStream getOutputStream() {
		return System.out;
	}
	
	@Override
	public void close() {
		System.out.flush();
		System.out.close();
	}
	
}
