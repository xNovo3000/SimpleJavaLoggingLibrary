package io.github.xnovo3000.sjll.data;

import java.time.Instant;
import java.util.Objects;

public class LogMessage {
	
	private final Level level;
	private final String caller;
	private final String message;
	private final String threadName;
	private final Instant timestamp;
	
	public LogMessage(Level level, String caller, String message) {
		this.level = level;
		this.caller = caller;
		this.message = message;
		this.threadName = Thread.currentThread().getName();
		this.timestamp = Instant.now();
	}
	
	public Level getLevel() {
		return level;
	}
	
	public String getCaller() {
		return caller;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getThreadName() {
		return threadName;
	}
	
	public Instant getTimestamp() {
		return timestamp;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		LogMessage that = (LogMessage) o;
		return level == that.level && caller.equals(that.caller) && message.equals(that.message) &&
			threadName.equals(that.threadName) && timestamp.equals(that.timestamp);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(level, caller, message, threadName, timestamp);
	}
	
}
