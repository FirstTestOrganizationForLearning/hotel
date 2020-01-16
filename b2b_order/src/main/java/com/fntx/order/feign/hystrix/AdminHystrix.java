package com.fntx.order.feign.hystrix;

import com.fntx.common.domain.BHotelDetail;
import com.fntx.order.feign.AdminFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

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
    public String tokenCheck(String redisToken,
                             String accessToken,
                             String hotelId) {
        logger.error("[b2b_admin服务]-到达熔断器:tokenCheck");
        return "-1";
    }


}
