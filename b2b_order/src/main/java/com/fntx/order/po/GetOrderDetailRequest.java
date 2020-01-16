package com.fntx.order.po;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @FileName: 获取订单详情请求
 * @Author: 胡庆康
 * @Date: 2019/7/12 15:42
 */
@Data
public class GetOrderDetailRequest {


    public List<Unique> UniqueID;
    @Data
    public static class Unique{
        private String Type;
        private String ID;
    }
    public String TimeStamp;

    public String Version;

    public String PrimaryLangID;
}
