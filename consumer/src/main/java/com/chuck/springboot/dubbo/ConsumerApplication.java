package com.chuck.springboot.dubbo;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableDubboConfiguration
public class ConsumerApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext run = SpringApplication.run(ConsumerApplication.class, args);

		HelloConsumer helloConsumer = (HelloConsumer) run.getBean("helloConsumer");

		for (int i = 0; i < 10; i++) {
			helloConsumer.getMessage("hello"+i);
		}
	}

}
