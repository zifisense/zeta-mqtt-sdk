package com.zifisense.zeta.mqtt.api.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * 自定义线程方法
 * @Title: com.zifisense.zeta.mqtt.api.util.ZifiThreadFactory.java
 * @Description: 
 * @author huangdg
 * @date 2020年9月16日
 */
public class ZifiThreadFactory implements ThreadFactory {
	private final AtomicInteger threadNum = new AtomicInteger(1);
	private final String threadNamePrefix;

	public ZifiThreadFactory(String threadNamePrefix) {
		this.threadNamePrefix = threadNamePrefix + "-";
	}

	@Override
	public Thread newThread(Runnable r) {
		return new Thread(r, threadNamePrefix + threadNum.getAndIncrement());
	}
}
