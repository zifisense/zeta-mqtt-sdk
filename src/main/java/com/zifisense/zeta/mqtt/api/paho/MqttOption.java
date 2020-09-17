package com.zifisense.zeta.mqtt.api.paho;

/**
 * Paho mqtt连接参数
 * 
 * @Title: com.zifisense.zeta.mqtt.api.paho.MqttOption.java
 * @Description:
 * @author huangdg
 * @date 2020年9月11日
 */
public class MqttOption {
	private MqttOption() {

	}

	/**
	 * 保留主题订阅和确认位置,保留为false；有效期根据服务器配置，默认2h
	 */
	public static final boolean CLEAN_START = true;
	/**
	 * mqtt协议版本3.1,目前只允许该版本支持通配符
	 */
	public static final int MQTT_VERSION = 3;
	/**
	 * 低耗网络，但是又需要及时获取数据，心跳30s
	 */
	public static final short KEEP_ALIVE = 30;
	/**
	 * 自动重连
	 */
	public static final boolean AUTOMATIC_RECONNECT = true;
	/**
	 * 每次重连间隔
	 */
	public static final int RECONNECTION_DELAY = 2000;
}
