package com.fntx.order.po;

import java.util.List;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @FileName: 提交订单请求
 * @Author: 胡庆康
 * @Date: 2019/7/12 15:34
 */
public class SubmitOrderRequest {

    public ReservationPayment ReservationPayment;

    public String TimeStamp;

    public String Version;

    public String PrimaryLangID;

    public static class ReservationPayment {

        public ReservationID ReservationID;

        public List<PaymentDetailItem> PaymentDetail;
    }

    public static class ReservationID {

        public String Type;

        public String ID;
    }

    public static class PaymentDetailItem {

        public ChannelAccount ChannelAccount;

        public PaymentAmount PaymentAmount;

        public String GuaranteeIndicator;

        public String GuaranteeTypeCode;

        public String PaymentType;
    }

    public static class ChannelAccount {

        public String ChannelAccountIndicator;
    }

    public static class PaymentAmount {

        public Double Amount;

        public String CurrencyCode;
    }
}
