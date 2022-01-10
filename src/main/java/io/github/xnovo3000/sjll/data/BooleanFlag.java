package io.github.xnovo3000.sjll.data;

public final class BooleanFlag {
	
	private boolean value = false;
	
	public synchronized void setFlag() {
		value = true;
	}
	
	 public synchronized boolean getValue() {
		 return !value;
	 }
	
}
