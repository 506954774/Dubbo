package com.chuck.springboot.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.demo.IHelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * HelloServiceImpl
 * 责任人:  Chuck
 * 修改人： Chuck
 * 创建/修改时间: 2020/10/21 14:49
 * Copyright :  版权所有
 **/
@Service(interfaceClass = IHelloService.class,loadbalance = "roundrobin")
@Component
@Slf4j
public class HelloServiceImpl implements IHelloService {
    //...

    public String sendMessage(String msg) {
        log.info("provider:"+msg);
        //System.out.println("msg = [" + msg + "]");
        return "provider:"+msg;
    }
}
