package com.fntx.sdk.feign;

import com.alibaba.fastjson.JSONObject;
import com.fntx.common.domain.BaseInfo;
import com.fntx.common.domain.BaseReq;
import com.fntx.sdk.feign.hystrix.OrderHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @FileName: 订单服务调用
 * @Author: 胡庆康
 * @Date: 2019/6/26 9:39
 * @Description:
 */
@FeignClient(value = "b2bOrder", fallback = OrderHystrix.class)
public interface OrderFeign {

    /**
     * 检查房型是否可预订
     *
     * @param param json参数
     * @author 胡庆康
     * @date 2019/7/23 20:21
     * @returns
     */
    @RequestMapping(value = "/order/checkBookable", method = RequestMethod.POST)
    JSONObject checkBookable(@RequestBody JSONObject param);

    /**
     * 创建订单
     *
     * @param param json参数
     * @author 胡庆康
     * @date 2019/7/23 20:21
     * @returns
     */
    @RequestMapping(value = "/order/orderCreate", method = RequestMethod.POST)
    public JSONObject orderCreate(@RequestBody JSONObject param);

    /**
     * 提交订单
     *
     * @param param json参数
     * @author 胡庆康
     * @date 2019/7/23 20:21
     * @returns
     */
    @RequestMapping(value = "/order/orderSubmit", method = RequestMethod.POST)
    public JSONObject orderSubmit(@RequestBody JSONObject param);

    /**
     * 检测订单变化
     *
     * @param param json参数
     * @author 胡庆康
     * @date 2019/7/23 20:21
     * @returns
     */
    @RequestMapping(value = "/order/orderStateChange", method = RequestMethod.POST)
    public JSONObject orderStateChange(@RequestBody JSONObject param);

    /**
     * 获取订单详情
     *
     * @param param json参数
     * @author 胡庆康
     * @date 2019/7/23 20:21
     * @returns
     */

    @RequestMapping(value = "/order/orderDetail", method = RequestMethod.POST)
    public JSONObject orderDetail(@RequestBody JSONObject param);

    /**
     * 获取订单部分退款
     *
     * @param param json参数
     * @author 渠猛
     * @date 2019/7/23 20:21
     * @returns
     */
    @RequestMapping(value = "/order/orderRefund", method = RequestMethod.POST)
    Object orderRefund(@RequestBody JSONObject param);

    /**
     * 获取订单夜审状态
     *
     * @param param json参数
     * @author 渠猛
     * @date 2019/7/23 20:21
     * @returns
     */
    @RequestMapping(value = "/order/orderNightCheckStatus", method = RequestMethod.POST)
    JSONObject orderNightCheckStatus(@RequestBody JSONObject param);

    /**
     * 取消订单
     *
     * @param param json参数
     * @author 渠猛
     * @date 2019/7/23 20:21
     * @returns
     */
    @RequestMapping(value = "/order/cancelOrder", method = RequestMethod.POST)
    Object cancelOrder(@RequestBody JSONObject param);

    /**
     * 获取订单夜审结果
     *
     * @param param json参数
     * @author 渠猛
     * @date 2019/7/23 20:21
     * @returns
     */
    @RequestMapping(value = "/order/orderNightCheckResult", method = RequestMethod.POST)
    JSONObject orderNightCheckResult(@RequestBody JSONObject param);
}
