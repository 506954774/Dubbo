package com.chuck.springboot.dubbo;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@SpringBootApplication
@EnableDubboConfiguration
@Slf4j
@EnableAsync
public class ConsumerApplication {

	//private static final Logger logger = Logger.getLogger(ConsumerApplication.class);


	public static void main(String[] args) {

		ConfigurableApplicationContext run = SpringApplication.run(ConsumerApplication.class, args);

		HelloConsumer helloConsumer = (HelloConsumer) run.getBean("helloConsumer");



		for (int i = 0; i < 10; i++) {
			//helloConsumer.getMessage("hello"+i);
		}
		//synchronous(helloConsumer);
		try {
			asynchronous(helloConsumer);
		} catch ( Exception e) {
			log.error(e.getMessage());
		}

	}

	/**
	 * 同步调用,按顺序调用
	 * @param helloConsumer
	 */
	private static void synchronous(HelloConsumer helloConsumer) {
		long time1=System.currentTimeMillis();
		System.out.println(time1);
		helloConsumer.getMessage("hello");
		long time2=System.currentTimeMillis();

		log.info("执行第一个耗时:" + (time2-time1));

		helloConsumer.getMessage("hello");

		long time3=System.currentTimeMillis();

		log.info("执行第二个耗时:" + (time3-time2));
		log.info("执行总耗时:" + (time3 - time1));
	}

	/**
	 * 同步调用,模拟场景:多个api调用,或者多个接口调用
	 * @param helloConsumer
	 */
	private static void asynchronous(HelloConsumer helloConsumer) throws ExecutionException, InterruptedException {
		long time1=System.currentTimeMillis();
		System.out.println(time1);
		helloConsumer.getMessageAsyn("hello");
		Future<String> call= RpcContext.getContext().getFuture();

		long time2=System.currentTimeMillis();

		log.info("主线程执行第一个耗时:" + (time2-time1));

		helloConsumer.getMessageAsyn("hello2");
		Future<String> call2= RpcContext.getContext().getFuture();


		long time3=System.currentTimeMillis();

		log.info("主线程执行第二个耗时:" + (time3-time2));
		log.info("主线程执行总耗时:" + (time3-time1));

		log.info("结果:" + call.get()+","+call2.get()+",整个业务实际耗时"+(System.currentTimeMillis()-time1));

		log.info("主线程执行总耗时:" + (time3-time1));


	}

}
