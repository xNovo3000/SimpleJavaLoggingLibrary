package io.github.xnovo3000.sjll.formatter;

import io.github.xnovo3000.sjll.data.LogMessage;

/**
 * <p>Appends the level of the log message</p>
 *
 * @since 1.0
 * @author xNovo3000
 */
public class LevelFormatter implements LogFormatter {
	
	/* Singleton pattern */
	
	private static LevelFormatter levelFormatter;
	
	public static synchronized LevelFormatter getInstance() {
		if (levelFormatter == null) {
			levelFormatter = new LevelFormatter();
		}
		return levelFormatter;
	}
	
	/* Class */
	
	private LevelFormatter() {}
	
	@Override
	public void format(StringBuilder current, LogMessage logMessage) {
		current.append(logMessage.getLevel().getName());
	}
	
}
