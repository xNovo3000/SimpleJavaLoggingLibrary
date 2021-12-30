package io.github.xnovo3000.sjll.old;

public class LevelFormatter implements Formatter {
	
	private final boolean singleCharacter;
	
	public LevelFormatter(boolean singleCharacter) {
		this.singleCharacter = singleCharacter;
	}
	
	@Override
	public StringBuilder onFormatRequired(StringBuilder stringBuilder, LogMessage logMessage) {
		if (singleCharacter) {
			return stringBuilder.append(logMessage.getLevel().getFirstChar());
		} else {
			return stringBuilder.append(logMessage.getLevel());
		}
	}
	
}
