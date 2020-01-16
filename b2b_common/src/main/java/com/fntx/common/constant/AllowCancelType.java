package com.fntx.common.constant;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @Author: 渠猛
 * @Date: 2019/7/23 0023 下午 2:36
 * @Description: 订单是否可以取消
 */
public enum AllowCancelType {
    CAN("T", "可取消"),
    NO_CAN("F", "不可取消");

    //Payment     // 已支付


    private String code;
    private String name;

    AllowCancelType(String code, String name) {
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
    public static AllowCancelType getByCode(Integer value) {
        for (AllowCancelType hotelOrderState : values()) {
            if (hotelOrderState.getCode().equals(value)) {
                return hotelOrderState;
            }
        }
        return null;
    }
}