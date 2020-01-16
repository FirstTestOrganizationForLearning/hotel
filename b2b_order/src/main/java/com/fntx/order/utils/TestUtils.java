package com.fntx.order.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fntx.common.domain.FinanceResponse;
import com.fntx.order.po.PaymentCallbackResponse;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @FileName
 * @Author 胡庆康
 * @Date 2019/7/31 10:59
 */
public class TestUtils {
    /**
     * 测试-返回一个获取支付回调结果
     *
     * @author 胡庆康
     * @date 2019/7/31 11:18
     * @returns
     */
    public static PaymentCallbackResponse getCallback(Double amount) {
        PaymentCallbackResponse paymentCallbackResponse = new PaymentCallbackResponse();
        paymentCallbackResponse.pay_amount = String.valueOf(amount);
        paymentCallbackResponse.is_success = true;
        paymentCallbackResponse.pay_currency = "CNY";
        paymentCallbackResponse.err_code = 0;
        paymentCallbackResponse.remark = "我是一个备注";
        paymentCallbackResponse.err_msg = "我没有返回错误信息";
        return paymentCallbackResponse;
    }

    /**
     * 测试-获取余额
     *
     * @param
     * @author 胡庆康
     * @date 2019/7/31 11:25
     * @returns
     */
    public static FinanceResponse getBalance() {
        FinanceResponse financeResponse = new FinanceResponse();
        FinanceResponse.Data data = new FinanceResponse.Data();
        data.setAccountbalance(1000);
        data.setModuleId("0000500002");
        data.setPaytype("0000700001");
        data.setCid("test");
        financeResponse.setMsg("SUCCESS");
        financeResponse.setStatus(100);
        financeResponse.setData(data);
        return financeResponse;
    }

    /**
     * 测试-支付
     *
     * @author 胡庆康
     * @date 2019/7/31 11:26
     * @returns
     */
    public static FinanceResponse pay() {
        FinanceResponse financeResponse = new FinanceResponse();
        FinanceResponse.Data data = new FinanceResponse.Data();
        data.setTradedetail("123456789");
        data.setAccountbalance(1000);
        data.setModuleId("0000500002");
        data.setPaytype("0000700001");
        data.setCid("test");
        financeResponse.setMsg("SUCCESS");
        financeResponse.setStatus(100);
        financeResponse.setData(data);
        return financeResponse;
    }

}
