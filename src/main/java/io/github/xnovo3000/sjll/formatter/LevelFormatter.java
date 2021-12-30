package io.github.xnovo3000.sjll.formatter;

import io.github.xnovo3000.sjll.data.LogMessage2;
import io.github.xnovo3000.sjll.old.Formatter;
import io.github.xnovo3000.sjll.old.LogMessage;

public class LevelFormatter implements LogFormatter2 {
	
	private final boolean singleCharacter;
	
	public LevelFormatter(boolean singleCharacter) {
		this.singleCharacter = singleCharacter;
	}
	
	@Override
	public void onFormat(StringBuilder current, LogMessage2 logMessage) {
		if (singleCharacter) {
			current.append(logMessage.getLevel().getFirstChar());
		} else {
			current.append(logMessage.getLevel());
		}
	}
	
}
