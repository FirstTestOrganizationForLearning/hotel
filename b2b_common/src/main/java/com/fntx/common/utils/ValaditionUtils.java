package com.fntx.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fntx.common.constant.CtripApiErrorCode;
import com.fntx.common.domain.BaseInfo;
import com.fntx.common.domain.ErrorResp;
import org.apache.commons.lang.StringUtils;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @FileName
 * @Author 胡庆康
 * @Date 2019/7/25 11:23
 */
public class ValaditionUtils {
    public static JSONObject parameterVerify(BaseInfo baseInfo, JSONObject param) {
        if (baseInfo == null || param == null || param.size() == 0) {
            ErrorResp errorResp = new ErrorResp();
            errorResp.setErrCode(CtripApiErrorCode.InvalidSIdOrAllianceId.getCode());
            errorResp.setErrMsg(CtripApiErrorCode.InvalidSIdOrAllianceId.getName());
            return JSON.parseObject(JSONObject.toJSONString(errorResp));
        } else {
            return null;
        }
    }
    /**
     * token失效
     *
     * @author 胡庆康
     * @date 2019/7/25 17:34
     * @returns
     */
    public static JSONObject getTokenLose() {
        ErrorResp errorResp = new ErrorResp();
        errorResp.setErrCode(CtripApiErrorCode.tokeninvalid.getCode());
        errorResp.setErrMsg(CtripApiErrorCode.tokeninvalid.getName());
        return JSON.parseObject(JSONObject.toJSONString(errorResp));
    }
       /**
     * 参数错误
     *
     * @author 胡庆康
     * @date 2019/7/25 17:34
     * @returns
     */
    public static JSONObject getParamError() {
        ErrorResp errorResp = new ErrorResp();
        errorResp.setErrCode(CtripApiErrorCode.InvalidSIdOrAllianceId.getCode());
        errorResp.setErrMsg(CtripApiErrorCode.InvalidSIdOrAllianceId.getName());
        return JSON.parseObject(JSONObject.toJSONString(errorResp));
    }
    /**
     * 参数错误
     *
     * @param info 错误信息
     * @author 胡庆康
     * @date 2019/7/25 17:34
     * @returns
     */
    public static JSONObject getParamError(String info) {
        ErrorResp errorResp = new ErrorResp();
        errorResp.setErrCode(CtripApiErrorCode.InvalidSIdOrAllianceId.getCode());
        errorResp.setErrMsg(info);
        return JSON.parseObject(JSONObject.toJSONString(errorResp));
    }

    /**
     * token验证
     *
     * @param moduleId
     * @return
     */
    public static JSONObject tokenVerify(String moduleId) {
        if (!"0".equals(moduleId) && !"1".equals(moduleId)) {
            ErrorResp errorResp = new ErrorResp();
            errorResp.setErrCode(CtripApiErrorCode.PermissionDenied.getCode());
            errorResp.setErrMsg(CtripApiErrorCode.PermissionDenied.getName());
            return JSON.parseObject(JSONObject.toJSONString(errorResp));
        } else {
            return null;
        }
    }

    /**
     * api内部错误
     *
     * @author 胡庆康
     * @date 2019/7/25 17:36
     * @returns
     */
    public static JSONObject getApiError() {
        ErrorResp errorResp = new ErrorResp();
        errorResp.setErrCode(CtripApiErrorCode.ApiInternalError.getCode());
        errorResp.setErrMsg(CtripApiErrorCode.ApiInternalError.getName());
        return JSONObject.parseObject(JSON.toJSONString(errorResp));
    }
    /**
     * api内部错误
     *
     * @author 胡庆康
     * @date 2019/7/25 17:36
     * @returns
     */
    public static JSONObject getApiError(String msg) {
        ErrorResp errorResp = new ErrorResp();
        errorResp.setErrCode(CtripApiErrorCode.ApiInternalError.getCode());
        errorResp.setErrMsg(msg);
        return JSONObject.parseObject(JSON.toJSONString(errorResp));
    }
    /**
     * 访问超时
     *
     * @author 胡庆康
     * @date 2019/7/25 17:36
     * @returns
     */
    public static JSONObject getResponseTimeOut() {
        ErrorResp errorResp = new ErrorResp();
        errorResp.setErrCode(CtripApiErrorCode.ACCESS_TIMEOUT.getCode());
        errorResp.setErrMsg(CtripApiErrorCode.ACCESS_TIMEOUT.getName());
        return JSONObject.parseObject(JSON.toJSONString(errorResp));
    }
}
