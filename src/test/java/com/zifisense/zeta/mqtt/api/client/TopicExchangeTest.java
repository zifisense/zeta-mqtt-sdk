package com.zifisense.zeta.mqtt.api.client;

import static org.junit.Assert.*;

import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;
import org.junit.Test;

import com.zifisense.zeta.mqtt.api.model.ZifiQos;
import com.zifisense.zeta.mqtt.api.model.ZifiTopic;

public class TopicExchangeTest {

	@Test
	public void testZifiToFusesource() {
		ZifiTopic zifiTopic = new ZifiTopic("test", ZifiQos.EXACTLY_ONCE);
		Topic topic = new Topic("test", QoS.EXACTLY_ONCE);
		assertEquals(topic, TopicExchange.zifiToFusesource(zifiTopic));
	}

	@Test
	public void testFusesourceToZifi() {
		ZifiTopic zifiTopic = new ZifiTopic("test", ZifiQos.EXACTLY_ONCE);
		Topic topic = new Topic("test", QoS.EXACTLY_ONCE);
		assertEquals(zifiTopic, TopicExchange.fusesourceToZifi(topic));
	}

}
