package com.fntx.sdk.controller;

import com.alibaba.fastjson.JSONObject;
import com.fntx.common.constant.CtripApiErrorCode;
import com.fntx.common.domain.BaseInfo;
import com.fntx.common.utils.ValaditionUtils;
import com.fntx.sdk.feign.AdminFeign;
import com.fntx.sdk.feign.OrderFeign;
import com.fntx.sdk.feign.SearchFeign;
import com.fntx.sdk.utils.ApiFrequencyCheck;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 服务分发对外接口
 *
 * @Copyright (C), 2019, 弗恩天下
 * @FileName:
 * @Author: 胡庆康
 * @Date: 2019/7/9 9:50
 * @Description:
 */
@RestController
@RequestMapping(value = "openservice")
public class ServiceDeliveryController {

    private static Logger logger = LoggerFactory.getLogger(ServiceDeliveryController.class);

    @Autowired
    private AdminFeign adminFeign;
    @Autowired
    private OrderFeign orderFeign;
    @Autowired
    private SearchFeign searchFeign;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ApiFrequencyCheck apiFrequencyCheck;

    @RequestMapping(value = "serviceproxy.ashx")
    public Object getApi(@RequestBody JSONObject param, @ModelAttribute BaseInfo baseInfo) {
        long startTime=System.currentTimeMillis();
        if (null == param || param.size() == 0 ||
                !StringUtils.isNotBlank(baseInfo.getAID()) ||
                !StringUtils.isNotBlank(baseInfo.getSID()) ||
                !StringUtils.isNotBlank(baseInfo.getICODE()) ||
                !StringUtils.isNotBlank(baseInfo.getUUID()) ||
                !StringUtils.isNotBlank(baseInfo.getFormat()) ||
                !StringUtils.isNotBlank(baseInfo.getMode()) ||
                !StringUtils.isNotBlank(baseInfo.getToken())) {
            return ValaditionUtils.getParamError();
        }
        JSONObject paramMap = new JSONObject(16);
                paramMap.put("baseInfo", JSONObject.toJSON(baseInfo));
                paramMap.put("param", param);
        JSONObject response = new JSONObject();

        switch (baseInfo.getICODE())
        {
            case "hotel.list":
                {response = searchFeign.hotelList(paramMap);
                break;}
            case "hotel.detail":
                {response = searchFeign.hotelDetail(paramMap);
                break;}
            case "hotel.roomtype.list":
                {response = searchFeign.hotelRoomtypeList(paramMap);
                break;}
            case "hotel.incr.data":
                {response = searchFeign.hotelIncrData(paramMap);
                break;}
            case "hotel.incr.roomtype":
                {response = searchFeign.getHotelIncrRoomtype(paramMap);
                break;}
            case "hotel.incr.ioprice":
                {break;}
            case "hotel.simpleioprice":
                {break;}
            case "hotel.order.nightcheck": {
                response = orderFeign.orderNightCheckStatus(paramMap);
                break;
            }
            case "hotel.order.nightcheck.postback":
                {response = orderFeign.orderNightCheckResult(paramMap);
                break;}
            default:
            {
                 //校验访问频次
                try
                {
                    boolean signLock = apiFrequencyCheck.apiFrequencyCheck(baseInfo.getAID(),
                            baseInfo.getSID(), baseInfo.getICODE());
                    if ( signLock )
                    {
                        switch (baseInfo.getICODE())
                        {
                            case "hotel.baseprice":
                                {response = searchFeign.getHotelBaseprice(paramMap);
                                break;}
                            case "hotel.ioprice":
                                {response = searchFeign.getHotelIoprice(paramMap);
                                break;}
                            case "hotel.bookable.check":
                                {response = orderFeign.checkBookable(paramMap);
                                break;}
                            case "hotel.order.create":
                                {response = orderFeign.orderCreate(paramMap);
                                break;}
                            case "hotel.order.submit":
                                {response = orderFeign.orderSubmit(paramMap);
                                break;}
                            case "hotel.order.statechange":
                                {response = orderFeign.orderStateChange(paramMap);
                                break;}
                            case "hotel.order.detail":
                                {response = orderFeign.orderDetail(paramMap);
                                break;}
                            case "hotel.order.cancel":
                                {return orderFeign.cancelOrder(paramMap);}
                            case "hotel.order.refund":
                                 {return orderFeign.orderRefund(paramMap);}
                            default:
                                {return ValaditionUtils.getParamError();}
                        }
                    }else
                    {
                        response.put("ErrCode", CtripApiErrorCode.RateLimit.getCode());
                        response.put("ErrMsg", CtripApiErrorCode.RateLimit.getName());
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                    response.put("ErrCode", CtripApiErrorCode.ApiInternalError.getCode());
                    response.put("ErrMsg", CtripApiErrorCode.ApiInternalError.getName());
                }
            }
        }
        long endTime=System.currentTimeMillis();
        logger.info("主程序运行时间： "+(endTime-startTime) + "毫秒");
        return response;
    }
}