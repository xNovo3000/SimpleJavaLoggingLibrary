package io.github.xnovo3000.sjll.outputprovider;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class FileOutputProvider implements OutputProvider {
	
	private final FileOutputStream fileOutputStream;
	
	public FileOutputProvider(String filePath) throws FileNotFoundException {
		fileOutputStream = new FileOutputStream(filePath, true);
	}
	
	@Override
	public OutputStream getOutputStream() {
		return fileOutputStream;
	}
	
	@Override
	public void close() throws Exception {
		fileOutputStream.close();
	}
	
}
