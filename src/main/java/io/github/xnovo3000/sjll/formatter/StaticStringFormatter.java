package io.github.xnovo3000.sjll.formatter;

import io.github.xnovo3000.sjll.data.LogMessage2;
import io.github.xnovo3000.sjll.old.Formatter;
import io.github.xnovo3000.sjll.old.LogMessage;

public class StaticStringFormatter implements LogFormatter2 {
	
	private final String string;
	
	public StaticStringFormatter(String value) {
		this.string = value;
	}
	
	@Override
	public void onFormat(StringBuilder current, LogMessage2 logMessage) {
		current.append(string);
	}
	
}
