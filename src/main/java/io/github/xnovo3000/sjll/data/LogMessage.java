package io.github.xnovo3000.sjll.data;

import java.time.Instant;
import java.util.Objects;

/**
 * <p>A log message; contains all information about the message logged</p>
 *
 * @since 1.0
 * @author xNovo3000
 */
public class LogMessage {
	
	private final Level level;
	private final String caller;
	private final String message;
	private final String threadName;
	private final Instant timestamp;
	
	/**
	 * <p>Creates a new message</p>
	 *
	 * @param level The level of the message
	 * @param caller The caller of the message
	 * @param message The message
	 */
	public LogMessage(Level level, String caller, String message) {
		this.level = level;
		this.caller = caller;
		this.message = message;
		this.threadName = Thread.currentThread().getName();
		this.timestamp = Instant.now();
	}
	
	/**
	 * @return The level of the message
	 */
	public Level getLevel() {
		return level;
	}
	
	/**
	 * @return The caller of the message
	 */
	public String getCaller() {
		return caller;
	}
	
	/**
	 * @return The message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * @return The name of the thread generated the message
	 */
	public String getThreadName() {
		return threadName;
	}
	
	/**
	 * @return When the message was created
	 */
	public Instant getTimestamp() {
		return timestamp;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		LogMessage that = (LogMessage) o;
		return level == that.level &&
			Objects.equals(caller, that.caller) &&
			Objects.equals(message, that.message) &&
			Objects.equals(threadName, that.threadName) &&
			Objects.equals(timestamp, that.timestamp);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(level, caller, message, threadName, timestamp);
	}
	
	@Override
	public String toString() {
		return "LogMessage{" +
			"level=" + level +
			", caller='" + caller + '\'' +
			", message='" + message + '\'' +
			", threadName='" + threadName + '\'' +
			", timestamp=" + timestamp +
			'}';
	}
	
}
