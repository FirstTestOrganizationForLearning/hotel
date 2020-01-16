package com.fntx.common.domain.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: MinPriceCashBackBean
 * @Author: 王俊文
 * @Date: 19-7-18 下午5:06
 * @Description: TODO
 */
public class MinPriceCashBackBean implements Serializable
{
    private static final long serialVersionUID = 1L;
    /**
     * MinPrice : 55
     * MinPrice_OriginalCurrency : 55
     * OriginalCurrency : RMB
     * MinPrice_RoomID : 146532285
     */

    @JSONField(name="MinPrice")
    private Double MinPrice;
    @JSONField(name="MinPrice_OriginalCurrency")
    private Double MinPrice_OriginalCurrency;
    @JSONField(name="OriginalCurrency")
    private String OriginalCurrency;
    @JSONField(name="MinPrice_RoomID")
    private Long MinPrice_RoomID;

    public Double getMinPrice() {
        return MinPrice;
    }

    public void setMinPrice(Double MinPrice) {
        this.MinPrice = MinPrice;
    }

    public Double getMinPrice_OriginalCurrency() {
        return MinPrice_OriginalCurrency;
    }

    public void setMinPrice_OriginalCurrency(Double MinPrice_OriginalCurrency) {
        this.MinPrice_OriginalCurrency = MinPrice_OriginalCurrency;
    }

    public String getOriginalCurrency() {
        return OriginalCurrency;
    }

    public void setOriginalCurrency(String OriginalCurrency) {
        this.OriginalCurrency = OriginalCurrency;
    }

    public Long getMinPrice_RoomID() {
        return MinPrice_RoomID;
    }

    public void setMinPrice_RoomID(Long MinPrice_RoomID) {
        this.MinPrice_RoomID = MinPrice_RoomID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (!(o instanceof MinPriceCashBackBean)) {return false;}
        MinPriceCashBackBean that = (MinPriceCashBackBean) o;
        return MinPrice.equals(that.MinPrice) &&
                MinPrice_OriginalCurrency.equals(that.MinPrice_OriginalCurrency) &&
                OriginalCurrency.equals(that.OriginalCurrency) &&
                MinPrice_RoomID.equals(that.MinPrice_RoomID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(MinPrice, MinPrice_OriginalCurrency, OriginalCurrency, MinPrice_RoomID);
    }

    @Override
    public String toString() {
        return "MinPriceCashBackBean{" +
                "MinPrice=" + MinPrice +
                ", MinPrice_OriginalCurrency=" + MinPrice_OriginalCurrency +
                ", OriginalCurrency='" + OriginalCurrency + '\'' +
                ", MinPrice_RoomID=" + MinPrice_RoomID +
                '}';
    }
}
