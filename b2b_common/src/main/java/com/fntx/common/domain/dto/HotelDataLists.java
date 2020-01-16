package com.fntx.common.domain.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: HotelDataLists
 * @Author: 王俊文
 * @Date: 19-7-17 下午6:03
 * @Description: 城市入离起价实体
 */
public class HotelDataLists implements Serializable
{
    private static final long serialVersionUID = 1L;


    /**
     * HotelStatusEntity : {"Hotel":4732165,"MinPrice":55,"MinPrice_MyCurrency":55,"Currency_minPrice":"RMB","MinPriceRoom":146532285,"MinPriceInfoEntity":{"MinPrice_AfterTax":{"MinPrice_CashBack":{"MinPrice":55,"MinPrice_OriginalCurrency":55,"OriginalCurrency":"RMB","MinPrice_RoomID":146532285}}}}
     */

    @JSONField(name="HotelStatusEntity")
    private HotelStatusEntityBean HotelStatusEntity;

    public HotelStatusEntityBean getHotelStatusEntity() {
        return HotelStatusEntity;
    }

    public void setHotelStatusEntity(HotelStatusEntityBean HotelStatusEntity) {
        this.HotelStatusEntity = HotelStatusEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (!(o instanceof HotelDataLists)) {return false;}
        HotelDataLists that = (HotelDataLists) o;
        return HotelStatusEntity.equals(that.HotelStatusEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(HotelStatusEntity);
    }

    @Override
    public String toString() {
        return "HotelDataLists{" +
                "HotelStatusEntity=" + HotelStatusEntity +
                '}';
    }
}
