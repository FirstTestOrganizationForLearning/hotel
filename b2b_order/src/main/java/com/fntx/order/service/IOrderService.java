package com.fntx.order.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @Description
 * @Author 胡庆康
 * @Date 2019/7/4 1626
 * @Param
 * @returns
 * @History <author>     <time>             <version>       <desc>
 * 胡庆康      2019/7/4 1626          1.0
 */
import com.fntx.common.domain.BaseInfo;
import com.fntx.common.domain.BaseReq;
import com.fntx.order.po.GetOrderDetailResponse;

public interface IOrderService {
    /**
     * 检查房型是否可用
     *
     * @param param json参数
     * @author 胡庆康
     * @date 2019/7/10 1039
     * @returns com.alibaba.fastjson.JSONObject
     */
    JSONObject checkBookable(JSONObject param);

    /**
     * 创建订单
     *
     * @param baseInfo 分销商信息
     * @param param    json参数
     * @author 胡庆康
     * @date 2019/7/10 1039
     * @returns com.alibaba.fastjson.JSONObject
     */
    JSONObject orderCreate(BaseInfo baseInfo, JSONObject param);

    /**
     * 提交订单
     *
     * @param baseInfo 分销商信息
     * @param param    json参数
     * @author 胡庆康
     * @date 2019/7/10 1039
     * @returns com.alibaba.fastjson.JSONObject
     */
    JSONObject orderSubmit(BaseInfo baseInfo, JSONObject param);

    /**
     * 监测订单状态变化
     *
     * @param baseInfo 分销商信息
     * @param param    json参数
     * @author 胡庆康
     * @date 2019/7/10 1039
     * @returns com.alibaba.fastjson.JSONObject
     * 解决一个分销商获取到所有状态变化的订单的问题，弗恩统一定时同步订单状态， 然后分销商调用该接口时，从数据库中查询变化的订单，并返回结果
     */
    JSONObject orderStateChange(BaseInfo baseInfo, JSONObject param);

    /**
     * 获取订单详情
     *
     * @param param    json参数
     * @param baseInfo 分销商基础信息
     * @author 胡庆康
     * @date 2019/7/10 1039
     * @returns com.alibaba.fastjson.JSONObject
     */
    JSONObject orderDetail(BaseInfo baseInfo, JSONObject param);

    /**
     * 获取订单详情实体类
     *
     * @param strOrderId 订单号
     * @param moduleId   模块 0国内 1国外
     * @author: 胡庆康
     * @date: 2019/7/22 16:56
     * @returns:
     */
    public GetOrderDetailResponse getOrderDetailResponse(String strOrderId, String moduleId);

    /**
     * 远程调用公共方法
     *
     * @param param  json参数
     * @param master 正式icode
     * @param test   测试icode
     * @author 胡庆康
     * @date 2019/7/10 1155
     * @returns com.alibaba.fastjson.JSONObject
     */
    JSONObject getResponse(JSONObject param, String master, String test);


    /**
     * 远程调用公共方法(区分国内,外)
     *
     * @param param  json参数
     * @param master 正式icode
     * @param test   测试icode
     * @author 渠猛
     * @date 2019/9/11 1155
     * @returns com.alibaba.fastjson.JSONObject
     */
    JSONObject getResponseNew(JSONObject param, String master, String test);

    /**
     * 同步订单到智科库，临时用
     * @author 胡庆康
     * @date 2019/9/25 16:26
     * @returns
     */
    JSONObject orderSync();

    /**
     * @Copyright (C), 2019, 弗恩天下
     * @Author 渠猛
     * @Date 2019/7/9 0009 下午 257
     * @Description 取消酒店订单
     */
    Object cancelOrder(BaseInfo baseReq, JSONObject orderRefundReqVo);

    /**
     * 获取订单夜审状态
     *
     * @param param json参数
     * @author 渠猛
     * @date 2019/7/10 1039
     * @returns com.alibaba.fastjson.JSONObject
     */
    JSONObject getOrderNightCheckStatus(JSONObject param);

    /**
     * 获取订单退款
     *
     * @param orderRefundReqVo json参数
     * @author 渠猛
     * @date 2019/7/10 1039
     * @returns com.alibaba.fastjson.JSONObject
     */
    JSONObject getOrderRefund(JSONObject orderRefundReqVo);

    /**
     * 获取订单夜审结果
     *
     * @param orderRefundReqVo json参数
     * @author 渠猛
     * @date 2019/7/10 1039
     * @returns com.alibaba.fastjson.JSONObject
     */
    JSONObject getOrderNightCheckResult(JSONObject orderRefundReqVo);
}
