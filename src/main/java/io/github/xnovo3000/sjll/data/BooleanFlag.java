package io.github.xnovo3000.sjll.data;

/**
 * <p>Simple, thread-safe boolean flag; cannot be reset</p>
 *
 * @since 1.0
 * @author xNovo3000
 */
public final class BooleanFlag {
	
	private boolean value = false;
	
	/**
	 * <p>Sets the flag to true</p>
	 */
	public synchronized void setFlag() {
		value = true;
	}
	
	/**
	 * @return The value of the current flag
	 */
	public synchronized boolean getValue() {
		 return value;
	 }
	
}
