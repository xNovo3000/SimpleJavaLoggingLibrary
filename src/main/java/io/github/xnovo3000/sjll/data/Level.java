package io.github.xnovo3000.sjll.data;

public enum Level {
	
	DEBUG("DEBUG"),
	INFO("INFO"),
	WARNING("WARNING"),
	ERROR("ERROR");
	
	private final String name;
	
	Level(String name) {
		this.name = name;
	}
	
	public char getFirstChar() {
		return name.charAt(0);
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
