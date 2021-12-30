package io.github.xnovo3000.sjll.old;

import io.github.xnovo3000.sjll.data.Level;

import java.time.Instant;
import java.util.Objects;

public class LogMessage {
	
	private final Level level;
	private final String message;
	private final String threadName;
	private final Instant timestamp;
	
	public LogMessage(Level level, String message) {
		this.level = level;
		this.message = message;
		this.threadName = Thread.currentThread().getName();
		this.timestamp = Instant.now();
	}
	
	public Level getLevel() {
		return level;
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
		return level == that.level && Objects.equals(message, that.message) &&
			Objects.equals(threadName, that.threadName) && Objects.equals(timestamp, that.timestamp);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(level, message, threadName, timestamp);
	}
	
}
