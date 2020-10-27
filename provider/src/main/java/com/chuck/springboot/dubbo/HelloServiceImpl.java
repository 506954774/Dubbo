package com.chuck.springboot.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

/**
 * HelloServiceImpl
 * 责任人:  Chuck
 * 修改人： Chuck
 * 创建/修改时间: 2020/10/21 14:49
 * Copyright :  版权所有
 **/
@Service(interfaceClass = IHelloService.class)
@Component
public class HelloServiceImpl implements IHelloService {
    //...

    public String sendMessage(String msg) {
        return "provider:"+msg;
    }
}
