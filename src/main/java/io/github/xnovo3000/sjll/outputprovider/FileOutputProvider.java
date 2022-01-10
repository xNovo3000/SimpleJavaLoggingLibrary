package io.github.xnovo3000.sjll.outputprovider;

import java.io.File;
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
	
	/**
	 * <p>Default constructor</p>
	 *
	 * @param filePath The path of the output file
	 */
	public FileOutputProvider(String filePath) throws FileNotFoundException {
		String[] paths = filePath.split("/");
		StringBuilder fullPathBuilder = new StringBuilder();
		for (int i = 0; i < paths.length - 1; i++) {
			fullPathBuilder.append(paths[i]).append("/");
		}
		String fullPath = fullPathBuilder.toString();
		File folders = new File(fullPath);
		if (!folders.exists() && !folders.mkdirs()) {
			throw new FileNotFoundException("Cannot create folder " + folders);
		}
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
