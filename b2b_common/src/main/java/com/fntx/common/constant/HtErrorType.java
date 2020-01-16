package com.fntx.common.constant;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @Author: 渠猛
 * @Date: 2019/7/10 0010 下午 2:30
 * @Description:
 * 酒店redis 过期时间
 */
  
public enum HtErrorType {
    //参数不可用
    INVALID_PARAMETERS("1001", "INVALID_PARAMETERS"),
    //refreshToken不可用
    INVALID_REFRESHTOKEN("1005", "INVALID_REFRESHTOKEN"),
    // "身份验证失败"
    AUTHENTICATION_FAILURE("100006", "身份验证失败"),
    // TODO 自定义 取消订单失败
    CANCEL_ORDER_FAILURE("100008", "取消订单失败"),
    //获取订单ID失败
    GET_ORDERID_FAILURE("100009", "获取订单ID失败");

    private String code;
    private String name;

    HtErrorType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }


    public static HtErrorType getByCode(Long value) {
        for (HtErrorType code : values()) {
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
