package io.github.xnovo3000.sjll.outputprovider;

import java.io.OutputStream;

/**
 * <p>Used to manage the OutputStream</p>
 *
 * @since 1.0
 * @author xNovo3000
 */
public interface OutputProvider extends AutoCloseable {
	
	/**
	 * <p>OutputStream getter</p>
	 *
	 * @return The OutputStream
	 */
	OutputStream getOutputStream();
	
}
