package com.fntx.order.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fntx.common.constant.ContactType;
import com.fntx.common.constant.CtripApiErrorCode;
import com.fntx.common.domain.BaseInfo;
import com.fntx.common.domain.BaseReq;
import com.fntx.common.domain.ErrorResp;
import com.fntx.common.utils.ValaditionUtils;
import com.fntx.order.feign.AdminFeign;
import com.fntx.order.po.CheckBookableRequest;
import com.fntx.order.po.CreateOrderRequest;
import com.fntx.order.po.MonitorOrderStateRequest;
import com.fntx.order.service.IOrderService;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Description: 订单服务
 * @Author: 胡庆康
 * @Date: 2019/7/4 14:58
 * <author>     <time>             <version>       <desc>
 * 胡庆康      2019/7/4 14:58          1.0
 */
@RestController
@RequestMapping(value = "order")
public class OrderController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    AdminFeign adminFeign;


    @Autowired
    private IOrderService orderServiceImpl;

    /**
     * 检查房型是否可预订
     *
     * @param param json参数
     * @author: 胡庆康
     * @date: 2019/7/15 14:21
     * @returns: com.alibaba.fastjson.JSONObject
     */
    @RequestMapping(value = "/checkBookable", method = RequestMethod.POST)
    public JSONObject checkBookable(@RequestBody JSONObject param) {
        JSONObject jsonParam = param.getJSONObject("param");
        JSONObject baseInfoParam = param.getJSONObject("baseInfo");
        BaseInfo baseInfo = JSONObject.toJavaObject(baseInfoParam, BaseInfo.class);
        //验参数
        JSONObject isError = ValaditionUtils.parameterVerify(baseInfo, jsonParam);
        if (null != isError) {
            return isError;
        }
        logger.debug("order/checkBookable入参baseInfo:" + baseInfo.toString() + "jsonParam:" + jsonParam);
        CheckBookableRequest request = null;
        try {
            //验token
            request = JSON.toJavaObject(jsonParam, CheckBookableRequest.class);
            String hotelId = request.getAvailRequestSegments().getAvailRequestSegment().getHotelSearchCriteria().getCriterion().getHotelRef().getHotelCode();
            if (!StringUtils.isNotBlank(hotelId) || null == hotelId) {
                return ValaditionUtils.getParamError();
            }
            String redisToken = stringRedisTemplate.opsForValue().get(baseInfo.getAID() + ":" + baseInfo.getSID() + ":" + baseInfo.getUUID() + ":token");
            if (!StringUtils.isNotBlank(redisToken)) {
                return ValaditionUtils.getTokenLose();
            }
            String moduleId = adminFeign.tokenCheck(redisToken, baseInfo.getToken(), hotelId);
            isError = ValaditionUtils.tokenVerify(moduleId);
            if (null != isError) {
                return isError;
            }
            jsonParam.put("moduleId", moduleId);
        } catch (Exception e) {
            //转换实体类错误，获取hotelId错误，或者redis根据分销商身份获取token错误等，返回错误信息
            logger.error("order/checkBookable发生异常，异常信息为：" + e.toString());
            return ValaditionUtils.getParamError();
        }
        jsonParam.put("moduleId", "0");
        JSONObject response = orderServiceImpl.checkBookable(jsonParam);
        if (null == response) {
            return ValaditionUtils.getApiError();
        }
        logger.debug("order/checkBookable返回值为:" + response.toJSONString());
        return response;
    }

    /**
     * 创建订单
     *
     * @param param json参数
     * @author: 胡庆康
     * @date: 2019/7/15 14:21
     * @returns: com.alibaba.fastjson.JSONObject
     */
    @RequestMapping(value = "/orderCreate", method = RequestMethod.POST)
    public JSONObject orderCreate(@RequestBody JSONObject param) {
        JSONObject jsonParam = param.getJSONObject("param");
        JSONObject baseInfoParam = param.getJSONObject("baseInfo");
        BaseInfo baseInfo = JSONObject.toJavaObject(baseInfoParam, BaseInfo.class);
        JSONObject isError = ValaditionUtils.parameterVerify(baseInfo, param);
        if (null != isError) {
            return isError;
        }
        logger.debug("order/orderCreate入参baseInfo:" + baseInfo.toString() + "jsonParam:" + param);
        CreateOrderRequest request = null;
        try {
            //验token
            request = JSON.toJavaObject(jsonParam, CreateOrderRequest.class);
            if (null == request) {
                return ValaditionUtils.getParamError();
            }
            String hotelId = request.getHotelReservations().getHotelReservation().getRoomStays().getRoomStay().getBasicPropertyInfo().HotelCode;
            String roomId = request.getHotelReservations().getHotelReservation().getRoomStays().getRoomStay().getRatePlans().getRatePlan().RoomID;
            if (!StringUtils.isNotBlank(hotelId) || !StringUtils.isNotBlank(roomId) || null == hotelId || null == roomId) {
                return ValaditionUtils.getParamError();
            }
            String redisToken = stringRedisTemplate.opsForValue().get(baseInfo.getAID() + ":" + baseInfo.getSID() + ":" + baseInfo.getUUID() + ":token");
            if (!StringUtils.isNotBlank(redisToken)) {
                return ValaditionUtils.getTokenLose();
            }
            String moduleId = adminFeign.tokenCheck(redisToken, baseInfo.getToken(), hotelId);
            isError = ValaditionUtils.tokenVerify(moduleId);
            if (null != isError) {
                return isError;
            }
            request.getHotelReservations().getHotelReservation().getResGuests().ResGuest.Profiles.ProfileInfo.Profile.Customer.ContactPerson.ContactType = ContactType.Non.getCode();
            jsonParam = JSON.parseObject(JSONObject.toJSONString(request));
            jsonParam.put("moduleId", moduleId);
        } catch (Exception e) {
            //转换实体类错误，获取hotelId错误，或者redis根据分销商身份获取token错误等，返回错误信息
            logger.error("order/orderCreate发生异常，异常信息为：" + e.toString());
            return ValaditionUtils.getParamError();
        }
        jsonParam.put("moduleId", "0");
        JSONObject response = orderServiceImpl.orderCreate(baseInfo, jsonParam);
        if (null == response) {
            return ValaditionUtils.getApiError();
        }
        logger.debug("order/orderCreate返回值为:" + response.toJSONString());
        return response;
    }

    /**
     * 提交订单
     *
     * @param param json参数
     * @author: 胡庆康
     * @date: 2019/7/15 14:21
     * @returns: com.alibaba.fastjson.JSONObject
     */
    @RequestMapping(value = "/orderSubmit", method = RequestMethod.POST)
    public JSONObject orderSubmit(@RequestBody JSONObject param) {
        JSONObject jsonParam = param.getJSONObject("param");
        JSONObject baseInfoParam = param.getJSONObject("baseInfo");
        BaseInfo baseInfo = JSONObject.toJavaObject(baseInfoParam, BaseInfo.class);
        JSONObject isError = ValaditionUtils.parameterVerify(baseInfo, jsonParam);
        if (null != isError) {
            return isError;
        }
        logger.debug("order/orderSubmit入参baseInfo:" + baseInfo.toString() + "jsonParam:" + jsonParam);
        try {
            //验token
            String redisToken = stringRedisTemplate.opsForValue().get(baseInfo.getAID() + ":" + baseInfo.getSID() + ":" + baseInfo.getUUID() + ":token");
            if (!StringUtils.isNotBlank(redisToken)) {
                return ValaditionUtils.getTokenLose();
            }
            String moduleId = adminFeign.tokenCheck(redisToken, baseInfo.getToken(), null);
            isError = ValaditionUtils.tokenVerify(moduleId);
            if (null != isError) {
                return isError;
            }
            jsonParam.put("moduleId", moduleId);
        } catch (Exception e) {
            //redis根据分销商身份获取token错误，返回错误信息
            logger.error("order/orderSubmit发生异常，异常信息为：" + e.toString());
            return ValaditionUtils.getParamError();
        }
        jsonParam.put("moduleId", "0");
        JSONObject response = orderServiceImpl.orderSubmit(baseInfo, jsonParam);
        if (null == response) {
            return ValaditionUtils.getApiError();
        }
        logger.debug("order/orderSubmit返回值为:" + response.toJSONString());
        return response;
    }

    /**
     * 检测订单变化
     *
     * @param param json参数
     * @author: 胡庆康
     * @date: 2019/7/15 14:21
     * @returns: com.alibaba.fastjson.JSONObject
     */
    @RequestMapping(value = "/orderStateChange", method = RequestMethod.POST)
    public JSONObject orderStateChange(@RequestBody JSONObject param) {
        JSONObject jsonParam = param.getJSONObject("param");
        JSONObject baseInfoParam = param.getJSONObject("baseInfo");
        BaseInfo baseInfo = JSONObject.toJavaObject(baseInfoParam, BaseInfo.class);
        JSONObject isError = ValaditionUtils.parameterVerify(baseInfo, jsonParam);
        if (null != isError) {
            return isError;
        }
        logger.debug("order/orderStateChange入参baseInfo:" + baseInfo.toString() + "jsonParam:" + jsonParam);
        MonitorOrderStateRequest requestEntity = null;
        try {
            requestEntity = JSON.<MonitorOrderStateRequest>toJavaObject(jsonParam, MonitorOrderStateRequest.class);
            if (null == requestEntity) {
                return ValaditionUtils.getParamError();
            }
            //验token
            String redisToken = stringRedisTemplate.opsForValue().get(baseInfo.getAID() + ":" + baseInfo.getSID() + ":" + baseInfo.getUUID() + ":token");
            if (!StringUtils.isNotBlank(redisToken)) {
                return ValaditionUtils.getTokenLose();
            }
            String moduleId = adminFeign.tokenCheck(redisToken, baseInfo.getToken(), null);
            isError = ValaditionUtils.tokenVerify(moduleId);
            if (null != isError) {
                return isError;
            }
            jsonParam.put("moduleId", moduleId);
        } catch (Exception e) {
            //redis根据分销商身份获取token错误，返回错误信息
            logger.error("order/orderStateChange发生异常，异常信息为：" + e.toString());
            return ValaditionUtils.getParamError();
        }
        if (!StringUtils.isNotBlank(requestEntity.getHotelReservations().get(0).getLastModifyDateTime()) || null == requestEntity.getHotelReservations().get(0).getLastModifyDateTime()) {
            //redis根据分销商身份获取token错误，返回错误信息
            return ValaditionUtils.getParamError();
        }
        jsonParam.put("moduleId", "0");
        JSONObject response = orderServiceImpl.orderStateChange(baseInfo, jsonParam);
        if (null == response) {
            return ValaditionUtils.getApiError();
        }
        logger.debug("order/orderStateChange返回值为:" + response.toJSONString());
        return response;
    }

    /**
     * 获取订单详情
     *
     * @param param json参数
     * @author: 胡庆康
     * @date: 2019/7/15 14:21
     * @returns: com.alibaba.fastjson.JSONObject
     */
    @RequestMapping(value = "/orderDetail", method = RequestMethod.POST)
    public JSONObject orderDetail(@RequestBody JSONObject param) {
        JSONObject jsonParam = param.getJSONObject("param");
        JSONObject baseInfoParam = param.getJSONObject("baseInfo");
        BaseInfo baseInfo = JSONObject.toJavaObject(baseInfoParam, BaseInfo.class);
        JSONObject isError = ValaditionUtils.parameterVerify(baseInfo, jsonParam);
        if (null != isError) {
            return isError;
        }
        logger.debug("order/orderDetail入参baseInfo:" + baseInfo.toString() + "jsonParam:" + jsonParam);
        try {
            //验token
            String redisToken = stringRedisTemplate.opsForValue().get(baseInfo.getAID() + ":" + baseInfo.getSID() + ":" + baseInfo.getUUID() + ":token");
            if (!StringUtils.isNotBlank(redisToken)) {
                return ValaditionUtils.getTokenLose();
            }
            String moduleId = adminFeign.tokenCheck(redisToken, baseInfo.getToken(), null);
            isError = ValaditionUtils.tokenVerify(moduleId);
            if (null != isError) {
                return isError;
            }
            jsonParam.put("moduleId", moduleId);
        } catch (Exception e) {

            //redis根据分销商身份获取token错误，返回错误信息
            logger.error("order/orderDetail发生异常，异常信息为：" + e.toString());
            return ValaditionUtils.getParamError();
        }
        jsonParam.put("moduleId", "0");
        JSONObject response = orderServiceImpl.orderDetail(baseInfo, jsonParam);
        if (null == response) {
            return ValaditionUtils.getApiError();
        }
        logger.debug("order/orderStateChange返回值为:" + response.toJSONString());
        return response;
    }

    /**
     * @Copyright (C), 2019, 弗恩天下
     * @Author: 渠猛
     * @Date: 2019/7/9 0009 下午 2:51
     * @Description: 取消订单
     */
    @RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
    public Object cancelOrder(@RequestBody JSONObject param) {
        JSONObject orderRefundReqVo = param.getJSONObject("param");
        JSONObject baseInfo = param.getJSONObject("baseInfo");
        BaseInfo baseReqVo = JSONObject.toJavaObject(baseInfo, BaseInfo.class);
        logger.info("order/cancelOrder 入参为baseReqVo：" + baseReqVo + ";orderRefundReqVo:" + orderRefundReqVo);
        try {
            //验token
            String redisToken = stringRedisTemplate.opsForValue().get(baseReqVo.getAID() + ":" + baseReqVo.getSID() + ":" + baseReqVo.getUUID() + ":token");
            if (StringUtils.isEmpty(redisToken) || !redisToken.equals(baseReqVo.getToken())) {
                ErrorResp errorResp = new ErrorResp();
                errorResp.setErrCode(CtripApiErrorCode.tokeninvalid.getCode());
                errorResp.setErrMsg(CtripApiErrorCode.tokeninvalid.getName());
                return errorResp;
            }
            String moduleId = adminFeign.tokenCheck(redisToken, baseReqVo.getToken(), null);
            JSONObject isError = ValaditionUtils.tokenVerify(moduleId);
            if (null != isError) {
                return isError;
            }
            orderRefundReqVo.put("moduleId", moduleId);
        } catch (Exception e) {
            //redis根据分销商身份获取token错误，返回错误信息
            logger.error("order/cancelOrder 发生异常，异常信息为：" + e.toString());
            return ValaditionUtils.getApiError();
        }
        return orderServiceImpl.cancelOrder(baseReqVo, orderRefundReqVo);
    }

    /**
     * @Copyright (C), 2019, 弗恩天下
     * @Author: 渠猛
     * @Date: 2019/7/9 0009 上午 11:05
     * @Description: 获取订单部分退款
     */
    @RequestMapping(value = "/orderRefund", method = RequestMethod.POST)
    public Object getOrderRefund(@RequestBody JSONObject param) {
        JSONObject orderRefundReqVo = param.getJSONObject("param");
        JSONObject baseInfo = param.getJSONObject("baseInfo");
        BaseInfo baseReqVo = JSONObject.toJavaObject(baseInfo, BaseInfo.class);
        try {
            //验token
            String redisToken = stringRedisTemplate.opsForValue().get(baseReqVo.getAID() + ":" + baseReqVo.getSID() + ":" + baseReqVo.getUUID() + ":token");
            if (StringUtils.isEmpty(redisToken) || !redisToken.equals(baseReqVo.getToken())) {
                ErrorResp errorResp = new ErrorResp();
                errorResp.setErrCode(CtripApiErrorCode.tokeninvalid.getCode());
                errorResp.setErrMsg(CtripApiErrorCode.tokeninvalid.getName());
                return errorResp;
            }
            String moduleId = adminFeign.tokenCheck(redisToken, baseReqVo.getToken(), null);
            JSONObject isError = ValaditionUtils.tokenVerify(moduleId);
            if (null != isError) {
                return isError;
            }
            orderRefundReqVo.put("moduleId", moduleId);
        } catch (Exception e) {
            //redis根据分销商身份获取token错误，返回错误信息
            logger.error("order/orderRefund 发生异常，异常信息为：" + e.toString());
            return ValaditionUtils.getParamError();
        }
        return orderServiceImpl.getOrderRefund(orderRefundReqVo);
    }

    /**
     * @Copyright (C), 2019, 弗恩天下
     * @Author: 渠猛
     * @Date: 2019/7/9 0009 下午 2:50
     * @Description: 获取订单夜审状态
     */
    @RequestMapping(value = "/orderNightCheckStatus", method = RequestMethod.POST)
    public Object getOrderNightCheckStatus(@RequestBody JSONObject param) {
        JSONObject orderRefundReqVo = param.getJSONObject("param");
        JSONObject baseInfo = param.getJSONObject("baseInfo");
        BaseInfo baseReqVo = JSONObject.toJavaObject(baseInfo, BaseInfo.class);
        try {
            //验token
            String redisToken = stringRedisTemplate.opsForValue().get(baseReqVo.getAID() + ":" + baseReqVo.getSID() + ":" + baseReqVo.getUUID() + ":token");
            if (StringUtils.isEmpty(redisToken) || !redisToken.equals(baseReqVo.getToken())) {
                ErrorResp errorResp = new ErrorResp();
                errorResp.setErrCode(CtripApiErrorCode.tokeninvalid.getCode());
                errorResp.setErrMsg(CtripApiErrorCode.tokeninvalid.getName());
                return errorResp;
            }
            String moduleId = adminFeign.tokenCheck(redisToken, baseReqVo.getToken(), null);
            JSONObject isError = ValaditionUtils.tokenVerify(moduleId);
            if (null != isError) {
                return isError;
            }
            orderRefundReqVo.put("moduleId", moduleId);
        } catch (Exception e) {
            //redis根据分销商身份获取token错误，返回错误信息
            logger.error("order/orderNightCheckStatus 发生异常，异常信息为：" + e.toString());
            return ValaditionUtils.getParamError();
        }
        return orderServiceImpl.getOrderNightCheckStatus(orderRefundReqVo);
    }


    /**
     * @Copyright (C), 2019, 弗恩天下
     * @Author: 渠猛
     * @Date: 2019/7/9 0009 下午 2:49
     * @Description: 获取订单夜审结果
     */
    @RequestMapping(value = "/orderNightCheckResult", method = RequestMethod.POST)
    public Object getOrderNightCheckResult(@RequestBody JSONObject param) {
        JSONObject orderRefundReqVo = param.getJSONObject("param");
        JSONObject baseInfo = param.getJSONObject("baseInfo");
        BaseInfo baseReqVo = JSONObject.toJavaObject(baseInfo, BaseInfo.class);
        try {
            //验token
            String redisToken = stringRedisTemplate.opsForValue().get(baseReqVo.getAID() + ":" + baseReqVo.getSID() + ":" + baseReqVo.getUUID() + ":token");
            if (StringUtils.isEmpty(redisToken) || !redisToken.equals(baseReqVo.getToken())) {
                ErrorResp errorResp = new ErrorResp();
                errorResp.setErrCode(CtripApiErrorCode.tokeninvalid.getCode());
                errorResp.setErrMsg(CtripApiErrorCode.tokeninvalid.getName());
                return errorResp;
            }
            String moduleId = adminFeign.tokenCheck(redisToken, baseReqVo.getToken(), null);
            JSONObject isError = ValaditionUtils.tokenVerify(moduleId);
            if (null != isError) {
                return isError;
            }
            orderRefundReqVo.put("moduleId", moduleId);
        } catch (Exception e) {
            //redis根据分销商身份获取token错误，返回错误信息
            logger.error("order/orderNightCheckResult 发生异常，异常信息为：" + e.toString());
            return ValaditionUtils.getParamError();
        }
        return orderServiceImpl.getOrderNightCheckResult(orderRefundReqVo);
    }


}