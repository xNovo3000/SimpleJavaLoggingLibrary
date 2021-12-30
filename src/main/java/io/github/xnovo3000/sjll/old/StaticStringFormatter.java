package io.github.xnovo3000.sjll.old;

public class StaticStringFormatter implements Formatter {
	
	private final String string;
	
	public StaticStringFormatter(String value) {
		this.string = value;
	}
	
	@Override
	public StringBuilder onFormatRequired(StringBuilder stringBuilder, LogMessage logMessage) {
		return stringBuilder.append(string);
	}
	
}
