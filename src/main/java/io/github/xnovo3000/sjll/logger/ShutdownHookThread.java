package io.github.xnovo3000.sjll.logger;

import io.github.xnovo3000.sjll.outputprovider.OutputProvider;

import java.util.List;

final class ShutdownHookThread extends Thread {
	
	private final List<AutoCloseable> activeTargets;
	private final List<Thread> activeThreads;
	private final List<OutputProvider> activeProviders;
	
	public ShutdownHookThread(List<AutoCloseable> activeTargets, List<Thread> activeThreads, List<OutputProvider> activeProviders) {
		this.activeTargets = activeTargets;
		this.activeThreads = activeThreads;
		this.activeProviders = activeProviders;
	}
	
	@Override
	public void run() {
		// Close the targets, the providers and join the threads
		try {
			for (AutoCloseable activeTarget : activeTargets) {
				activeTarget.close();
			}
			for (Thread activeThread : activeThreads) {
				activeThread.join();
			}
			for (OutputProvider activeProvider : activeProviders) {
				activeProvider.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
