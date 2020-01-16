package com.fntx.order.po;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * @copyright (C), 2019, 弗恩天下
 * @fileName: 创建订单请求
 * @author: 胡庆康
 * @date: 2019/7/11 12:00
 */
@NoArgsConstructor
@Data
public class CreateOrderRequest {
    public List<GeneralEntity.ResponseStatus.UniqueIDItem> UniqueID;
    public GeneralEntity.ResponseStatus.HotelReservations HotelReservations;

    public String TimeStamp;

    public String Version;

    public String PrimaryLangID;

}
