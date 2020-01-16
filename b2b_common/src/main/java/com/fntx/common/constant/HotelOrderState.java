package com.fntx.common.constant;

//携程订单状态：0:Uncommitted-订单未提交；1:Process-确认中；2:Confirm-已确认；3:Cancel-已取消；4:Success-成交；
public enum HotelOrderState {
    Uncommitted(0, "Uncommitted"),
    Process(1, "Process"),
    Confirm(2, "Confirm"),
    Cancel(3, "Cancel"),
    Success(4, "Success");
    //Payment     // 已支付


    private Integer code;
    private String name;

    HotelOrderState(Integer code, String name) {
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
    public static HotelOrderState getByCode(String value) {
        for (HotelOrderState hotelOrderState : values()) {
            if (hotelOrderState.getName().equals(value)) {
                return hotelOrderState;
            }
        }
        return null;
    }
}