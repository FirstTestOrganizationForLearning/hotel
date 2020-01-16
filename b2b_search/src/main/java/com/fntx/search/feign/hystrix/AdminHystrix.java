package com.fntx.search.feign.hystrix;

import com.fntx.search.feign.AdminFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @FileName:
 * @Author: 胡庆康
 * @Date: 2019/6/26 9:58
 * @Description:
 */
@Component
public class AdminHystrix implements AdminFeign {
    private static final Logger logger = LoggerFactory.getLogger(AdminHystrix.class);

    @Override
    public String tokenCheck(@RequestParam("redisToken") String redisToken,
                                        @RequestParam("accessToken") String accessToken,
                                        @RequestParam(value="hotelId", required = false) String hotelId) {
        logger.info("[b2b_admin服务]-到达熔断器");
        return "-1";
    }

    @Override
    public void messageQueue(Map<String, Object> paramMap) {
    }
}
