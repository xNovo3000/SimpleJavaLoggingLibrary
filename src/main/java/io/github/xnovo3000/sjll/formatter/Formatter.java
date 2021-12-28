package io.github.xnovo3000.sjll.formatter;

import io.github.xnovo3000.sjll.data.LogMessage;

public interface Formatter {
	
	StringBuilder onFormatRequired(StringBuilder stringBuilder, LogMessage logMessage);
	
}
