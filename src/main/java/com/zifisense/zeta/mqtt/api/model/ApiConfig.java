package com.zifisense.zeta.mqtt.api.model;

/**
 * Api连接认证信息
 * @Title: com.zifisense.zeta.mqtt.api.model.ApiConfig.java
 * @Description: 
 * @author huangdg
 * @date 2020年8月28日
 */
public class ApiConfig {
	/**
	 * api_key: 管理平台web页面->系统管理->企业管理->企业编码
	 */
	private String apiKey;
	/**
	 * secret_key: 管理平台web页面->系统管理->企业管理->企业密钥
	 */
	private String secretKey;
	/**
	 * hostname: 连接地址
	 * 如：
	 * tcp://127.0.0.1:1883
	 * tcp://abc.com:1883
	 */
	private String hostName;
	/**
	 * 连接ID，三位唯一的数值，如果重复会导致连接互踢
	 */
	private String clientId;
	
	public ApiConfig(String apiKey, String secretKey, String hostName, String clientId) {
		this.apiKey = apiKey;
		this.secretKey = secretKey;
		this.hostName = hostName;
		this.clientId = apiKey+":"+secretKey+clientId;
	}
	
	public String getApiKey() {
		return apiKey;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public String getHostName() {
		return hostName;
	}
	public String getClientId() {
		return clientId;
	}

	@Override
	public String toString() {
		return "ApiConfig [apiKey=" + apiKey + ", secretKey=" + secretKey + ", hostName=" + hostName+ ", clientId=" + clientId + "]";
	}
}
