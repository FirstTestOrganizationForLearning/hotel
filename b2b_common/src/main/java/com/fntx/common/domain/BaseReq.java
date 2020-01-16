package com.fntx.common.domain;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @Author: 渠猛
 * @Date: 2019/7/4 0004 下午 2:13
 * @Description: 请求基础类
 */
public  class BaseReq {
    //分销商或者弗恩的AID
    private String SID;
    //分销商或弗恩的站点SID
    private String AID;
   //不同的 ICODE 指向不同的 Service AP
   private String ICODE;
   //分销商的用户 ID（理论上），实际上可以只生成一个 ID 来代表
   //分销商自己的身份。(弗恩也是携程的分销商)
   private String UUID;
    //令牌
    private String Token;
   //写死为 1
   private String Mode;
   // JSON或XML  只能是JSON(默认是JSON)
   private String Format;
   //模块id
   private String ModuleId;
    //key  授权秘钥
    private String KEY;
    //refreshToken
    private String Refresh_Token;
    /**
     * 支付回调url
     */
    private String url;
    public String getRefresh_Token() {
        return Refresh_Token;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public void setRefresh_Token(String refresh_Token) {
        Refresh_Token = refresh_Token;
    }

    public String getModuleId() {
        return ModuleId;
    }

    public void setModuleId(String moduleId) {
        ModuleId = moduleId;
    }

    public String getKEY() {
        return KEY;
    }

    public void setKEY(String KEY) {
        this.KEY = KEY;
    }

    public String getSID() {
        return SID;
    }

    public void setSID(String SID) {
        this.SID = SID;
    }

    public String getAID() {
        return AID;
    }

    public void setAID(String AID) {
        this.AID = AID;
    }

    public String getICODE() {
        return ICODE;
    }

    public void setICODE(String ICODE) {
        this.ICODE = ICODE;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getMode() {
        return Mode;
    }

    public void setMode(String mode) {
        Mode = mode;
    }

    public String getFormat() {
        return Format;
    }

    public void setFormat(String format) {
        Format = format;
    }

    public BaseReq() {
    }

    public BaseReq(String SID, String AID, String ICODE, String UUID, String token, String mode, String format, String moduleId, String KEY, String refresh_Token, String url) {
        this.SID = SID;
        this.AID = AID;
        this.ICODE = ICODE;
        this.UUID = UUID;
        Token = token;
        Mode = mode;
        Format = format;
        ModuleId = moduleId;
        this.KEY = KEY;
        Refresh_Token = refresh_Token;
        this.url = url;
    }

    @Override
    public String toString() {
        return "BaseReq{" +
                "SID='" + SID + '\'' +
                ", AID='" + AID + '\'' +
                ", ICODE='" + ICODE + '\'' +
                ", UUID='" + UUID + '\'' +
                ", Token='" + Token + '\'' +
                ", Mode='" + Mode + '\'' +
                ", Format='" + Format + '\'' +
                ", ModuleId='" + ModuleId + '\'' +
                ", KEY='" + KEY + '\'' +
                ", Refresh_Token='" + Refresh_Token + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
