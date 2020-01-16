package com.fntx.order.feign;

import com.fntx.common.domain.BHotelDetail;
import com.fntx.order.feign.hystrix.AdminHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @FileName: 主管理服务调用
 * @Author: 胡庆康
 * @Date: 2019/6/26 9:39
 * @Description:
 */
@FeignClient(value = "b2bAdmin", fallback = AdminHystrix.class)
public interface AdminFeign {

    /**
     * @Description: token解析服务调用
     * @Author: 王俊文
     * @Date: 19-7-17 上午10:04
     * @Param: [redisToken--redisToken, accessToken--分销商传入token, hotelId--酒店id 存在即需传入]
     * @returns: java.lang.String
     * @History: <author>       <time>            <version>          <desc>
     * 王俊文     19-7-17 上午10:04          1.0          token解析服务调用
     * @param accessToken 传过来的token
     * @param hotelId 酒店代码
     * @param redisToken redis中的token
     */
    @RequestMapping(value = "tokenCheck", method = RequestMethod.GET)
    String tokenCheck(@RequestParam("redisToken") String redisToken,
                      @RequestParam("accessToken") String accessToken,
                      @RequestParam("hotelId") String hotelId);

}
