package com.fntx.order.po;

/**
 * 支付结果回调实体类
 *
 * @Copyright (C), 2019, 弗恩天下
 * @FileName:
 * @Author: 胡庆康
 * @Date: 2019/7/18 18:43
 */
public class PaymentCallbackResponse {

    public boolean is_success;

    public String pay_amount;

    public String pay_currency;
    /**
     * 备注信息
     */
    public String remark;

    public int err_code;
    /**
     * 返回错误信息
     */
    public String err_msg;
}
