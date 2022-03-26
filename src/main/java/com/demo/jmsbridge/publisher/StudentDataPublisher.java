package com.demo.jmsbridge.publisher;

import javax.jms.Destination;

import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StudentDataPublisher extends AbstractMessagePublisher {

	@Value("${amq.artemis.bridge.config.student-data.local-queue-name}")
	private String studentDataLocalQueue;

	@Override
	public Destination getDestination() {
		return new ActiveMQQueue(studentDataLocalQueue);
	}

}
