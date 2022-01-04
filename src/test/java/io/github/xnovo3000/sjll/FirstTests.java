package io.github.xnovo3000.sjll;

import io.github.xnovo3000.sjll.logger.Logger;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class FirstTests {

	@Test
	void firstTests() throws InterruptedException {
		Logger logger = Logger.getLogger("MainLogger");
		logger.d("Caller", "Custom message");
		logger.i("Caller", "Custom message");
		logger.e("Caller", "Custom message");
		Thread.sleep(250);
		logger.i("Caller", "Custom message");
		logger.e("Caller", "Custom message");
		Thread.sleep(500);
		logger.i("Caller", "Custom message");
		logger.e("Caller", "Custom message");
		Thread.sleep(1000);
	}
	
	@Test
	void doMultithreadedTests() {
		Logger logger = Logger.getLogger("MainLogger");
		// Create the threads
		List<Thread> ts = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			int finalI = i;
			Thread t1 = new Thread(() -> {
				for (int j = 0; j < 10; j++) {
					try {
						Thread.sleep(new Random().nextInt(200) + 200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					logger.d("Runnable" + finalI, "Messaggio numero " + j);
				}
			});
			t1.start();
			ts.add(t1);
		}
		// Join
		ts.forEach(t -> {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}
	
}
