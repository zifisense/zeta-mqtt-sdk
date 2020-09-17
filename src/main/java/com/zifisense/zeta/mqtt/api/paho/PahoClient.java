package com.zifisense.zeta.mqtt.api.paho;

import java.io.UnsupportedEncodingException;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zifisense.zeta.mqtt.api.model.ApiConfig;
import com.zifisense.zeta.mqtt.api.model.ZifiQos;
import com.zifisense.zeta.mqtt.api.model.ZifiTopic;

/**
 * paho库客户端方法
 * 
 * @Title: com.zifisense.zeta.mqtt.api.paho.PahoClient.java
 * @Description:
 * @author huangdg
 * @date 2020年9月16日
 */
public class PahoClient {
	private static Logger logger = LoggerFactory.getLogger(PahoClient.class);
	private ApiConfig apiConfig;
	private MqttConnectOptions connOpts = new MqttConnectOptions();
	private MqttClient mqttClient = null;
	private MqttCallback callBack = null;
	/**
	 * 获取api配置信息
	 * 
	 * @return
	 */
	public ApiConfig getApiConfig() {
		return apiConfig;
	}

	/**
	 * 获取MqttClient信息
	 * 
	 * @return
	 */
	public MqttClient getConnect() {
		return mqttClient;
	}

	/**
	 * 获取连接
	 * 
	 * @return
	 */
	public MqttConnectOptions getOptions() {
		return connOpts;
	}

	public PahoClient(String apiKey, String secretKey, String hostName, String clientId, MqttCallback callBackp) {
		if (mqttClient == null) {
			apiConfig = new ApiConfig(apiKey, secretKey, hostName, clientId);
			callBack = callBackp;
			createConnect();
		}
	}

	/**
	 * 创建mqtt的连接
	 */
	private void createConnect() {
		// 本地信息如clientid等的持久化方式，以下采用内存方式
		MemoryPersistence persistence = new MemoryPersistence();
		try {
			mqttClient = new MqttClient(apiConfig.getHostName(), apiConfig.getClientId(), persistence);
			mqttOption();
			mqttClient.setCallback(callBack);
			mqttClient.connect(connOpts);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 订阅
	 * 
	 * @param topics 主题
	 * @param qos    消息质量
	 * @throws MqttException
	 */
	public void subcribe(ZifiTopic[] topic) throws MqttException {
		if (mqttClient == null) {
			createConnect();
		}
		// 转换topic信息
		int topicSize = topic.length;
		String[] topics = new String[topicSize];
		int[] qos = new int[topicSize];
		for (int i = 0; i < topicSize; i++) {
			topics[i] = topic[i].name();
			ZifiQos zifiQos = topic[i].qos();
			if (zifiQos.AT_MOST_ONCE == zifiQos) {
				qos[i] = 0;
			} else {
				qos[i] = zifiQos == ZifiQos.AT_LEAST_ONCE ? 1 : 2;
			}

		}
		mqttClient.subscribe(topics, qos);
	}

	/**
	 * 取消订阅
	 * 
	 * @param topics 主题
	 * @throws MqttException
	 */
	public void unSubcribe(String[] topics) throws MqttException {
		if (mqttClient == null) {
			createConnect();
		}
		mqttClient.unsubscribe(topics);
	}

	/**
	 * 发布消息
	 * 
	 * @param topic
	 * @param message
	 * @param qos     0:most once 1:least once 2:exacly once
	 * @throws MqttPersistenceException
	 * @throws MqttException
	 * @throws UnsupportedEncodingException
	 */
	public void publish(String topic, String message, int qos) throws UnsupportedEncodingException, MqttException {
		if (mqttClient == null) {
			createConnect();
		}
		MqttMessage mqttMessage = new MqttMessage();
		mqttMessage.setQos(qos);
		mqttMessage.setPayload(message.getBytes("utf8"));
		mqttClient.publish(topic, mqttMessage);
	}

	/**
	 * 断开连接
	 * 
	 * @throws MqttException
	 */
	public void disconnnect() throws MqttException {
		if (mqttClient != null) {
			mqttClient.disconnect();
		}
	}

	/**
	 * mqtt连接配置
	 * 
	 * @return
	 */
	private void mqttOption() {
		connOpts.setCleanSession(MqttOption.CLEAN_START);
		connOpts.setKeepAliveInterval(MqttOption.KEEP_ALIVE);
		connOpts.setUserName(apiConfig.getApiKey());
		connOpts.setPassword(apiConfig.getSecretKey().toCharArray());
		connOpts.setAutomaticReconnect(MqttOption.AUTOMATIC_RECONNECT);
		connOpts.setMqttVersion(MqttOption.MQTT_VERSION);
		connOpts.setMaxReconnectDelay(MqttOption.RECONNECTION_DELAY);
	}
}