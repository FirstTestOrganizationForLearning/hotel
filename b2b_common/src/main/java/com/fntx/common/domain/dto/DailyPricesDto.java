package com.fntx.common.domain.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: DailyPricesDto
 * @Author: 王俊文
 * @Date: 19-7-23 下午3:27
 * @Description: TODO
 */
public class DailyPricesDto  implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * MealInfo : {"NumberOfBreakfast":0,"NumberOfLunch":0,"NumberOfDinner":0,"IsOptionalMeal":false}
     * Prices : [{"Type":"DisplayCurrency","ExclusiveAmount":1564,"InclusiveAmount":1564,"Currency":"CNY"},{"Type":"OriginalCurrency","ExclusiveAmount":1563.33,"InclusiveAmount":1563.33,"Currency":"CNY"}]
     * Cashbacks : [{"Type":"OriginalCurrency","Amount":1564,"Currency":"CNY"},{"Type":"DisplayCurrency","Amount":1564,"Currency":"CNY"}]
     * DiscountDetailInfos : []
     * EffectiveDate : 2019-08-01T00:00:00.0000000
     * GuaranteeCode : 2
     */

    @JSONField(name = "MealInfo")
    private MealInfoBeanDto MealInfo;
    @JSONField(name = "EffectiveDate")
    private String EffectiveDate;
    @JSONField(name = "GuaranteeCode")
    private String GuaranteeCode;
    @JSONField(name = "Prices")
    private List<PricesBeanXDto> Prices;
    @JSONField(name = "Cashbacks")
    private List<CashbacksBeanDto> Cashbacks;
    @JSONField(name = "DiscountDetailInfos")
    private List<Object> DiscountDetailInfos;

    public MealInfoBeanDto getMealInfo() {
        return MealInfo;
    }

    public void setMealInfo(MealInfoBeanDto mealInfo) {
        MealInfo = mealInfo;
    }

    public String getEffectiveDate() {
        return EffectiveDate;
    }

    public void setEffectiveDate(String EffectiveDate) {
        this.EffectiveDate = EffectiveDate;
    }

    public String getGuaranteeCode() {
        return GuaranteeCode;
    }

    public void setGuaranteeCode(String GuaranteeCode) {
        this.GuaranteeCode = GuaranteeCode;
    }

    public List<PricesBeanXDto> getPrices() {
        return Prices;
    }

    public void setPrices(List<PricesBeanXDto> prices) {
        Prices = prices;
    }

    public List<CashbacksBeanDto> getCashbacks() {
        return Cashbacks;
    }

    public void setCashbacks(List<CashbacksBeanDto> cashbacks) {
        Cashbacks = cashbacks;
    }

    public List<Object> getDiscountDetailInfos() {
        return DiscountDetailInfos;
    }

    public void setDiscountDetailInfos(List<Object> discountDetailInfos) {
        DiscountDetailInfos = discountDetailInfos;
    }

    public static class MealInfoBeanDto {
        /**
         * NumberOfBreakfast : 0
         * NumberOfLunch : 0
         * NumberOfDinner : 0
         * IsOptionalMeal : false
         */

        @JSONField(name = "NumberOfBreakfast")
        private int NumberOfBreakfast;
        @JSONField(name = "NumberOfLunch")
        private int NumberOfLunch;
        @JSONField(name = "NumberOfDinner")
        private int NumberOfDinner;
        @JSONField(name = "IsOptionalMeal")
        private boolean IsOptionalMeal;

        public int getNumberOfBreakfast() {
            return NumberOfBreakfast;
        }

        public void setNumberOfBreakfast(int NumberOfBreakfast) {
            this.NumberOfBreakfast = NumberOfBreakfast;
        }

        public int getNumberOfLunch() {
            return NumberOfLunch;
        }

        public void setNumberOfLunch(int NumberOfLunch) {
            this.NumberOfLunch = NumberOfLunch;
        }

        public int getNumberOfDinner() {
            return NumberOfDinner;
        }

        public void setNumberOfDinner(int NumberOfDinner) {
            this.NumberOfDinner = NumberOfDinner;
        }

        public boolean isIsOptionalMeal() {
            return IsOptionalMeal;
        }

        public void setIsOptionalMeal(boolean IsOptionalMeal) {
            this.IsOptionalMeal = IsOptionalMeal;
        }
    }

    public static class PricesBeanXDto implements Serializable{
        private static final long serialVersionUID = 1L;
        /**
         * Type : DisplayCurrency
         * ExclusiveAmount : 1564
         * InclusiveAmount : 1564
         * Currency : CNY
         */

        @JSONField(name = "Type")
        private String Type;
        @JSONField(name = "ExclusiveAmount")
        private int ExclusiveAmount;
        @JSONField(name = "InclusiveAmount")
        private int InclusiveAmount;
        @JSONField(name = "Currency")
        private String Currency;

        public String getType() {
            return Type;
        }

        public void setType(String Type) {
            this.Type = Type;
        }

        public int getExclusiveAmount() {
            return ExclusiveAmount;
        }

        public void setExclusiveAmount(int ExclusiveAmount) {
            this.ExclusiveAmount = ExclusiveAmount;
        }

        public int getInclusiveAmount() {
            return InclusiveAmount;
        }

        public void setInclusiveAmount(int InclusiveAmount) {
            this.InclusiveAmount = InclusiveAmount;
        }

        public String getCurrency() {
            return Currency;
        }

        public void setCurrency(String Currency) {
            this.Currency = Currency;
        }
    }

    public static class CashbacksBeanDto implements Serializable{
        private static final long serialVersionUID = 1L;
        /**
         * Type : OriginalCurrency
         * Amount : 1564
         * Currency : CNY
         */

        @JSONField(name = "Type")
        private String Type;
        @JSONField(name = "Amount")
        private int Amount;
        @JSONField(name = "Currency")
        private String Currency;

        public String getType() {
            return Type;
        }

        public void setType(String Type) {
            this.Type = Type;
        }

        public int getAmount() {
            return Amount;
        }

        public void setAmount(int Amount) {
            this.Amount = Amount;
        }

        public String getCurrency() {
            return Currency;
        }

        public void setCurrency(String Currency) {
            this.Currency = Currency;
        }
    }
}
