package com.zifisense.zeta.mqtt.api.model;

/**
 * Topic
 * 
 * @Title: com.zifisense.zeta.mqtt.api.model.ZifiTopic.java
 * @Description:
 * @author huangdg
 * @date 2020年9月11日
 */
public class ZifiTopic {

	private final String name;
	private final ZifiQos qos;

	public ZifiTopic(String name, ZifiQos qos) {
		this.name = name;
		this.qos = qos;
	}

	public String name() {
		return name;
	}

	public ZifiQos qos() {
		return qos;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (o.getClass() != this.getClass()) {
			return false;
		}
		if (this == o) {
			return true;
		}

		ZifiTopic topic = (ZifiTopic) o;

		if (name != null ? !name.equals(topic.name) : topic.name != null) {
			return false;
		}
		if (qos != topic.qos) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (qos != null ? qos.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "{ name=" + name + ", qos=" + qos + " }";
	}
}
