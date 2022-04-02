package io.github.xnovo3000.sjll.implementation.v2;

import io.github.xnovo3000.sjll.outputprovider.OutputProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IShutdownHook extends Thread {
	
	private final List<ILogTarget> logTargets;
	private final List<OutputProvider> outputProviders;
	
	public IShutdownHook(Map<String, ILogTarget> logTargets, List<OutputProvider> outputProviders) {
		this.logTargets = new ArrayList<>(logTargets.values());
		this.outputProviders = outputProviders;
	}
	
	@Override
	public void run() {
		try {
			for (ILogTarget logTarget : logTargets) {
				logTarget.close();
			}
			for (OutputProvider outputProvider : outputProviders) {
				outputProvider.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
