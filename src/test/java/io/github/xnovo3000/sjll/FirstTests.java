package io.github.xnovo3000.sjll;

import io.github.xnovo3000.sjll.logger.LogFactory;
import io.github.xnovo3000.sjll.logger.Logger;
import org.junit.jupiter.api.Test;

class FirstTests {

	@Test
	void firstTests() {
		Logger logger = LogFactory.getLogger("MainLogger");
		logger.d("Caller", "Custom message");
		logger.i("Caller", "Custom message");
		logger.e("Caller", "Custom message");
	}
	
}
