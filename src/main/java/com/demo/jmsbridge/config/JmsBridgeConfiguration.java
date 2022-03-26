package com.demo.jmsbridge.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("amq.artemis.bridge")
public class JmsBridgeConfiguration {
	/*
	 * This map will automatically gets loaded by reading configuration properties
	 * from the property sources.
	 */
	private Map<String, JmsBridgeProperties> config;

	public Map<String, JmsBridgeProperties> getConfig() {
		return config;
	}

	public void setConfig(Map<String, JmsBridgeProperties> config) {
		this.config = config;
	}
	
}