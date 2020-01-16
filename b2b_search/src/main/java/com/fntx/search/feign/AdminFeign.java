package com.fntx.search.feign;

import com.fntx.search.feign.hystrix.AdminHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

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
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-7-17 上午10:04          1.0          token解析服务调用
     */
    @RequestMapping(value = "tokenCheck", method = RequestMethod.GET)
    String tokenCheck(@RequestParam("redisToken") String redisToken,
                                        @RequestParam("accessToken") String accessToken,
                                        @RequestParam(value="hotelId", required = false) String hotelId);

    /**
     * @Description: 将指定内容放入消息队列
     * @Author: 王俊文
     * @Date: 19-7-23 下午6:49
     * @Param: [paramMap]
     * @returns: java.lang.String
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     19-7-23 下午6:49          1.0         将指定内容放入消息队列
     */
    @RequestMapping(value = "messageQueue", method = RequestMethod.POST)
    void messageQueue(@RequestParam Map<String, Object> paramMap);
}
