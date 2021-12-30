package io.github.xnovo3000;

public interface Logger {
	
	void d(String caller, Object obj);
	
	void w(String caller, Object obj);
	
	void i(String caller, Object obj);
	
	void e(String caller, Object obj);
	
}
