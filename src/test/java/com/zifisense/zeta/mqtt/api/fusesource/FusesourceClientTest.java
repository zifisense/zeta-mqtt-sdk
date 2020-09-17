package com.zifisense.zeta.mqtt.api.fusesource;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.Callback;
import org.fusesource.mqtt.client.ExtendedListener;
import org.fusesource.mqtt.client.QoS;
import org.junit.Test;

import com.zifisense.zeta.mqtt.api.SampleInfo;
import com.zifisense.zeta.mqtt.api.queue.QueueFactory;

public class FusesourceClientTest {

	@Test
	public void test() {
		// 订阅示例，注意保证每条连接的clientid是唯一的，否则会出现连接互踢
		FusesourceClient client = new FusesourceClient(SampleInfo.API_KEY, SampleInfo.SECRET_KEY, SampleInfo.HOST_NAME,
				SampleInfo.CLIENT_ID+"fuse", new ConnectCallBack());
		client.getConnect().getDispatchQueue().execute(new Runnable() {
			@Override
			public void run() {
				// 开始监听
				client.listener(new ReceiveMessageListener());
				// 订阅示例
				client.subscribe(SampleInfo.TOPIC_ALL, new SubcribeCallBack());
				// 发布示例，【实际对接无该操作】；模拟发送10条消息
				for (int i = 0; i < 10; i++) {
					client.publish(SampleInfo.PUBLISH_TOPIC, SampleInfo.PUBLISH_MESSAGE + "fuse"+i, QoS.AT_LEAST_ONCE,
							new PublishCallBack());
				}
			}
		});
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < 10 ; i++) {
			String msgString = QueueFactory.getInstance().consume();
			if (msgString != null) {
				// TODO 实际获取数据后，进行的处理
				System.out.println(msgString);
			} 
		}
		System.out.println(SampleInfo.TOPIC_ALL[0].toString());
		System.out.println(SampleInfo.TOPIC_ALL[0].hashCode());
		System.out.println(client.getApiConfig().toString());
		assertEquals(SampleInfo.SECRET_KEY, client.getApiConfig().getSecretKey());
		assertEquals(SampleInfo.HOST_NAME, client.getApiConfig().getHostName());
		assertEquals(SampleInfo.HOST_NAME.toString(), client.getMqtt().getHost().toString());
	}

	/**
	 * connect回调
	 * 
	 */
	static class ConnectCallBack implements Callback<Void> {
		@Override
		public void onSuccess(Void value) {
			System.out.println("====mqtt connect success====");

		}

		@Override
		public void onFailure(Throwable value) {
			System.err.println("====mqtt connect failure====" + value.getLocalizedMessage());
		}
	}

	static class ReceiveMessageListener implements ExtendedListener {
		/**
		 * 获取订阅到的消息，进行处理 强烈建议：此处不进行阻塞和长耗时操作，避免连接阻塞,待推送的数据堆积丢失；
		 * 实际应用中，建议放入分布式队列缓存系统中，如：rabbitmq、kafka等
		 */
		@Override
		public void onPublish(UTF8Buffer topic, Buffer body, Callback<Callback<Void>> ack) {
			String msgString = new String(body.toByteArray());
			QueueFactory.getInstance().produce(msgString);
			ack.onSuccess(new Callback<Void>() {

				@Override
				public void onSuccess(Void value) {
					// 订阅返回成功应答处理
				}

				@Override
				public void onFailure(Throwable value) {
					System.err.println("====received message failure====" + value.getLocalizedMessage());

				}
			});
		}

		@Override
		public void onPublish(UTF8Buffer topic, Buffer body, Runnable ack) {
			// 该方法已经过时，被上面方法覆盖;不要在这里做任何操作
		}

		// 连接失败
		@Override
		public void onFailure(Throwable value) {
			System.err.println("====mqtt connect failure====" + value.getLocalizedMessage());
		}

		// 连接断开
		@Override
		public void onDisconnected() {
			System.err.println("====mqtt disconnected====");
		}

		// 连接成功
		@Override
		public void onConnected() {
			System.out.println("====mqtt connected====");
		}
	}

	/**
	 * subcribe回调
	 * 
	 */
	static class SubcribeCallBack implements Callback<byte[]> {
		@Override
		public void onSuccess(byte[] value) {
			System.out.println("====subcribe success====");
		}

		@Override
		public void onFailure(Throwable value) {
			System.err.println("====subcribe failure====" + value.getLocalizedMessage());
		}
	}

	/**
	 * publish回调
	 * 
	 */
	static class PublishCallBack implements Callback<Void> {
		@Override
		public void onSuccess(Void value) {
			System.out.println("====publish success====");
		}

		@Override
		public void onFailure(Throwable value) {
			System.err.println("====publish failure====" + value.getLocalizedMessage());
		}
	}
}
