package com.zifisense.zeta.mqtt.api.fusesource;

import java.net.URISyntaxException;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.Callback;
import org.fusesource.mqtt.client.CallbackConnection;
import org.fusesource.mqtt.client.ExtendedListener;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;

import com.zifisense.zeta.mqtt.api.client.TopicExchange;
import com.zifisense.zeta.mqtt.api.model.ApiConfig;
import com.zifisense.zeta.mqtt.api.model.ZifiTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * fusesource库客户端方法
 * 
 * @Title: com.zifisense.zeta.mqtt.api.FusesourceClient.java
 * @Description:
 * @author huangdg
 * @date 2020年9月11日
 */
public class FusesourceClient {
	private static Logger logger = LoggerFactory.getLogger(FusesourceClient.class);
	/**
	 * mqtt连接
	 */
	private MQTT mqtt = new MQTT();
	/**
	 * 连接认证信息
	 */
	private ApiConfig apiConfig;
	/**
	 * 连接回调
	 */
	private Callback<Void> connectCallBack = null;

	/**
	 * mqtt连接
	 */
	private CallbackConnection mqttClient = null;

	/**
	 * 建立一个mqtt订阅，并且将接收到的数据写入内存队列中
	 * QueueFactory.getInstance().consume();方法可以获取一条队列中的数据
	 * 
	 * @param apiKey
	 * @param secretKey
	 * @param hostName
	 * @param clientId
	 * @param Ctopics
	 */
	public FusesourceClient(String apiKey, String secretKey, String hostName, String clientId,
			Callback<Void> callBack) {
		if (mqttClient == null) {
			apiConfig = new ApiConfig(apiKey, secretKey, hostName, clientId);
			connectCallBack = callBack;
			connect();
		}
	}
	/**
	 * 获取mqtt
	 * 
	 * @return
	 */
	public MQTT getMqtt() {
		return mqtt;
	}
	/**
	 * 获取api配置信息
	 * 
	 * @return
	 */
	public ApiConfig getApiConfig() {
		return apiConfig;
	}

	/**
	 * 获取连接
	 * 
	 * @return
	 */
	public CallbackConnection getConnect() {
		return mqttClient;
	}

	/**
	 * 启动mqtt订阅，并且将接收到的数据写入内存队列中
	 */
	private void connect() {
		try {
			mqttConfig();
			// 使用回调式API
			mqttClient = mqtt.callbackConnection();
			// 连接
			mqttClient.connect(connectCallBack);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * mqtt连接配置
	 * 
	 * @param apiConfig
	 * @return
	 * @throws URISyntaxException
	 */
	private void mqttConfig() throws URISyntaxException {
		mqtt.setHost(apiConfig.getHostName());
		mqtt.setCleanSession(MqttConfig.CLEAN_START);
		mqtt.setKeepAlive(MqttConfig.KEEP_ALIVE);
		mqtt.setUserName(apiConfig.getApiKey());
		mqtt.setPassword(apiConfig.getSecretKey());
		mqtt.setClientId(apiConfig.getClientId());
		mqtt.setReconnectAttemptsMax(MqttConfig.RECONNECTION_ATTEMPT_MAX);
		mqtt.setReceiveBufferSize(MqttConfig.RECEIVE_BUFFER_SIZE);
		mqtt.setSendBufferSize(MqttConfig.SEND_BUFFER_SIZE);
		mqtt.setVersion(MqttConfig.MQTT_VERSION);
		mqtt.setReconnectDelay(MqttConfig.RECONNECTION_DELAY);
	}

	/**
	 * 开始监听连接，对连接和获取到的消息进行处理
	 * 
	 * @param listener
	 */
	public void listener(ExtendedListener listener) {
		if (mqttClient == null) {
			connect();
		}
		mqttClient.listener(listener);
	}

	/**
	 * 断开连接
	 * 
	 * @param topic
	 */
	public void disconnect(Callback<Void> callBack) {
		if (mqttClient != null) {
			mqttClient.disconnect(callBack);
		}
	}

	/**
	 * 订阅
	 * 
	 * @param topic
	 */
	public void subscribe(ZifiTopic[] topic, Callback<byte[]> callBack) {
		if (mqttClient == null) {
			connect();
		}
		// 转换Topic
		int topicSize = topic.length;
		Topic[] topics = new Topic[topicSize];
		for (int i = 0; i < topicSize; i++) {
			topics[i] = TopicExchange.zifiToFusesource(topic[i]);
		}
		mqttClient.subscribe(topics, callBack);
	}

	/**
	 * 取消订阅
	 * 
	 * @param topic
	 */
	public void unSubscribe(UTF8Buffer[] topics, Callback<Void> callBack) {
		if (mqttClient == null) {
			connect();
		}
		mqttClient.unsubscribe(topics, callBack);
	}

	/**
	 * 发布主题消息
	 * 
	 * @param topic
	 * @param msg
	 */
	public void publish(String topic, String msg, QoS qos, Callback<Void> callBack) {
		if (mqttClient == null) {
			connect();
		}
		mqttClient.publish(topic, msg.getBytes(), qos, false, callBack);
	}

}
