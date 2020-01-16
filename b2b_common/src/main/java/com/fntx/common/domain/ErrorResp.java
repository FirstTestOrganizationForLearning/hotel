package com.fntx.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fntx.common.constant.HtErrorType;

import java.io.Serializable;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @Author: 渠猛
 * @Date: 2019/7/10 0010 下午 4:54
 * @Description:
 */

public class ErrorResp implements Serializable {

    /**
     * ErrCode : 1001
     * ErrMsg : INVALID_PARAMETERS
     */
    @JsonIgnore
    private String ErrCode;
    @JsonIgnore
    private String ErrMsg;

    public String getErrCode() {
        return ErrCode;
    }

    public void setErrCode(String ErrCode) {
        this.ErrCode = ErrCode;
    }

    public String getErrMsg() {
        return ErrMsg;
    }

    public void setErrMsg(String ErrMsg) {
        this.ErrMsg = ErrMsg;
    }

    public ErrorResp() {
    }

    public ErrorResp(String errCode, String errMsg) {
        ErrCode = errCode;
        ErrMsg = errMsg;
    }

    public ErrorResp paramsError(){
        this.setErrCode(HtErrorType.INVALID_PARAMETERS.getCode());
        this.setErrMsg(HtErrorType.INVALID_PARAMETERS.getName());
      return this;
    };

    @Override
    public String toString() {
        return "ErrorResp{" +
                "ErrCode='" + ErrCode + '\'' +
                ", ErrMsg='" + ErrMsg + '\'' +
                '}';
    }
}
