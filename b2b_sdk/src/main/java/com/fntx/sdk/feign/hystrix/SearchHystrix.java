package com.fntx.sdk.feign.hystrix;

import com.alibaba.fastjson.JSONObject;
import com.fntx.common.constant.CtripApiErrorCode;
import com.fntx.sdk.feign.SearchFeign;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * @Copyright (C), 2019, 弗恩天下
 * @Author: 王俊文
 * @Date: 2019/7/29 17:39
 * @Description:查询相关服务调用
 */
@Component
public class SearchHystrix implements SearchFeign {
    @Override
    public JSONObject hotelList(@RequestBody JSONObject map) {
        JSONObject resultModel = new JSONObject(16);
        resultModel.put("ErrCode", CtripApiErrorCode.ACCESS_TIMEOUT.getCode());
        resultModel.put("ErrMsg", CtripApiErrorCode.ACCESS_TIMEOUT.getName());
        return resultModel;
    }

    @Override
    public JSONObject hotelDetail(@RequestBody JSONObject map) {
        JSONObject resultModel = new JSONObject(16);
        resultModel.put("ErrCode", CtripApiErrorCode.ACCESS_TIMEOUT.getCode());
        resultModel.put("ErrMsg", CtripApiErrorCode.ACCESS_TIMEOUT.getName());
        return resultModel;
    }

    @Override
    public JSONObject hotelRoomtypeList(@RequestBody JSONObject map) {
        JSONObject resultModel = new JSONObject(16);
        resultModel.put("ErrCode", CtripApiErrorCode.ACCESS_TIMEOUT.getCode());
        resultModel.put("ErrMsg", CtripApiErrorCode.ACCESS_TIMEOUT.getName());
        return resultModel;
    }

    @Override
    public JSONObject hotelIncrData(@RequestBody JSONObject map) {
        JSONObject resultModel = new JSONObject(16);
        resultModel.put("ErrCode", CtripApiErrorCode.ACCESS_TIMEOUT.getCode());
        resultModel.put("ErrMsg", CtripApiErrorCode.ACCESS_TIMEOUT.getName());
        return resultModel;
    }

    @Override
    public JSONObject getHotelIncrRoomtype(@RequestBody JSONObject map) {
        JSONObject resultModel = new JSONObject(16);
        resultModel.put("ErrCode", CtripApiErrorCode.ACCESS_TIMEOUT.getCode());
        resultModel.put("ErrMsg", CtripApiErrorCode.ACCESS_TIMEOUT.getName());
        return resultModel;
    }

    @Override
    public JSONObject getHotelBaseprice(@RequestBody JSONObject map) {
        JSONObject resultModel = new JSONObject(16);
        resultModel.put("ErrCode", CtripApiErrorCode.ACCESS_TIMEOUT.getCode());
        resultModel.put("ErrMsg", CtripApiErrorCode.ACCESS_TIMEOUT.getName());
        return resultModel;
    }

    @Override
    public JSONObject getHotelIoprice(@RequestBody JSONObject map) {
        JSONObject resultModel = new JSONObject(16);
        resultModel.put("ErrCode", CtripApiErrorCode.ACCESS_TIMEOUT.getCode());
        resultModel.put("ErrMsg", CtripApiErrorCode.ACCESS_TIMEOUT.getName());
        return resultModel;
    }

    @Override
    public JSONObject hotelIpriceTestPrice(@RequestBody JSONObject map) {
        JSONObject resultModel = new JSONObject(16);
        resultModel.put("ErrCode", CtripApiErrorCode.ACCESS_TIMEOUT.getCode());
        resultModel.put("ErrMsg", CtripApiErrorCode.ACCESS_TIMEOUT.getName());
        return resultModel;
    }
}
