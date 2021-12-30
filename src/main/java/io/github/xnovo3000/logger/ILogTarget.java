package io.github.xnovo3000.logger;

import io.github.xnovo3000.LogTarget;
import io.github.xnovo3000.sjll.data.LogMessage;
import io.github.xnovo3000.sjll.formatter.LogFormatter;
import io.github.xnovo3000.sjll.outputprovider.OutputProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ILogTarget implements LogTarget {
	
	private final List<LogMessage> messages;
	private final List<LogFormatter> formatters;
	private final OutputProvider outputProvider;
	
	// Caches
	private final List<LogMessage> consumedMessages;
	private final StringBuilder messageBuilder;
	
	boolean shouldClose;
	
	public ILogTarget(List<LogFormatter> formatters, OutputProvider outputProvider) {
		this.messages = new ArrayList<>();
		this.formatters = formatters;
		this.outputProvider = outputProvider;
		consumedMessages = new ArrayList<>();
		messageBuilder = new StringBuilder();
		shouldClose = false;
	}
	
	@Override
	public void run() {
		// TODO: Handle shouldClose
		// Get the consumed messages
		consumedMessages.clear();
		synchronized (messages) {
			consumedMessages.addAll(messages);
			messages.clear();
		}
		// Write to the OutputProvider
		try {
			for (LogMessage logMessage : consumedMessages) {
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
	
	@Override
	public void put(LogMessage logMessage) {
		synchronized (messages) {
			messages.add(logMessage);
		}
	}
	
	@Override
	public void close() {
		shouldClose = true;
	}
	
}
