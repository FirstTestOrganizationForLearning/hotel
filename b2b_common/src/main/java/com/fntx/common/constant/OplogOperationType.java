package com.fntx.common.constant;

//1：创建 2：提交 3：确认 4：取消
public enum OplogOperationType {
    Create(1, "创建"),
    Submit(2, "提交"),
    Confirm(3, "确认"),
    Cancel(4, "取消"),
    Success(5,"完成");

    private Integer code;
    private String name;

    OplogOperationType(Integer code, String name) {
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
    public static OplogOperationType getByCode(String value) {
        for (OplogOperationType hotelOrderState : values()) {
            if (hotelOrderState.getName().equals(value)) {
                return hotelOrderState;
            }
        }
        return null;
    }
}