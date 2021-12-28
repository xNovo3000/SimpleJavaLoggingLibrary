package io.github.xnovo3000.sjll.outputprovider;

import java.io.OutputStream;

public class ConsoleOutputProvider implements OutputProvider {
	
	@Override
	public OutputStream getOutputStream() {
		return System.out;
	}
	
	@Override
	public void close() throws Exception {
		System.out.close();
	}
	
}
