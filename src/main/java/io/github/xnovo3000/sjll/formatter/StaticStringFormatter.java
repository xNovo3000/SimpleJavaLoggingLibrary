package io.github.xnovo3000.sjll.formatter;

import io.github.xnovo3000.sjll.data.LogMessage;

public class StaticStringFormatter implements LogFormatter {
	
	private final String string;
	
	public StaticStringFormatter(String value) {
		this.string = value;
	}
	
	@Override
	public void onFormat(StringBuilder current, LogMessage logMessage) {
		current.append(string);
	}
	
}
