package com.zifisense.zeta.mqtt.api.fusesource;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.Callback;
import org.fusesource.mqtt.client.ExtendedListener;
import org.fusesource.mqtt.client.QoS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zifisense.zeta.mqtt.api.SampleInfo;
import com.zifisense.zeta.mqtt.api.queue.QueueFactory;
import com.zifisense.zeta.mqtt.api.util.ZifiThreadFactory;

/**
 * fusesource调用示例
 * 
 * @Title: com.zifisense.zeta.mqtt.api.fusesource.ExeSample.java
 * @Description:
 * @author huangdg
 * @date 2020年9月16日
 */
public class ExeSample {
	private static Logger logger = LoggerFactory.getLogger(ExeSample.class);

	public static void main(String[] args) {
		// 订阅示例，注意保证每条连接的clientid是唯一的，否则会出现连接互踢
		FusesourceClient client = new FusesourceClient(SampleInfo.API_KEY, SampleInfo.SECRET_KEY, SampleInfo.HOST_NAME,
				SampleInfo.CLIENT_ID, new ConnectCallBack());

		// 开始监听
		client.listener(new ReceiveMessageListener());
		// 订阅示例
		client.subscribe(SampleInfo.TOPIC_ALL, new SubcribeCallBack());
		// 发布示例，【实际对接无该操作】；模拟发送10条消息
		for (int i = 0; i < 10; i++) {
			client.publish(SampleInfo.PUBLISH_TOPIC, SampleInfo.PUBLISH_MESSAGE + i, QoS.AT_LEAST_ONCE,
					new PublishCallBack());
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

	/**
	 * connect回调
	 * 
	 */
	static class ConnectCallBack implements Callback<Void> {
		@Override
		public void onSuccess(Void value) {
			logger.info("====mqtt connect success====");

		}

		@Override
		public void onFailure(Throwable value) {
			logger.error("====mqtt connect failure====" + value.getLocalizedMessage());
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
					logger.error("====received message failure====" + value.getLocalizedMessage());

				}
			});
		}

		@Override
		public void onPublish(UTF8Buffer topic, Buffer body, Runnable ack) {
			//该方法已经过时，被上面方法覆盖;不要在这里做任何操作
		}

		// 连接失败
		@Override
		public void onFailure(Throwable value) {
			logger.error("====mqtt connect failure====" + value.getLocalizedMessage());
		}

		// 连接断开
		@Override
		public void onDisconnected() {
			logger.error("====mqtt disconnected====");
		}

		// 连接成功
		@Override
		public void onConnected() {
			logger.info("====mqtt connected====");
		}
	}

	/**
	 * subcribe回调
	 * 
	 */
	static class SubcribeCallBack implements Callback<byte[]> {
		@Override
		public void onSuccess(byte[] value) {
			logger.info("====subcribe success====");
		}

		@Override
		public void onFailure(Throwable value) {
			logger.error("====subcribe failure====" + value.getLocalizedMessage());
		}
	}

	/**
	 * publish回调
	 * 
	 */
	static class PublishCallBack implements Callback<Void> {
		@Override
		public void onSuccess(Void value) {
			logger.info("====publish success====");
		}

		@Override
		public void onFailure(Throwable value) {
			logger.error("====publish failure====" + value.getLocalizedMessage());
		}
	}
}
