package com.demo.jmsbridge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.demo.jmsbridge.config.JmsBridgeSetup;

@SpringBootApplication
@EnableScheduling
public class DemoJmsBridgeApplication {

	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext context = SpringApplication.run(DemoJmsBridgeApplication.class, args);
		JmsBridgeSetup jmsBridgeSetup = context.getBean(JmsBridgeSetup.class);
		jmsBridgeSetup.setupAndStartJmsBridge();
	}

}
