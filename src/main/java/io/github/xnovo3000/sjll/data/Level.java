package io.github.xnovo3000.sjll.data;

public enum Level {
	
	DEBUG("Debug"),
	INFO("Info"),
	WARNING("Warning"),
	ERROR("Error");
	
	private final char shortName;
	private final String longName;
	private final String longNameUppercase;
	
	Level(String name) {
		this.shortName = name.charAt(0);
		this.longName = name;
		this.longNameUppercase = name.toUpperCase();
	}
	
	public char getShortName() {
		return shortName;
	}
	
	public String getLongName() {
		return longName;
	}
	
	public String getLongNameUppercase() {
		return longNameUppercase;
	}
	
	@Override
	public String toString() {
		return longName;
	}
	
}
