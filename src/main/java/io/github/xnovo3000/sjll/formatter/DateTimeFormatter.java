package io.github.xnovo3000.sjll.formatter;

import io.github.xnovo3000.sjll.data.LogMessage2;
import io.github.xnovo3000.sjll.old.Formatter;
import io.github.xnovo3000.sjll.old.LogMessage;

public class DateTimeFormatter implements LogFormatter2 {
	
	public static final DateTimeFormatter INSTANCE = new DateTimeFormatter();
	
	private static final java.time.format.DateTimeFormatter FORMATTER =
		java.time.format.DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.nnnnnn");
	
	@Override
	public void onFormat(StringBuilder current, LogMessage2 logMessage) {
		current.append(FORMATTER.format(logMessage.getTimestamp()));
	}
	
}
