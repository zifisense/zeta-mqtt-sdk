package com.zifisense.zeta.mqtt.api.queue;

import static org.junit.Assert.*;

import org.junit.Test;

public class MessageQueueTest {

	@Test
	public void testProducer() {
		for (int i = 0; i < 100; i++) {
			assertTrue(QueueFactory.getInstance().produce(i+""));
		}
		assertFalse(QueueFactory.getInstance().produce(null));
	}
	@Test
	public void testConsumer() {
		int count = 0;
		for (int i = 0; i < 100; i++) {
			String resultString = QueueFactory.getInstance().consume();
			if (resultString != null) {
				count++;
			}
		}
		assertTrue(count>0);
	}

}
