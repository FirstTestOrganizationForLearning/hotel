package com.fntx.common.constant;

/**
 * Created by 王俊文 on 19-6-6.
 * <p>
 * 业务类型
 */

public enum OperateTypeNo {
    //支付订单
    PAYMENT_OF_ORDERS("0000600001", "支付订单"),

    //充值
    RECHARGE("0000600002", "充值"),

    //退票
    TO_REFUND_A_TICKET("0000600003", "退票"),

    //退款
    REFUND("0000600004", "退款"),

    //退票反充
    REFUND_ANTI_CHARGING("0000600005", "退票反充"),

    //授信额度调整
    CREDIT_LINE_ADJUSTMENT("0000600006", "授信额度调整"),

    //保险退款
    INSURANCE_REFUND("0000600007", "保险退款"),

    //单独购买保险
    BUY_INSURANCE_SEPARATELY("0000600008", "单独购买保险"),

    //改签
    TICKET_CHANGES("0000600009", "改签"),

    //调账
    ACCOUNT_REGULATION("0000600010", "调账");


    private String code;
    private String name;

    OperateTypeNo(String code, String name) {
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
    public static OperateTypeNo getByCode(String value) {
        for (OperateTypeNo code : values()) {
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
