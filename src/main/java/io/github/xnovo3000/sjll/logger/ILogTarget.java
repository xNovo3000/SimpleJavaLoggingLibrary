package io.github.xnovo3000.sjll.logger;

import io.github.xnovo3000.sjll.data.LogMessage;
import io.github.xnovo3000.sjll.formatter.LogFormatter;
import io.github.xnovo3000.sjll.outputprovider.OutputProvider;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

final class ILogTarget implements Runnable, AutoCloseable {
	
	private final BlockingQueue<LogMessage> messages;
	private final List<LogFormatter> formatters;
	private final OutputProvider outputProvider;
	private final int minimumImportance;
	
	private boolean shouldClose;
	
	ILogTarget(List<LogFormatter> formatters, OutputProvider outputProvider, int minimumImportance) {
		this.messages = new LinkedBlockingQueue<>();
		this.formatters = formatters;
		this.outputProvider = outputProvider;
		this.minimumImportance = minimumImportance;
		this.shouldClose = false;
	}
	
	@Override
	public void run() {
		// Caches
		final StringBuilder messageBuilder = new StringBuilder();
		// Management
		while (!shouldClose) {
			// Get the message if exists
			LogMessage logMessage = null;
			try {
				logMessage = messages.poll(1, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (logMessage != null && logMessage.getLevel().getImportance() > minimumImportance) {
				// Write to the OutputStream and flush
				try {
					messageBuilder.setLength(0);
					for (LogFormatter formatter : formatters) {
						formatter.onFormat(messageBuilder, logMessage);
					}
					outputProvider.getOutputStream().write(messageBuilder.toString().getBytes());
					outputProvider.getOutputStream().flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void put(LogMessage logMessage) {
		messages.add(logMessage);
	}
	
	@Override
	public void close() {
		shouldClose = true;
	}
	
}
