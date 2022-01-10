package io.github.xnovo3000.sjll.formatter;

import io.github.xnovo3000.sjll.data.LogMessage;

/**
 * <p>Appends a string</p>
 *
 * @since 1.0
 * @author xNovo3000
 */
public class StaticStringFormatter implements LogFormatter {
	
	private final String string;
	
	/**
	 * <p>Default constructor</p>
	 *
	 * @param value The value of the String
	 */
	public StaticStringFormatter(String value) {
		this.string = value;
	}
	
	@Override
	public void format(StringBuilder current, LogMessage logMessage) {
		current.append(string);
	}
	
}
