package io.github.xnovo3000.sjll.outputprovider;

import java.io.OutputStream;

public interface OutputProvider extends AutoCloseable {
	
	OutputStream getOutputStream();
	
}
