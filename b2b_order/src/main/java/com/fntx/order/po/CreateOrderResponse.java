package com.fntx.order.po;

import java.util.List;

/**
 *
 * @copyright (C), 2019, 弗恩天下
 * @fileName: 创建订单响应
 * @author: 胡庆康
 * @date: 2019/7/11 12:00
 */
public class CreateOrderResponse {
    public ResponseStatus ResponseStatus;
    public List<GeneralEntity.Warnings> Warnings;
    public GeneralEntity.ResponseStatus.HotelReservations HotelReservations;
    public List<GeneralEntity.Errors> Errors;
    public String TimeStamp;
    public String Version;
    public String PrimaryLangID;
}
