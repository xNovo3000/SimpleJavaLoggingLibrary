package io.github.xnovo3000.sjll.formatter;

import io.github.xnovo3000.sjll.data.LogMessage;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * <p>Appends the timestamp of the log message</p>
 *
 * @since 1.0
 * @author xNovo3000
 */
public class InstantFormatter implements LogFormatter {
	
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS")
		.withLocale(Locale.getDefault()).withZone(ZoneId.of("UTC"));
	
	@Override
	public void format(StringBuilder current, LogMessage logMessage) {
		current.append(FORMATTER.format(logMessage.getTimestamp()));
	}
	
}
