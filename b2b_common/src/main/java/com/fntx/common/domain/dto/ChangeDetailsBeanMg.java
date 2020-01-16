package com.fntx.common.domain.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: ChangeDetailsBeanMg
 * @Author: 王俊文
 * @Date: 19-8-6 下午4:50
 * @Description: TODO
 */
public class ChangeDetailsBeanMg implements Serializable
{
    private static final long serialVersionUID = 4619877447701522921L;
    /**
         * Name : IsOpen
         * NewValue : F
         */
        /**
            IsOpen 房型上下线，T上线F下线
            address 酒店地址
            hotelname、hotelnameintl、hotelcnname 酒店名称
            hotelstar 挂牌星级
            starlicence 是否挂牌
            ctripstar 携程星级
            gdlat、gdlon、glat、glon、lat、lon 经纬度
            telephone 联系电话
            bookable 酒店是否可预订
            bookablechangereason 不可订原因
            roomname
            addbed 是否加床
            addbedfee 加床费
            haskingbed、hassinglebed、hastwinbed 床型
            kingbedwidth、singlebedwidth、twinbedwidth 床宽
         */
        @JSONField(name = "Name")
        private String Name;
        @JSONField(name = "NewValue")
        private String NewValue;

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getNewValue() {
            return NewValue;
        }

        public void setNewValue(String NewValue) {
            this.NewValue = NewValue;
        }
    }
