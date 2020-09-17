package com.zifisense.zeta.mqtt.api.fusesource;

/**
 * Fusesource mqtt连接参数
 * 
 * @Title: com.zifisense.zeta.mqtt.api.fusesource.MqttConfig.java
 * @Description:
 * @author huangdg
 * @date 2020年9月11日
 */
public class MqttConfig {
	private MqttConfig() {
		
	}
	/**
	 * 保留主题订阅和确认位置,保留为false；有效期根据服务器配置，默认2h
	 */
	public static final boolean CLEAN_START = true;
	/**
	 * mqtt协议版本3.1,目前只允许该版本支持通配符
	 */
	public static final String MQTT_VERSION = "3.1";
	/**
	 * 低耗网络，但是又需要及时获取数据，心跳30s
	 */
	public static final short KEEP_ALIVE = 30;
	/**
	 * 无限制重连次数
	 */
	public static final long RECONNECTION_ATTEMPT_MAX = -1L;
	/**
	 * 每次重连间隔
	 */
	public static final long RECONNECTION_DELAY = 2000L;
	/**
	 * 设置socket发送缓冲区大小2M，默认为65536（64k）
	 */
	public static final int SEND_BUFFER_SIZE = 2 * 1024 * 1024;
	/**
	 * 设置socket接收缓冲区大小2M，默认为65536（64k）
	 */
	public static final int RECEIVE_BUFFER_SIZE = 2 * 1024 * 1024;
}
