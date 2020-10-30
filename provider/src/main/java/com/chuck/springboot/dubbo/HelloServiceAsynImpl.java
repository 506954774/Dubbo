package com.chuck.springboot.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.demo.IHelloService;
import com.example.demo.IHelloServiceAsyn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * HelloServiceImpl
 * 责任人:  Chuck
 * 修改人： Chuck
 * 创建/修改时间: 2020/10/21 14:49
 * Copyright :  版权所有
 **/
@Service(interfaceClass = IHelloServiceAsyn.class ,loadbalance = "roundrobin")
@Component
@Slf4j
public class HelloServiceAsynImpl implements IHelloServiceAsyn {
    //...

    public String sendMessage(String msg) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {

        }
        log.info("provider:"+msg);
        return "provider:"+msg;
    }
}
