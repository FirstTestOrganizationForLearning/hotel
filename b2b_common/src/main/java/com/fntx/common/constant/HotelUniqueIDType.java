package com.fntx.common.constant;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @Author: 渠猛
 * @Date: 2019/7/8  上午 11:23
 * @Description: 弗恩酒店 ID 类型
 */
public enum HotelUniqueIDType {


    /*
     * Type为1，ID表示分销商自己的用户id。为简化工作，
     * 分销商可以仅使用一个id来标识分销商自己的身份。该id需通过弗恩接口生成。
     */
    DISTRIBUTOR_USER_ID("1", "分销商用户id"),

    /*
     *Type为28，ID表示分销商的AllianceID；
     */
    ALLIANCE_ID("28", "AllianceID"),

    /**
     * Type为501，ID表示本次查询的弗恩订单号。
     */

    ORDER_NO("501", "弗恩订单号"),

    /**
     * Type为503，ID表示分销商的SiteID
     */
    SITE_ID("503", "SiteID");

    private String code;
    private String name;

    HotelUniqueIDType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    // 根据code返回枚举类型,主要在switch中使用
    public static HotelUniqueIDType getByCode(String value) {
        for (HotelUniqueIDType code : values()) {
            if (code.getCode().equals(value)) {
                return code;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "OperateTypeNo{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
