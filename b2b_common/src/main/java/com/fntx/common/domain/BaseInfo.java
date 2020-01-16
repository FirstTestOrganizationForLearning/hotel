package com.fntx.common.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 分销商信息
 * @Copyright (C), 2019, 弗恩天下
 * @FileName:
 * @Author: 胡庆康
 * @Date: 2019/7/18 15:35
 */
@Data
public class BaseInfo {
    private String AID;
    private String SID;
    private String ICODE;
    private String UUID;
    private String Token;
    private String mode;
    private String format;

    @JSONField(name = "AID")
    public String getAID() {
        return AID;
    }

    public void setAID(String AID) {
        this.AID = AID;
    }
    @JSONField(name = "SID")
    public String getSID() {
        return SID;
    }

    public void setSID(String SID) {
        this.SID = SID;
    }
    @JSONField(name = "ICODE")
    public String getICODE() {
        return ICODE;
    }

    public void setICODE(String ICODE) {
        this.ICODE = ICODE;
    }
    @JSONField(name = "UUID")
    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }
    @JSONField(name = "Token")
    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
    @JSONField(name = "mode")
    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
    @JSONField(name = "format")
    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
