package io.github.xnovo3000.sjll.implementation;

import io.github.xnovo3000.sjll.data.LogMessage;
import io.github.xnovo3000.sjll.outputprovider.OutputProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LogTarget implements Runnable, AutoCloseable {
	
	private boolean shouldClose;
	private final List<LogMessage> messages;
	private final OutputProvider outputProvider;
	/* TODO: Add formatting */
	
	public LogTarget(OutputProvider op) {
		this.shouldClose = false;
		this.messages = new ArrayList<>();
		this.outputProvider = op;
	}
	
	/* FIXME: This implementation wakes up the thread lots of times. Use a timer instead */
	
	@Override
	public synchronized void run() {
		while (!shouldClose) {
			// Wait until first message is sent
			if (messages.isEmpty()) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			// Read all messages and free the main list
			List<LogMessage> temporaryMessages;
			synchronized (messages) {
				temporaryMessages = new ArrayList<>(messages);
				messages.clear();
			}
			// Write the messages to the stream and flush
			try {
				for (LogMessage logMessage : temporaryMessages) {
					outputProvider.getOutputStream().write(logMessage.getMessage().getBytes());
				}
				outputProvider.getOutputStream().flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void put(LogMessage message) {
		synchronized (messages) {
			messages.add(message);
		}
		notifyAll();  // Unlock the wait in the run() method
	}
	
	@Override
	public synchronized void close() {
		shouldClose = true;
		notifyAll();  // Unlock the wait in the run() method
	}
	
}
