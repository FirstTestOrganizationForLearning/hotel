package com.fntx.common.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @FileName
 * @Author 胡庆康
 * @Date 2019/7/25 17:04
 */
@NoArgsConstructor
@Data
public class FinanceResponse {

    /**
     * data : {"accountbalance":200}
     * msg : SUCCESS
     * status : 100
     */

    private Data data;
    private String msg;
    private int status;

    @NoArgsConstructor
    @lombok.Data
    public static class Data {
        /**
         * 余额
         */
        private int accountbalance;
        /**
         * 流水单号
         */
        private String tradedetail;
        /**
         * 模块id
         */
        private String moduleId;
        /**
         * 支付方式
         */
        private String paytype;
        /**
         * 渠道id
         */
        private String cid;
    }
}
