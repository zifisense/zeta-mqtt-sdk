package com.zifisense.zeta.mqtt.api.paho;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.Test;

import com.zifisense.zeta.mqtt.api.SampleInfo;
import com.zifisense.zeta.mqtt.api.queue.QueueFactory;

public class PahoClientTest {

	@Test
	public void test() throws MqttException, UnsupportedEncodingException {
		// 订阅示例，注意保证每条连接的clientid是唯一的，否则会出现来接互踢
		PahoClient pahoClient = new PahoClient(SampleInfo.API_KEY, SampleInfo.SECRET_KEY, SampleInfo.HOST_NAME,
				SampleInfo.CLIENT_ID+"paho", new MqttCallback() {
					@Override
					public void connectionLost(Throwable cause) {
						// 连接丢失，一般在这里面进行重连，配置了自动重连则无需手动做重连操作
						System.err.println("====mqtt disconnected====");
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
				});
		pahoClient.subcribe(SampleInfo.TOPIC_ALL);
		// 发布示例，实际对接无该操作；模拟发送消息
		int publishInt = 10;
		for (int i = 0; i < publishInt; i++) {
			pahoClient.publish(SampleInfo.PUBLISH_TOPIC, SampleInfo.PUBLISH_MESSAGE + "paho" + i, 1);
		}
		for (int i = 0; i < publishInt ; i++) {
			String msgString = QueueFactory.getInstance().consume();
			if (msgString != null) {
				// TODO 实际获取数据后，进行的处理
				System.out.println(msgString);
			}
		}
		assertEquals(SampleInfo.API_KEY, pahoClient.getApiConfig().getApiKey());
		assertEquals(3, pahoClient.getOptions().getMqttVersion());
		assertEquals(SampleInfo.HOST_NAME, pahoClient.getConnect().getCurrentServerURI());
		pahoClient.disconnnect();
	}

}
