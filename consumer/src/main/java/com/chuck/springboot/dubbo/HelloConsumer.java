package com.chuck.springboot.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

/**
 * HelloConsumer
 * 责任人:  Chuck
 * 修改人： Chuck
 * 创建/修改时间: 2020/10/21 14:51
 * Copyright :  版权所有
 **/
@Component
public class HelloConsumer {
    @Reference(interfaceClass = IHelloService.class)
    private IHelloService iHelloService;

    public void getMessage(String msg) {
        System.out.println(iHelloService.sendMessage(msg));
    }

}
