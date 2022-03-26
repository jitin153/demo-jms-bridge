package com.demo.jmsbridge.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * This class is just to test the consumer part.
 * Since we are leveraging JMS bridge concept here, therefore, this class is just useless.
 */
@Component
public class StudentDataMessageConsumer {

	private static final Logger LOG = LoggerFactory.getLogger(StudentDataMessageConsumer.class);

	//@JmsListener(destination = "${amq.artemis.bridge.config.student-data.local-queue-name}")
	public void receiveMessage(String message) {
		LOG.info("Received message:\n{}", message);
	}
}
