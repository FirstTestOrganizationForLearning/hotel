package com.fntx.order.service;

import com.alibaba.fastjson.JSONObject;
import com.fntx.common.domain.BaseInfo;
import com.fntx.common.domain.BaseReq;
import com.fntx.order.domain.HtOrderOplog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单日志 服务类
 * </p>
 *
 * @author kang
 * @since 2019-07-17
 */
public interface HtOrderOplogService extends IService<HtOrderOplog> {
    /**
     * @Copyright (C), 2019, 弗恩天下
     * @Author: 渠猛
     * @Date: 2019/7/19 0019 下午 5:55
     * @Description: 酒店订单记录日志通用方法
     * baseReqVo;授权信息
     * req 接口入参
     * content 日志信息内容
     */
    void createHtLogger(BaseInfo baseReqVo, JSONObject req, String content, String orderId);

    /**
     * 酒店订单记录日志
     *
     * @param baseInfo 分销商信息
     * @param opType   操作类型
     * @param content  日志内容
     * @param memo     备注信息
     * @param orderId  订单号
     * @copyright (C), 2019, 弗恩天下
     * @author 胡庆康
     * @date: 2019/7/26 14:32
     * @description:
     */
    void insertOrderOpLog(BaseInfo baseInfo, int opType, String memo, String content, String orderId);

}
