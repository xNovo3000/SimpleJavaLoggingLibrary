package io.github.xnovo3000.sjll.formatter;

import io.github.xnovo3000.sjll.data.LogMessage;

public class LevelFormatter implements Formatter {
	
	public enum Type {
		CHAR, LONG, UPPERCASE
	}
	
	private final Type type;
	
	public LevelFormatter(Type type) {
		this.type = type;
	}
	
	/* TODO: Should be optimized */
	
	@Override
	public StringBuilder onFormatRequired(StringBuilder stringBuilder, LogMessage logMessage) {
		switch (type) {
			case CHAR: return stringBuilder.append(logMessage.getLevel().getShortName());
			case LONG: return stringBuilder.append(logMessage.getLevel());
			case UPPERCASE: return stringBuilder.append(logMessage.getLevel().getLongNameUppercase());
			default: return stringBuilder;
		}
	}
	
}
