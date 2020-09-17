package com.zifisense.zeta.mqtt.api.client;

import org.fusesource.mqtt.client.Topic;
import com.zifisense.zeta.mqtt.api.model.ZifiTopic;
/**
 * Fusesource Topic转换方法
 * @Title: com.zifisense.zeta.mqtt.api.fusesource.TopicExchange.java
 * @Description: 
 * @author huangdg
 * @date 2020年9月14日
 */
public class TopicExchange {
	private TopicExchange() {
		
	}
	/**
	 * Zifi类型转Fusesource类型
	 * @param topic
	 * @return
	 */
	public static Topic zifiToFusesource(ZifiTopic topic) {
		return new Topic(topic.name(), QosExchange.zifiToFusesource(topic.qos()));
		 
	}
	/**
	 * Fusesource类型转Zifi类型
	 * @param topic
	 * @return
	 */
	public static ZifiTopic fusesourceToZifi(Topic topic) {
		return new ZifiTopic(topic.name().toString(), QosExchange.fusesourceToZifi(topic.qos()));
	}
	
}
