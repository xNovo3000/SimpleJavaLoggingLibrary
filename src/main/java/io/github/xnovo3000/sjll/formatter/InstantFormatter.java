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
	
	/* Singleton pattern */
	
	private static InstantFormatter instantFormatter;
	
	public static synchronized InstantFormatter getInstance() {
		if (instantFormatter == null) {
			instantFormatter = new InstantFormatter();
		}
		return instantFormatter;
	}
	
	/* Class */
	
	private final DateTimeFormatter FORMATTER;
	
	private InstantFormatter() {
		FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS")
			.withLocale(Locale.getDefault()).withZone(ZoneId.systemDefault());
	}
	
	@Override
	public void format(StringBuilder current, LogMessage logMessage) {
		current.append(FORMATTER.format(logMessage.getTimestamp()));
	}
	
}
