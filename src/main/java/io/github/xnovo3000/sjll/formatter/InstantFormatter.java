package io.github.xnovo3000.sjll.formatter;

import io.github.xnovo3000.sjll.data.LogMessage;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class InstantFormatter implements LogFormatter {
	
	public static final InstantFormatter INSTANCE = new InstantFormatter();
	
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS")
		.withLocale(Locale.getDefault()).withZone(ZoneId.of("UTC"));
	
	@Override
	public void onFormat(StringBuilder current, LogMessage logMessage) {
		current.append(FORMATTER.format(logMessage.getTimestamp()));
	}
	
}
