package com.zifisense.zeta.mqtt.api.client;

import static org.junit.Assert.*;

import org.fusesource.mqtt.client.QoS;
import org.junit.Test;

import com.zifisense.zeta.mqtt.api.model.ZifiQos;

public class QosExchangeTest {

	@Test
	public void testZifiToFusesourceZifiQos() {
		assertEquals(QoS.AT_LEAST_ONCE,QosExchange.zifiToFusesource(ZifiQos.AT_LEAST_ONCE));
		assertEquals(QoS.AT_MOST_ONCE,QosExchange.zifiToFusesource(ZifiQos.AT_MOST_ONCE));
		assertEquals(QoS.EXACTLY_ONCE,QosExchange.zifiToFusesource(ZifiQos.EXACTLY_ONCE));
	}

	@Test
	public void testFusesourceToZifi() {
		assertEquals(ZifiQos.AT_LEAST_ONCE,QosExchange.fusesourceToZifi(QoS.AT_LEAST_ONCE));
		assertEquals(ZifiQos.AT_MOST_ONCE,QosExchange.fusesourceToZifi(QoS.AT_MOST_ONCE));
		assertEquals(ZifiQos.EXACTLY_ONCE,QosExchange.fusesourceToZifi(QoS.EXACTLY_ONCE));
	}

}
