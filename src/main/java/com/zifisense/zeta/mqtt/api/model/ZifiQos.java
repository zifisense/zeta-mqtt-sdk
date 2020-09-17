package com.zifisense.zeta.mqtt.api.model;

/**
 * Qos
 * @Title: com.zifisense.zeta.mqtt.api.model.ZifiQos.java
 * @Description: 
 * @author huangdg
 * @date 2020年9月14日
 */
public enum ZifiQos {
	/**
	 * 0 至多一次
	 */
    AT_MOST_ONCE,
    /**
	 * 1 至少一次
	 */
    AT_LEAST_ONCE,
    /**
	 * 2 只有一次
	 */
    EXACTLY_ONCE
}