package io.github.xnovo3000.sjll.data;

public class Null {
	
	public static final Null INSTANCE = new Null();
	
	private Null() {}
	
	@Override
	public boolean equals(Object obj) {
		return false;
	}
	
	@Override
	public int hashCode() {
		return 0;
	}
	
	@Override
	public String toString() {
		return "null";
	}
	
}
