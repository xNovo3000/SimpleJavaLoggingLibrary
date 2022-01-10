package io.github.xnovo3000.sjll.logger;

import io.github.xnovo3000.sjll.data.BooleanFlag;
import io.github.xnovo3000.sjll.data.LogMessage;
import io.github.xnovo3000.sjll.formatter.LogFormatter;
import io.github.xnovo3000.sjll.outputprovider.OutputProvider;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * <p>Standard implementation of the log target</p>
 *
 * @since 1.0
 * @author xNovo3000
 */
public final class ILogTarget implements LogTarget {
	
	private final BlockingQueue<LogMessage> messages;
	private final List<LogFormatter> formatters;
	private final OutputProvider outputProvider;
	private final BooleanFlag shouldClose;
	private final int minimumImportance;
	
	ILogTarget(List<LogFormatter> formatters, OutputProvider outputProvider, int minimumImportance) {
		this.messages = new LinkedBlockingQueue<>();
		this.formatters = formatters;
		this.outputProvider = outputProvider;
		this.shouldClose = new BooleanFlag();
		this.minimumImportance = minimumImportance;
	}
	
	@Override
	public void run() {
		// Caches
		final StringBuilder messageBuilder = new StringBuilder();
		// Management
		while (shouldClose.getValue() || !messages.isEmpty()) {
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
						formatter.format(messageBuilder, logMessage);
					}
					outputProvider.getOutputStream().write(messageBuilder.toString().getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// Flush on close
		try {
			outputProvider.getOutputStream().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void enqueue(LogMessage logMessage) {
		// Enqueue only if not closed
		if (shouldClose.getValue()) {
			messages.add(logMessage);
		}
	}
	
	@Override
	public void close() {
		// Set the close flag
		shouldClose.setFlag();
	}
	
}
