package com.fntx.common.domain.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: MinPriceInfoEntityBean
 * @Author: 王俊文
 * @Date: 19-7-18 下午5:05
 * @Description: TODO
 */
public class MinPriceInfoEntityBean implements Serializable
{
    private static final long serialVersionUID = 1L;
    /**
     * MinPrice_AfterTax : {"MinPrice_CashBack":{"MinPrice":55,"MinPrice_OriginalCurrency":55,"OriginalCurrency":"RMB","MinPrice_RoomID":146532285}}
     */

    @JSONField(name="MinPrice_AfterTax")
    private MinPriceAfterTaxBean MinPrice_AfterTax;

    public MinPriceAfterTaxBean getMinPrice_AfterTax() {
        return MinPrice_AfterTax;
    }

    public void setMinPrice_AfterTax(MinPriceAfterTaxBean MinPrice_AfterTax) {
        this.MinPrice_AfterTax = MinPrice_AfterTax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MinPriceInfoEntityBean)) return false;
        MinPriceInfoEntityBean that = (MinPriceInfoEntityBean) o;
        return MinPrice_AfterTax.equals(that.MinPrice_AfterTax);
    }

    @Override
    public int hashCode() {
        return Objects.hash(MinPrice_AfterTax);
    }

    @Override
    public String toString() {
        return "MinPriceInfoEntityBean{" +
                "MinPrice_AfterTax=" + MinPrice_AfterTax +
                '}';
    }
}
