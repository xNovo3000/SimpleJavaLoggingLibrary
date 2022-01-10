package io.github.xnovo3000.sjll.outputprovider;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Objects;

/**
 * <p>File output implementation of the provider</p>
 *
 * @since 1.0
 * @author xNovo3000
 */
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
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		FileOutputProvider that = (FileOutputProvider) o;
		return fileOutputStream.equals(that.fileOutputStream);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(fileOutputStream);
	}
	
}
