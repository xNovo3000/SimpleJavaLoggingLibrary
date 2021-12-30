package io.github.xnovo3000.sjll.formatter;

import io.github.xnovo3000.sjll.data.LogMessage;

public interface LogFormatter {
	
	void onFormat(StringBuilder current, LogMessage logMessage);
	
}
