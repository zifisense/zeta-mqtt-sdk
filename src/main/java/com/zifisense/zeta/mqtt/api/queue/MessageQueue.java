package com.zifisense.zeta.mqtt.api.queue;

import java.util.concurrent.ConcurrentLinkedQueue;
/**
 * ConcurrentLinkedQueue，实现的简单无界消息队列;
 * 需要注意队列信息的及时消费，避免内存溢出
 * @Title: com.zifisense.zeta.mqtt.api.queue.MessageQueue.java
 * @Description: 
 * @author huangdg
 * @date 2020年9月10日
 */
public class MessageQueue {
	private ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();

	/**
	 * 往队列中写入数据;
	 * 由于是无界消息队列，需要注意队列消息的及时消费，避免堆积导致内存溢出
	 * @param jsonString
	 * @return
	 */
	public boolean produce(String value) {
		if (value != null) {
			return queue.add(value);
		}
		return false;
	}
	/**
	 * 从队列中取出数据;
	 * 由于是无界消息队列，需要注意队列消息的及时消费，避免堆积导致内存溢出
	 * @param jsonString
	 * @return
	 */
	public String consume() {
		return queue.poll();
	}
}
