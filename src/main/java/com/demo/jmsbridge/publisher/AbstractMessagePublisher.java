package com.demo.jmsbridge.publisher;

import javax.jms.Destination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractMessagePublisher {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractMessagePublisher.class);

	@Autowired
	private JmsTemplate jmsTemplate;

	public abstract Destination getDestination();

	public void publichMessage(String message) {
		try {
			LOG.info("Publishing message...");
			jmsTemplate.convertAndSend(getDestination(), message);
			LOG.info("Message successfully published...");
		} catch (Exception e) {
			LOG.error("Error occurred while publishing message.\nError-{}", e);
		}
	}
}
