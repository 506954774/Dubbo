package com.chuck.springboot.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.demo.IHelloService;
import com.example.demo.IHelloServiceAsyn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * HelloConsumer
 * 责任人:  Chuck
 * 修改人： Chuck
 * 创建/修改时间: 2020/10/21 14:51
 * Copyright :  版权所有
 **/
@Component
@Slf4j

public class HelloConsumer {

    /****************************************************************************
     * 为何api要写两套?因为同一个api不允许同时支持同步或异步.
     * 也就是说同一个api的方法,不允许A业务里同步调用,而B业务里异步调用.
     * 所以,虽然两个api接口一模一样,但是最好像这样写两套,因为不确定哪些业务会异步调用.
     * 异步调用的场景:
     * 例如某个界面的首页,里面有很多不同模块的api,网关接口会整合这些api,客户端只需要调用网关的一个接口
     * 就能获取全部数据.
     * 该界面可能需要调用好几个接口,如果同步调用,
     * 则会非常耗时.而异步调用则解决了这个问题.类似安卓里的回调.
     */

    @Reference(interfaceClass = IHelloServiceAsyn.class,timeout = 4000,async = true)
    private IHelloServiceAsyn iHelloServiceAsyn;

    @Reference(interfaceClass = IHelloService.class,timeout = 4000)
    private IHelloService iHelloService;

    public void getMessage(String msg) {
        log.info("消费者调用成功:"+iHelloService.sendMessage(msg));
    }

    /**
     * 获取异步接口的实例,异步远程调用.
     * @param msg
     */
    public void getMessageAsyn(String msg) {
        log.info("消费者调用成功:"+iHelloServiceAsyn.sendMessage(msg));
    }

}
