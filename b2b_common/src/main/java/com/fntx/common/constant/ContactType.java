package com.fntx.common.constant;

public enum ContactType {
    Non("Non", "通过API对接"),
    Process("Tel", "电话联系"),
    Confirm("Sms", "短信联系");

    private String code;
    private String name;

    ContactType(String code, String name) {
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
    public static ContactType getByCode(String value) {
        for (ContactType hotelOrderState : values()) {
            if (hotelOrderState.getName().equals(value)) {
                return hotelOrderState;
            }
        }
        return null;
    }
}