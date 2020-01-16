package com.fntx.order.po;

import java.util.List;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @Author: 渠猛
 * @Date: 2019/7/23 0023 下午 4:19
 * @Description: 携程取消订单返回值
 */
public class CancelOrderResp {

    /**
     * 下面就是取消订单成功后返回的实体结构,主要判断标示是 如果反悔了Success空对象就表示成功了
     * Success : {}
     * Warnings : []
     * Errors : []
     * TimeStamp : 2018-01-15T18:01:43.6415430+08:00
     * Version : 1
     * PrimaryLangID : en
     */

    private Success Success;
    private String TimeStamp;
    private int Version;
    private String PrimaryLangID;
    private List<?> Warnings;
    private List<?> Errors;

    public Success getSuccess() {
        return Success;
    }

    public void setSuccess(Success Success) {
        this.Success = Success;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String TimeStamp) {
        this.TimeStamp = TimeStamp;
    }

    public int getVersion() {
        return Version;
    }

    public void setVersion(int Version) {
        this.Version = Version;
    }

    public String getPrimaryLangID() {
        return PrimaryLangID;
    }

    public void setPrimaryLangID(String PrimaryLangID) {
        this.PrimaryLangID = PrimaryLangID;
    }

    public List<?> getWarnings() {
        return Warnings;
    }

    public void setWarnings(List<?> Warnings) {
        this.Warnings = Warnings;
    }

    public List<?> getErrors() {
        return Errors;
    }

    public void setErrors(List<?> Errors) {
        this.Errors = Errors;
    }

    public static class Success {
    }
}
