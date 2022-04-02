package io.github.xnovo3000.sjll.implementation.v2;

import io.github.xnovo3000.sjll.LogFormatter;
import io.github.xnovo3000.sjll.LogTarget;
import io.github.xnovo3000.sjll.data.LogMessage;
import io.github.xnovo3000.sjll.outputprovider.OutputProvider;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ILogTarget implements LogTarget {
	
	private final Queue<LogMessage> logMessageBlockingQueue;
	private final List<LogFormatter> logFormatters;
	private final OutputProvider outputProvider;
	private final int minimumImportance;
	
	public ILogTarget(List<LogFormatter> logFormatters, OutputProvider outputProvider, int minimumImportance) {
		this.logMessageBlockingQueue = new LinkedList<>();
		this.logFormatters = logFormatters;
		this.outputProvider = outputProvider;
		this.minimumImportance = minimumImportance;
	}
	
	@Override
	public void run() {
		// Cache the builder
		final StringBuilder messageBuilder = new StringBuilder();
		// For each message in the queue
		while (!logMessageBlockingQueue.isEmpty()) {
			// Try to get the message
			LogMessage logMessage = logMessageBlockingQueue.poll();
			// If the message exists
			if (logMessage != null) {
				// Reset messageBuilder
				messageBuilder.setLength(0);
				// Apply formatters in order
				for (LogFormatter logFormatter : logFormatters) {
					logFormatter.format(messageBuilder, logMessage);
				}
				// Write to the stream
				try {
					outputProvider.getOutputStream().write(messageBuilder.toString().getBytes(StandardCharsets.UTF_8));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// Flush the stream
		try {
			outputProvider.getOutputStream().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void enqueue(LogMessage logMessage) {
		// If the importance level is right
		if (logMessage.getLevel().getImportance() >= minimumImportance) {
			// Try to insert the message
			logMessageBlockingQueue.add(logMessage);
		}
	}
	
	@Override
	public void close() {
		// This is called only when the application is killed
		// Execute for the last time. Flush all pending messages
		run();
	}
	
}
