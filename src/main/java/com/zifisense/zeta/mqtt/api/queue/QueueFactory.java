package com.zifisense.zeta.mqtt.api.queue;
/**
 * 队列工厂，获取队列集合，进行队列操作
 * @Title:package com.zifisense.zeta.mqtt.api.queue.QueueFactory.java
 * @Description: 
 * @author huangdg
 * @date 2020年8月31日
 */
public class QueueFactory {
	private static MessageQueue instance;
	private QueueFactory() {
	}
	
	public static MessageQueue getInstance() {
		if (null == instance) {
			instance = new MessageQueue();
		}
		return instance;
	}
}
