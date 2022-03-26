package com.demo.jmsbridge.config;

import java.util.Map.Entry;
import java.util.Objects;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;

import org.apache.activemq.artemis.jms.bridge.ConnectionFactoryFactory;
import org.apache.activemq.artemis.jms.bridge.DestinationFactory;
import org.apache.activemq.artemis.jms.bridge.JMSBridge;
import org.apache.activemq.artemis.jms.bridge.QualityOfServiceMode;
import org.apache.activemq.artemis.jms.bridge.impl.JMSBridgeImpl;
import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.apache.activemq.artemis.jms.client.ActiveMQQueueConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JmsBridgeSetup {

	private static final Logger LOG = LoggerFactory.getLogger(JmsBridgeSetup.class);

	@Autowired
	protected ConnectionFactory connectionFactory;

	@Autowired
	private JmsBridgeConfiguration jmsBridgeConfiguration;

	@Value("${environment}")
	private String environment;

	@Value("${amq.artemis.target.server.url}")
	private String targetServerUrl;

	@Value("${amq.artemis.target.server.username}")
	private String targetServerUsername;

	@Value("${amq.artemis.target.server.password}")
	private String targetServerPassword;
	
	private ActiveMQQueueConnectionFactory activeMQQueueConnectionFactory;
	
	private ActiveMQQueueConnectionFactory activeMQQueueConnectionFactory() throws JMSException {
		if(Objects.nonNull(activeMQQueueConnectionFactory)) {
			return activeMQQueueConnectionFactory;
		}
		activeMQQueueConnectionFactory = new ActiveMQQueueConnectionFactory();
		activeMQQueueConnectionFactory.setBrokerURL(targetServerUrl);
		activeMQQueueConnectionFactory.setUser(targetServerUsername);
		activeMQQueueConnectionFactory.setPassword(targetServerPassword);
		return activeMQQueueConnectionFactory;
	}

	private boolean shouldStartBridge(JmsBridgeProperties jmsBridgeProperties) {
		if (Objects.nonNull(jmsBridgeProperties) && StringUtils.hasText(jmsBridgeProperties.getLocalQueueName())
				&& StringUtils.hasText(jmsBridgeProperties.getTargetQueueName())) {
			if (!StringUtils.hasText(jmsBridgeProperties.getBridgeName())) {
				String bridgeName = new StringBuilder(environment).append("-")
						.append(jmsBridgeProperties.getLocalQueueName()).append("-")
						.append(jmsBridgeProperties.getTargetQueueName()).toString();
				jmsBridgeProperties.setBridgeName(bridgeName);
			}
			return true;
		}
		return false;
	}

	public void setupAndStartJmsBridge() throws Exception {
		ConnectionFactoryFactory sourceConnectionFactoryFactory = newConnectionFactoryFactory(connectionFactory);
		for (Entry<String, JmsBridgeProperties> entry : jmsBridgeConfiguration.getConfig().entrySet()) {
			JmsBridgeProperties jmsBridgeProperties = entry.getValue();
			if(shouldStartBridge(jmsBridgeProperties)) {
				Destination sourceDestination = new ActiveMQQueue(jmsBridgeProperties.getLocalQueueName());
				DestinationFactory sourceDestionationFactory = newDestinationFactory(sourceDestination);
				
				ConnectionFactoryFactory targetConnectionFactoryFactory = newConnectionFactoryFactory(activeMQQueueConnectionFactory());
				Destination targetDestination = new ActiveMQQueue(jmsBridgeProperties.getTargetQueueName());
				DestinationFactory targetDestionationFactory = newDestinationFactory(targetDestination);
				
				JMSBridge jmsBridge = new JMSBridgeImpl(sourceConnectionFactoryFactory, targetConnectionFactoryFactory,
						sourceDestionationFactory, targetDestionationFactory, null, null, null, null, null, 5000, -1,
						QualityOfServiceMode.DUPLICATES_OK, 1, -1, null, null, true);
				LOG.info("Starting JMS bridge '{}' between local queue '{}' & target queue '{}'...", jmsBridgeProperties.getBridgeName(), jmsBridgeProperties.getLocalQueueName(), jmsBridgeProperties.getTargetQueueName());
				jmsBridge.start();
				LOG.info("Started JMS bridge '{}' between local queue '{}' & target queue '{}'.", jmsBridgeProperties.getBridgeName(), jmsBridgeProperties.getLocalQueueName(), jmsBridgeProperties.getTargetQueueName());
			}else {
				LOG.error("Couldn't start JMS bridge '{}' between local queue '{}' & target queue '{}'.", jmsBridgeProperties.getBridgeName(), jmsBridgeProperties.getLocalQueueName(), jmsBridgeProperties.getTargetQueueName());
			}			
		}
	}

	private ConnectionFactoryFactory newConnectionFactoryFactory(final ConnectionFactory cf) {
		return new ConnectionFactoryFactory() {
			@Override
			public ConnectionFactory createConnectionFactory() throws Exception {
				return cf;
			}
		};
	}

	private DestinationFactory newDestinationFactory(final Destination dest) {
		return new DestinationFactory() {
			@Override
			public Destination createDestination() throws Exception {
				return dest;
			}
		};
	}
}