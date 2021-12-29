package io.github.xnovo3000.sjll;

import org.junit.jupiter.api.Test;

class FirstTests {
	
	@Test
	void initialize() {
		Logger logger = LogFactory.getLogger("MainLogger");
		// Write to console
		logger.d("Debug");
		logger.i("Info");
		logger.w("Warn");
		logger.e("Error");
	}
	
}
