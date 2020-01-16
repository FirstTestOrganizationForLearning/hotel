package com.fntx.sdk.feign.hystrix;

import com.alibaba.fastjson.JSONObject;
import com.fntx.common.constant.CtripApiErrorCode;
import com.fntx.common.domain.BaseReq;
import com.fntx.sdk.feign.AdminFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String hello(@RequestParam("name") String name) {
        logger.info(String.format("[b2b_admin服务]-到达熔断器 name:%s", name));
        return "Hystrix fallback ...";
    }

    @Override
    public JSONObject tokenCheck(@RequestParam("redisToken") String redisToken,
                                        @RequestParam("accessToken") String accessToken,
                                        @RequestParam(value="hotelId", required = false) String hotelId) {
        JSONObject resultModel = new JSONObject(16);
        resultModel.put("ErrCode", CtripApiErrorCode.ACCESS_TIMEOUT.getCode());
        resultModel.put("ErrMsg", CtripApiErrorCode.ACCESS_TIMEOUT.getName());
        return resultModel;
    }

    @Override
    public Object getToken(BaseReq baseReq) {
        JSONObject resultModel = new JSONObject(16);
        resultModel.put("ErrCode", CtripApiErrorCode.ACCESS_TIMEOUT.getCode());
        resultModel.put("ErrMsg", CtripApiErrorCode.ACCESS_TIMEOUT.getName());
        return resultModel;
    }

    @Override
    public Object refreshToken(BaseReq baseReq) {
        JSONObject resultModel = new JSONObject(16);
        resultModel.put("ErrCode", CtripApiErrorCode.ACCESS_TIMEOUT.getCode());
        resultModel.put("ErrMsg", CtripApiErrorCode.ACCESS_TIMEOUT.getName());
        return resultModel;
    }

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
    @Override
    public void frequencyConfigInit()
    {
        logger.info("[b2b_admin服务]-到达熔断器 baseReq");
    }
}
