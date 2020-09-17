package com.zifisense.zeta.mqtt.api.paho;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zifisense.zeta.mqtt.api.SampleInfo;
import com.zifisense.zeta.mqtt.api.queue.QueueFactory;
import com.zifisense.zeta.mqtt.api.util.ZifiThreadFactory;

/**
 * paho调用示例
 * 
 * @Title: com.zifisense.zeta.mqtt.api.paho.ExeSample.java
 * @Description:
 * @author huangdg
 * @date 2020年9月16日
 */
public class ExeSample {
	private static Logger logger = LoggerFactory.getLogger(ExeSample.class);

	public static void main(String[] args) throws MqttException, UnsupportedEncodingException {
		// 订阅示例，注意保证每条连接的clientid是唯一的，否则会出现连接互踢
		PahoClient pahoClient = new PahoClient(SampleInfo.API_KEY, SampleInfo.SECRET_KEY, SampleInfo.HOST_NAME,
				SampleInfo.CLIENT_ID, new MyCallBack());
		pahoClient.subcribe(SampleInfo.TOPIC_ALL);
		// 发布示例，【实际对接无该操作】；模拟发送10条消息
		for (int i = 0; i < 10; i++) {
			pahoClient.publish(SampleInfo.PUBLISH_TOPIC, SampleInfo.PUBLISH_MESSAGE + i, 1);
		}
		// 多线程处理订阅回来的数据,注意数据时序问题
		ZifiThreadFactory myThreadFactory = new ZifiThreadFactory("mqtt-thread");
		ThreadPoolExecutor singleThreadPool = new ThreadPoolExecutor(10, 100, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(1000), myThreadFactory, new ThreadPoolExecutor.AbortPolicy());

		// 创建10个线程
		for (int i = 1; i < 10; i++) {
			final int s = i;
			singleThreadPool.execute(() -> {
				while (true) {
					String msgString = QueueFactory.getInstance().consume();
					if (msgString != null) {
						logger.info("当前线程=" + Thread.currentThread().getName() + "数值=" + s + ":" + msgString);
						// TODO 实际获取数据后，进行的处理
					} else {
						try {
							Thread.sleep(100);
						} catch (Exception e) {
							logger.error(e.getMessage());
						}
					}
				}
			});
		}
	}
	
	static class MyCallBack implements MqttCallback{
		@Override
		public void connectionLost(Throwable cause) {
			// 连接丢失，一般在这里面进行重连，配置了自动重连则无需手动做重连操作
			logger.error("====mqtt disconnected====");
		}

		@Override
		public void deliveryComplete(IMqttDeliveryToken token) {
			// 发布成功,获取qos1和qos2的应答信息
		}

		/**
		 * 获取订阅到的消息，进行处理 强烈建议：此处不进行阻塞和长耗时操作，避免连接阻塞,待推送的数据堆积丢失；
		 * 实际应用中，建议放入分布式队列缓存系统中，如：rabbitmq、kafka等
		 */
		@Override
		public void messageArrived(String topic, MqttMessage message) throws Exception {
			// TODO 获取到订阅的消息，在这里进行处理
			String msgString = new String(message.getPayload());
			QueueFactory.getInstance().produce(msgString);
		}
	}
}
