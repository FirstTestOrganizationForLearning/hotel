package com.fntx.common.domain.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: HotelStatusEntityBean
 * @Author: 王俊文
 * @Date: 19-7-18 下午4:58
 * @Description: TODO
 */
public class HotelStatusEntityBean implements Serializable
{
    private static final long serialVersionUID = 1L;
    /**
     * Hotel : 4732165
     * MinPrice : 55
     * MinPrice_MyCurrency : 55
     * Currency_minPrice : RMB
     * MinPriceRoom : 146532285
     * MinPriceInfoEntity : {"MinPrice_AfterTax":{"MinPrice_CashBack":{"MinPrice":55,"MinPrice_OriginalCurrency":55,"OriginalCurrency":"RMB","MinPrice_RoomID":146532285}}}
     */

    @JSONField(name="Hotel")
    private Long Hotel;
    @JSONField(name="MinPrice")
    private Double MinPrice;
    @JSONField(name="MinPrice_MyCurrency")
    private Double MinPrice_MyCurrency;
    @JSONField(name="Currency_minPrice")
    private String Currency_minPrice;
    @JSONField(name="MinPriceRoom")
    private Long MinPriceRoom;
    @JSONField(name="MinPriceInfoEntity")
    private MinPriceInfoEntityBean MinPriceInfoEntity;

    public Long getHotel() {
        return Hotel;
    }

    public void setHotel(Long Hotel) {
        this.Hotel = Hotel;
    }

    public Double getMinPrice() {
        return MinPrice;
    }

    public void setMinPrice(Double MinPrice) {
        this.MinPrice = MinPrice;
    }

    public Double getMinPrice_MyCurrency() {
        return MinPrice_MyCurrency;
    }

    public void setMinPrice_MyCurrency(Double MinPrice_MyCurrency) {
        this.MinPrice_MyCurrency = MinPrice_MyCurrency;
    }

    public String getCurrency_minPrice() {
        return Currency_minPrice;
    }

    public void setCurrency_minPrice(String Currency_minPrice) {
        this.Currency_minPrice = Currency_minPrice;
    }

    public Long getMinPriceRoom() {
        return MinPriceRoom;
    }

    public void setMinPriceRoom(Long MinPriceRoom) {
        this.MinPriceRoom = MinPriceRoom;
    }

    public MinPriceInfoEntityBean getMinPriceInfoEntity() {
        return MinPriceInfoEntity;
    }

    public void setMinPriceInfoEntity(MinPriceInfoEntityBean MinPriceInfoEntity) {
        this.MinPriceInfoEntity = MinPriceInfoEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HotelStatusEntityBean)) return false;
        HotelStatusEntityBean that = (HotelStatusEntityBean) o;
        return Hotel.equals(that.Hotel) &&
                MinPrice.equals(that.MinPrice) &&
                MinPrice_MyCurrency.equals(that.MinPrice_MyCurrency) &&
                Currency_minPrice.equals(that.Currency_minPrice) &&
                MinPriceRoom.equals(that.MinPriceRoom) &&
                MinPriceInfoEntity.equals(that.MinPriceInfoEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Hotel, MinPrice, MinPrice_MyCurrency, Currency_minPrice, MinPriceRoom, MinPriceInfoEntity);
    }

    @Override
    public String toString() {
        return "HotelStatusEntityBean{" +
                "Hotel=" + Hotel +
                ", MinPrice=" + MinPrice +
                ", MinPrice_MyCurrency=" + MinPrice_MyCurrency +
                ", Currency_minPrice='" + Currency_minPrice + '\'' +
                ", MinPriceRoom=" + MinPriceRoom +
                ", MinPriceInfoEntity=" + MinPriceInfoEntity +
                '}';
    }
}
