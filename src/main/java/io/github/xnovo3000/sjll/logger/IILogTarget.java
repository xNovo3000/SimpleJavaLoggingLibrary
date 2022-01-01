package io.github.xnovo3000.sjll.logger;

import io.github.xnovo3000.sjll.data.LogMessage;
import io.github.xnovo3000.sjll.formatter.LogFormatter;
import io.github.xnovo3000.sjll.outputprovider.OutputProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class IILogTarget implements Runnable, AutoCloseable {
	
	private final List<LogMessage> messages;
	private final List<LogFormatter> formatters;
	private final OutputProvider outputProvider;
	
	private boolean shouldClose;
	
	IILogTarget(List<LogFormatter> formatters, OutputProvider outputProvider) {
		this.messages = new ArrayList<>();
		this.formatters = formatters;
		this.outputProvider = outputProvider;
		shouldClose = false;
	}
	
	@Override
	public synchronized void run() {
		// Caches
		final List<LogMessage> temporaryMessages = new ArrayList<>();
		final StringBuilder messageBuilder = new StringBuilder();
		/* New code for target thread management */
		// Case 1: messages.size() == 0 && !shouldClose -> wait
		// Case 2: messages.size() == 0 && shouldClose -> break
		// Case 3: messages.size() != 0 && !shouldClose -> write
		// Case 4: messages.size() != 0 && shouldClose -> write
		while (true) {
			// Check the number of messages. If != 0 then write to the stream and flush
			if (messages.size() == 0) {
				// Only close if the messages are zero and shouldClose is true. Wait otherwise
				if (shouldClose) {
					break;
				} else {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} else {
				// Destroy temporary messages
				temporaryMessages.clear();
				// Get the messages
				synchronized (messages) {
					temporaryMessages.addAll(messages);
					messages.clear();
				}
				// Write to the OutputStream and flush
				try {
					for (LogMessage logMessage : temporaryMessages) {
						messageBuilder.setLength(0);
						for (LogFormatter formatter : formatters) {
							formatter.onFormat(messageBuilder, logMessage);
						}
						outputProvider.getOutputStream().write(messageBuilder.toString().getBytes());
					}
					outputProvider.getOutputStream().flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public synchronized void put(LogMessage message) {
		// Put the message if this target has been closed
		if (!shouldClose) {
			synchronized (messages) {
				messages.add(message);
			}
		}
		// Release the lock always
		notifyAll();
	}
	
	@Override
	public synchronized void close() {
		shouldClose = true;
		notifyAll();  // Release the wait() inside the run method to terminate the thread
	}
	
}
