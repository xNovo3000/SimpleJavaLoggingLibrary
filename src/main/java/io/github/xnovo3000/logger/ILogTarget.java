package io.github.xnovo3000.logger;

import io.github.xnovo3000.LogTarget2;
import io.github.xnovo3000.sjll.data.LogMessage2;
import io.github.xnovo3000.sjll.formatter.LogFormatter2;
import io.github.xnovo3000.sjll.outputprovider.OutputProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ILogTarget implements LogTarget2 {
	
	private final List<LogMessage2> messages;
	private final List<LogFormatter2> formatters;
	private final OutputProvider outputProvider;
	
	private final List<LogMessage2> consumedMessages;
	private final StringBuilder messageBuilder;
	
	boolean shouldClose;
	
	public ILogTarget(List<LogFormatter2> formatters, OutputProvider outputProvider) {
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
			for (LogMessage2 logMessage : consumedMessages) {
				messageBuilder.setLength(0);
				for (LogFormatter2 formatter : formatters) {
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
	public void put(LogMessage2 logMessage) {
		synchronized (messages) {
			messages.add(logMessage);
		}
	}
	
	@Override
	public void close() {
		shouldClose = true;
	}
	
}
