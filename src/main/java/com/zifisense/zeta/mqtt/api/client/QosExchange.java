package com.zifisense.zeta.mqtt.api.client;

import org.fusesource.mqtt.client.QoS;
import com.zifisense.zeta.mqtt.api.model.ZifiQos;
/**
 * Fusesource Qos转换方法
 * @Title: com.zifisense.zeta.mqtt.api.fusesource.QosExchange.java
 * @Description: 
 * @author huangdg
 * @date 2020年9月14日
 */
public class QosExchange {
	private QosExchange() {
		
	}
	/**
	 * Zifi类型转Fusesource类型
	 * @param qos
	 * @return
	 */
	public static QoS zifiToFusesource(ZifiQos qos) {
		if (qos.equals(ZifiQos.AT_LEAST_ONCE)) {
			return QoS.AT_LEAST_ONCE;
		}
		else if (qos.equals(ZifiQos.EXACTLY_ONCE)) {
			return QoS.EXACTLY_ONCE;
		}
		else {
			return QoS.AT_MOST_ONCE;
		}
	}
	/**
	 * Fusesource类型转Zifi类型
	 * @param qos
	 * @return
	 */
	public static ZifiQos fusesourceToZifi(QoS qos) {
		if (qos.equals(QoS.AT_LEAST_ONCE)) {
			return ZifiQos.AT_LEAST_ONCE;
		}
		else if (qos.equals(QoS.EXACTLY_ONCE)) {
			return ZifiQos.EXACTLY_ONCE;
		}
		else {
			return ZifiQos.AT_MOST_ONCE;
		}
	}
	
}
