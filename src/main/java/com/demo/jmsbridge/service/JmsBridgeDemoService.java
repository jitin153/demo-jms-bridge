package com.demo.jmsbridge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.demo.jmsbridge.publisher.StudentDataPublisher;

@Service
public class JmsBridgeDemoService {

	@Autowired
	private StudentDataPublisher studentDataPublisher;
	
	@Scheduled(cron="${scheduler.cron}", zone="GMT")
	public void publisMessage() {
		studentDataPublisher.publichMessage("Student[name = Abc, email = abc@xyz.pqr]");
	}
}
