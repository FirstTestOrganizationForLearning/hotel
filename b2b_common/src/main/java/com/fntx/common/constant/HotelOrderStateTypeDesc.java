package com.fntx.common.constant;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @Author: 渠猛
 * @Date: 2019/7/23 0023 下午 2:36
 * @Description:
 */
//携程订单状态：Uncommitted-订单未提交；Process-确认中；Confirm-已确认；Cancel-已取消；Success-成交；
public enum HotelOrderStateTypeDesc {
    UNCOMMITTED(0, "未提交"),
    PROCESS(1, "确认中"),
    CONFIRM(2, "已确认"),
    CANCEL(3, "已取消"),
    SUCCESS(4, "已完成");
    //Payment     // 已支付


    private Integer code;
    private String name;

    HotelOrderStateTypeDesc(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    // 根据code返回枚举类型,主要在switch中使用
    public static HotelOrderStateTypeDesc getByCode(Integer value) {
        for (HotelOrderStateTypeDesc hotelOrderState : values()) {
            if (hotelOrderState.getCode().equals(value)) {
                return hotelOrderState;
            }
        }
        return null;
    }
}