package io.github.xnovo3000.sjll.data;

public enum Level {
	
	DEBUG("DEBUG", 10),
	INFO("INFO", 20),
	WARNING("WARNING", 30),
	ERROR("ERROR", 40);
	
	private final String name;
	private final int importance;
	
	Level(String name, int importance) {
		this.name = name;
		this.importance = importance;
	}
	
	public char getFirstChar() {
		return name.charAt(0);
	}
	
	public String getName() {
		return name;
	}
	
	public int getImportance() {
		return importance;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
