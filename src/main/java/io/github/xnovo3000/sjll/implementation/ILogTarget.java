package io.github.xnovo3000.sjll.implementation;

import io.github.xnovo3000.sjll.LogTarget;
import io.github.xnovo3000.sjll.data.LogMessage;
import io.github.xnovo3000.sjll.outputprovider.OutputProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ILogTarget implements LogTarget {
	
	private boolean shouldClose;
	private final List<String> messages;
	private final OutputProvider outputProvider;
	/* TODO: Add formatting */
	
	public ILogTarget(OutputProvider op) {
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
			List<String> temporaryMessages;
			synchronized (messages) {
				temporaryMessages = new ArrayList<>(messages);
				messages.clear();
			}
			temporaryMessages.forEach(t -> {
				try {
					outputProvider.getOutputStream().write(t.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}
	}
	
	@Override
	public synchronized void put(LogMessage message) {
		synchronized (messages) {
			// FIXME: messages.add(message);
		}
		notifyAll();  // Unlock the wait in the run() method
	}
	
	@Override
	public synchronized void close() {
		shouldClose = true;
		notifyAll();  // Unlock the wait in the run() method
	}
	
}
