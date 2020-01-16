package com.fntx.sdk.feign;

import com.alibaba.fastjson.JSONObject;
import com.fntx.common.domain.BaseReq;
import com.fntx.sdk.feign.hystrix.AdminHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

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
     * @Description: 服务调用示例
     * @Author: 胡庆康
     * @Date: 2019/6/26 9:53
     * @Param: name:姓名
     * @returns: java.lang.String
     * @History: <author>     <time>             <version>       <desc>
     * 胡庆康      2019/6/26 9:53       1.0
     */
    @RequestMapping(value = "admin/hello", method = RequestMethod.GET)
    String hello(@RequestParam("name") String name);

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
    JSONObject tokenCheck(@RequestParam("redisToken") String redisToken,
                          @RequestParam("accessToken") String accessToken,
                          @RequestParam(value="hotelId", required = false) String hotelId);

   /**
    * @Copyright (C), 2019, 弗恩天下
    * @Author: 渠猛
    * @Date: 2019/8/1 10:23
    * @Description: 分销商获取token
    */
    @RequestMapping(value = "ht/token", method = RequestMethod.POST)
    Object getToken(@RequestBody BaseReq baseReq);
    /**
     * @Copyright (C), 2019, 弗恩天下
     * @Author: 渠猛
     * @Date: 2019/8/1 10:23
     * @Description: 分销商刷新token
     */
    @RequestMapping(value = "ht/refreshToken", method = RequestMethod.POST)
    Object refreshToken(@RequestBody BaseReq baseReq);

    /**
     * @Description: 初始化分销商配置信息
     * @Author: 王俊文
     * @Date: 2019/8/30 下午3:09
     * @Param: []
     * @returns: void
     * @History:
     * <author>       <time>            <version>          <desc>
     *  王俊文     2019/8/30 下午3:09          1.0          初始化分销商配置信息
     */
    @GetMapping("apiConfig/frequencyConfigInit")
    void frequencyConfigInit();
}
