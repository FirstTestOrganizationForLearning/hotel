package com.fntx.order.po;

import java.util.List;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @Author: 渠猛
 * @Date: 2019/7/19 0019 下午 3:01
 * @Description: 取消订单 入参模型
 */
public class OrderCancelReq {
    /**
     * UniqueID : [{"Type":"1","ID":"92893cf1-8c92-4fb2-9fef-3570535f2dc4"},{"Type":"28","ID":"1"},{"Type":"503","ID":"50"},{"Type":"501","ID":"3018930191"}]
     * Reasons : {"Reason":{"Type":"501"}}
     * TimeStamp : 2018-01-15T18:01:43.5106879
     * Version : 1
     * PrimaryLangID : en
     */

    private Reasons Reasons;
    private String TimeStamp;
    private int Version;
    private String PrimaryLangID;
    private List<UniqueID> UniqueID;

    public Reasons getReasons() {
        return Reasons;
    }

    public void setReasons(Reasons Reasons) {
        this.Reasons = Reasons;
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

    public List<UniqueID> getUniqueID() {
        return UniqueID;
    }

    public void setUniqueID(List<UniqueID> UniqueID) {
        this.UniqueID = UniqueID;
    }
}
