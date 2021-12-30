package io.github.xnovo3000.sjll.formatter;

import io.github.xnovo3000.sjll.data.LogMessage;

public class DateTimeFormatter implements LogFormatter {
	
	public static final DateTimeFormatter INSTANCE = new DateTimeFormatter();
	
	private static final java.time.format.DateTimeFormatter FORMATTER =
		java.time.format.DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.nnnnnn");
	
	@Override
	public void onFormat(StringBuilder current, LogMessage logMessage) {
		current.append(FORMATTER.format(logMessage.getTimestamp()));
	}
	
}
