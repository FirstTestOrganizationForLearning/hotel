package com.fntx.sdk.controller;

import com.alibaba.fastjson.JSONObject;
import com.fntx.sdk.feign.AdminFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @FileName: 测试feign服务调用
 * @Author: 胡庆康
 * @Date: 2019/6/26 10:21
 * @Description:
 */
@RestController
@RequestMapping(value = "sdk")
public class SdkController {
    @Autowired
    AdminFeign adminFeign;

    @RequestMapping("/testHello")
    public String hello(String name) {
        String result =  adminFeign.hello(name) ;
        return result;
    }

    /**
     * @Description: token校验调用demo
     * @Author: 王俊文
     * @Date: 19-7-17 上午9:23
     * @Param: []
     * @returns: java.lang.String
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-7-17 上午9:23          1.0
     */
    @RequestMapping("/tokenCheck")
    public JSONObject tokenCheck() {
        JSONObject result =  adminFeign.tokenCheck("YXNkZmFzZGZfMQ==","YXNkZmFzZGZfMQ==",
                null) ;
        return result;
    }


}
