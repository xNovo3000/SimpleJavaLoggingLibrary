package io.github.xnovo3000.sjll.data;

/**
 * <p>Indicates the severity of the log message</p>
 *
 * @since 1.0
 * @author xNovo3000
 */
public enum Level {
	
	/**
	 * <p>Level DEBUG for a log message. Importance 10</p>
	 */
	DEBUG("DEBUG", 10),
	/**
	 * <p>Level INFO for a log message. Importance 20</p>
	 */
	INFO("INFO", 20),
	/**
	 * <p>Level WARNING for a log message. Importance 30</p>
	 */
	WARNING("WARNING", 30),
	/**
	 * <p>Level ERROR for a log message. Importance 40</p>
	 */
	ERROR("ERROR", 40);
	
	private final String name;
	private final char singleChar;
	private final int importance;
	
	Level(String name, int importance) {
		this.name = name;
		this.singleChar = name.charAt(0);
		this.importance = importance;
	}
	
	/**
	 * @return The first character of the enum name
	 */
	public char getSingleChar() {
		return singleChar;
	}
	
	/**
	 * @return The name of the enum
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return The importance level of the enum
	 */
	public int getImportance() {
		return importance;
	}
	
	@Override
	public String toString() {
		return "Level{" +
			"name='" + name + '\'' +
			", singleChar=" + singleChar +
			", importance=" + importance +
			'}';
	}
	
}
