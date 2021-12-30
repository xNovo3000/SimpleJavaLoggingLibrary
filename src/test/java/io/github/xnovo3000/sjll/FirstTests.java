package io.github.xnovo3000.sjll;

import io.github.xnovo3000.sjll.old.LogFactory;
import io.github.xnovo3000.sjll.old.Logger;
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
