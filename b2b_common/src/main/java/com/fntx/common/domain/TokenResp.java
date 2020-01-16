package com.fntx.common.domain;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @Author: 渠猛
 * @Date: 2019/7/10 0010 上午 10:16
 * @Description:
 */
public class TokenResp {

    /**
     * eg:
     * AID : 1
     * SID : 50
     * Access_Token : 55f747d434b64a9888cb7492e7672484
     * Expires_In : 600
     * Refresh_Token : d434cf51229e4ce7ada9e3717d648076
     */

    private String AID;

    private String SID;
    //授权Token
    private String Access_Token;

    private Long Expires_In;
   //刷新Token
    private String Refresh_Token;

    public String getAID() {
        return AID;
    }

    public void setAID(String AID) {
        this.AID = AID;
    }

    public String getSID() {
        return SID;
    }

    public void setSID(String SID) {
        this.SID = SID;
    }

    public String getAccess_Token() {
        return Access_Token;
    }

    public void setAccess_Token(String Access_Token) {
        this.Access_Token = Access_Token;
    }

    public Long getExpires_In() {
        return Expires_In;
    }

    public void setExpires_In(Long Expires_In) {
        this.Expires_In = Expires_In;
    }

    public String getRefresh_Token() {
        return Refresh_Token;
    }

    public void setRefresh_Token(String Refresh_Token) {
        this.Refresh_Token = Refresh_Token;
    }

    @Override
    public String toString() {
        return "TokenResp{" +
                "AID='" + AID + '\'' +
                ", SID='" + SID + '\'' +
                ", Access_Token='" + Access_Token + '\'' +
                ", Expires_In=" + Expires_In +
                ", Refresh_Token='" + Refresh_Token + '\'' +
                '}';
    }
}
