package com.zifisense.zeta.mqtt.api.model;

/**
 * Zeta mqtt几类常用订阅topic
 * 
 * @Title: com.zifisense.zeta.mqtt.api.model.ZifiTopic.java
 * @Description:
 * @author huangdg
 * @date 2020年9月11日
 */
public class TopicList {
	/**
	 * Topic消息质量：至少一次QoS.AT_LEAST_ONCE、至多一次QoS.AT_MOST_ONCE
	 */
	private static final ZifiQos QOS = ZifiQos.AT_LEAST_ONCE;
	/**
	 * 账户权限下的所有数据
	 */
	public static final ZifiTopic TOPIC_ALL = new ZifiTopic("#", QOS);
	/**
	 * 账户权限下的所有基站/网关数据
	 */
	public static final ZifiTopic TOPIC_AP_ALL = new ZifiTopic("+/v1/ap/#", QOS);
	/**
	 * 账户权限下的所有终端数据
	 */
	public static final ZifiTopic TOPIC_MS_ALL = new ZifiTopic("+/v1/ms/#", QOS);
	/**
	 * 账户权限下的所有中继数据
	 */
	public static final ZifiTopic TOPIC_MOTE_ALL = new ZifiTopic("+/v1/mote/#", QOS);
	/**
	 * 账户权限下的所有终端原始上行数据
	 */
	public static final ZifiTopic TOPIC_MS_UPLOAD = new ZifiTopic("+/v1/ms/+/reportuploadData", QOS);
	/**
	 * 账户权限下的所有中继原始上行数据
	 */
	public static final ZifiTopic TOPIC_MOTE_UPLOAD = new ZifiTopic("+/v1/mote/+/reportuploadData", QOS);
	/**
	 * 账户权限下的所有传感器解析后上行数据
	 */
	public static final ZifiTopic TOPIC_MS_ANALYSIS_DATA = new ZifiTopic("+/jll/property/ms/+/updata", QOS);
}
