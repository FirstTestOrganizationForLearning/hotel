package com.fntx.common.domain.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: MinPriceAfterTaxBean
 * @Author: 王俊文
 * @Date: 19-7-18 下午5:06
 * @Description: TODO
 */
public class MinPriceAfterTaxBean implements Serializable
{
    private static final long serialVersionUID = 1L;
    /**
     * MinPrice_CashBack : {"MinPrice":55,"MinPrice_OriginalCurrency":55,"OriginalCurrency":"RMB","MinPrice_RoomID":146532285}
     */

    @JSONField(name="MinPrice_CashBack")
    private MinPriceCashBackBean MinPrice_CashBack;

    public MinPriceCashBackBean getMinPrice_CashBack() {
        return MinPrice_CashBack;
    }

    public void setMinPrice_CashBack(MinPriceCashBackBean MinPrice_CashBack) {
        this.MinPrice_CashBack = MinPrice_CashBack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MinPriceAfterTaxBean)) return false;
        MinPriceAfterTaxBean that = (MinPriceAfterTaxBean) o;
        return Objects.equals(MinPrice_CashBack, that.MinPrice_CashBack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(MinPrice_CashBack);
    }

    @Override
    public String toString() {
        return "MinPriceAfterTaxBean{" +
                "MinPrice_CashBack=" + MinPrice_CashBack +
                '}';
    }
}
