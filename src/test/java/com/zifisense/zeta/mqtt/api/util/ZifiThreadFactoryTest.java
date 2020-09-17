package com.zifisense.zeta.mqtt.api.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class ZifiThreadFactoryTest {

	@Test
	public void testZifiThreadFactory() {
		String threadNameString = "threadFactory-test-name";
		ZifiThreadFactory zifiThreadFactory = new ZifiThreadFactory(threadNameString);
		Thread testThread = zifiThreadFactory.newThread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		testThread.start();
		System.out.printf("%s,before:%s,after:%s\n", Thread.currentThread().getStackTrace()[1].getMethodName(),threadNameString,testThread.getName());
		assertTrue(testThread.getName().contains(threadNameString));
	}

}
