package com.demo.jmsbridge.config;

public class JmsBridgeProperties {
	private String bridgeName;
	private String localQueueName;
	private String targetQueueName;
	private boolean durable;
	private Integer timeToLive;
	private boolean persistent;

	public String getBridgeName() {
		return bridgeName;
	}

	public void setBridgeName(String bridgeName) {
		this.bridgeName = bridgeName;
	}

	public String getLocalQueueName() {
		return localQueueName;
	}

	public void setLocalQueueName(String localQueueName) {
		this.localQueueName = localQueueName;
	}

	public String getTargetQueueName() {
		return targetQueueName;
	}

	public void setTargetQueueName(String targetQueueName) {
		this.targetQueueName = targetQueueName;
	}

	public boolean isDurable() {
		return durable;
	}

	public void setDurable(boolean durable) {
		this.durable = durable;
	}

	public Integer getTimeToLive() {
		return timeToLive;
	}

	public void setTimeToLive(Integer timeToLive) {
		this.timeToLive = timeToLive;
	}

	public boolean isPersistent() {
		return persistent;
	}

	public void setPersistent(boolean persistent) {
		this.persistent = persistent;
	}

}
