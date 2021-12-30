package io.github.xnovo3000.sjll.formatter;

import io.github.xnovo3000.sjll.data.LogMessage2;

public interface LogFormatter2 {
	
	void onFormat(StringBuilder current, LogMessage2 logMessage);
	
}
