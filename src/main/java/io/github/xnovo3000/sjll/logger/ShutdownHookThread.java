package io.github.xnovo3000.sjll.logger;

import java.util.List;

final class ShutdownHookThread extends Thread {
	
	private final List<AutoCloseable> activeTargets;
	private final List<Thread> activeThreads;
	
	public ShutdownHookThread(List<AutoCloseable> activeTargets, List<Thread> activeThreads) {
		this.activeTargets = activeTargets;
		this.activeThreads = activeThreads;
	}
	
	@Override
	public void run() {
		// Close the targets and join the threads
		try {
			for (AutoCloseable activeTarget : activeTargets) {
				activeTarget.close();
			}
			for (Thread activeThread : activeThreads) {
				activeThread.join();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
