package io.github.xnovo3000.sjll.formatter;

import io.github.xnovo3000.sjll.data.LogMessage;

public class DateTimeFormatter implements Formatter {
	
	private static final java.time.format.DateTimeFormatter FORMATTER =
		java.time.format.DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.nnnnnn");
	
	@Override
	public StringBuilder onFormatRequired(StringBuilder stringBuilder, LogMessage logMessage) {
		return stringBuilder.append(FORMATTER.format(logMessage.getTimestamp()));
	}
	
}
