package com.chuck.springboot.dubbo;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableDubboConfiguration
@Slf4j
public class ConsumerApplication {

	//private static final Logger logger = Logger.getLogger(ConsumerApplication.class);


	public static void main(String[] args) {

		ConfigurableApplicationContext run = SpringApplication.run(ConsumerApplication.class, args);

		HelloConsumer helloConsumer = (HelloConsumer) run.getBean("helloConsumer");

		log.info("ConsumerApplication,this is the new shit" );


		for (int i = 0; i < 10; i++) {
			helloConsumer.getMessage("hello"+i);
		}
	}

}
