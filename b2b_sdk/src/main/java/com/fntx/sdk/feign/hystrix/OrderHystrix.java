package com.fntx.sdk.feign.hystrix;

import com.alibaba.fastjson.JSONObject;
import com.fntx.common.utils.ValaditionUtils;
import com.fntx.sdk.feign.OrderFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @FileName: 订单服务熔断
 * @Author: 胡庆康
 * @Date: 2019/6/26 9:58
 * @Description:
 */
@Component
public class OrderHystrix implements OrderFeign {
    private static final Logger logger = LoggerFactory.getLogger(OrderHystrix.class);

    @Override
    public JSONObject checkBookable(JSONObject param) {
        logger.info("[b2bOrder服务]-到达熔断器 param:s"+ param);
        return ValaditionUtils.getResponseTimeOut();
    }

    @Override
    public JSONObject orderCreate(JSONObject param) {
        logger.info("[b2bOrder服务]-到达熔断器 param:"+ param);
        return ValaditionUtils.getResponseTimeOut();
    }

    @Override
    public JSONObject orderSubmit(JSONObject param) {
        logger.info("[b2bOrder服务]-到达熔断器 param:"+ param);
        return ValaditionUtils.getResponseTimeOut();
    }

    @Override
    public JSONObject orderStateChange(JSONObject param) {
        logger.info("[b2bOrder服务]-到达熔断器 param:"+param);
        return ValaditionUtils.getResponseTimeOut();
    }

    @Override
    public JSONObject orderDetail(JSONObject param) {
        logger.info("[b2bOrder服务]-到达熔断器 param:"+ param );
        return ValaditionUtils.getResponseTimeOut();
    }

    @Override
    public JSONObject orderRefund(JSONObject param) {
        logger.info("[b2bOrder服务]-到达熔断器 param:"+ param );
        return ValaditionUtils.getResponseTimeOut();
    }

    @Override
    public JSONObject orderNightCheckStatus(JSONObject param) {
        logger.info("[b2bOrder服务]-到达熔断器 param:"+ param );
        return ValaditionUtils.getResponseTimeOut();
    }

    @Override
    public JSONObject cancelOrder(JSONObject param) {
        logger.info("[b2bOrder服务]-到达熔断器 param:"+ param );
        return ValaditionUtils.getResponseTimeOut();
    }

    @Override
    public JSONObject orderNightCheckResult(JSONObject param) {
        logger.info("[b2bOrder服务]-到达熔断器 param:"+ param );
        return ValaditionUtils.getResponseTimeOut();
    }
}
