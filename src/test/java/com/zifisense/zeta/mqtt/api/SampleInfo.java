package com.zifisense.zeta.mqtt.api;

import java.util.Random;

import com.zifisense.zeta.mqtt.api.model.TopicList;
import com.zifisense.zeta.mqtt.api.model.ZifiTopic;

/**
 * 示例连接参数
 * @Title: com.zifisense.zeta.mqtt.api.model.SampleInfo.java
 * @Description: 
 * @author huangdg
 * @date 2020年9月16日
 */
public class SampleInfo {
//	public static final String API_KEY = "c6d8e5d10c6a4a5e8d9666ffd4c54d7c";
//	public static final String SECRET_KEY = "91b7dcb079cf4040afd6e9b8146151d6";
//	public static final String HOST_NAME = "tcp://test-cn.zifisense.com:1883";
//	public static final String CLIENT_ID = new Random().nextInt(999)+"" ;

	public static final String API_KEY = "c4ca4238a0b923820dcc509a6f75849b";
	public static final String SECRET_KEY = "c81e728d9d4c2f636f067f89cc14862c";
	public static final String HOST_NAME = "tcp://192.168.0.12:1883";
	public static final String CLIENT_ID = new Random().nextInt(999)+"" ;
	
	public static final ZifiTopic[] TOPIC_ALL = { TopicList.TOPIC_ALL };
	public static final ZifiTopic[] TOPIC_AP_ALL = { TopicList.TOPIC_AP_ALL };
	public static final ZifiTopic[] TOPIC_MS_ALL = { TopicList.TOPIC_MS_ALL };
	public static final ZifiTopic[] TOPIC_MS_UPLOAD = { TopicList.TOPIC_MS_UPLOAD };
	public static final ZifiTopic[] TOPIC_MS_ANALYSIS_DATA = { TopicList.TOPIC_MS_ANALYSIS_DATA };
	/**
	 * 复合Topic，订阅多种数据
	 */
	public static final ZifiTopic[] TOPIC_MUTIL = { TopicList.TOPIC_AP_ALL, TopicList.TOPIC_MOTE_ALL,
			TopicList.TOPIC_MS_ALL, TopicList.TOPIC_MS_ANALYSIS_DATA };
	/**
	 * 测试发布主题和消息
	 */
	public static final String PUBLISH_TOPIC = API_KEY + "/test/publish";
	public static final String PUBLISH_MESSAGE = "testpublictxt";
}
