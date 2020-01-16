package com.fntx.order.po;

import java.util.List;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @FileName: 提交订单响应
 * @Author: 胡庆康
 * @Date: 2019/7/12 15:35
 */
public class SubmitOrderResponse {

    public ResponseStatus ResponseStatus;

    public Success Success;

    public List<GeneralEntity.Warnings> Warnings;

    public ReservationPayment2 ReservationPayment;

    public List<GeneralEntity.Errors> Errors;

    public String TimeStamp;

    public String Version;

    public String PrimaryLangID;

    public static class Success {
    }

    public static class ReservationPayment2 {

        public SubmitOrderRequest.ReservationID ReservationID;
    }

}
