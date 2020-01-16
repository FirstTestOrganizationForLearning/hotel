package com.fntx.common.domain.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fntx.common.utils.StringToJsonSerializer;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: PriceInfosBeanDto
 * @Author: 王俊文
 * @Date: 19-7-22 下午5:49
 * @Description: TODO
 */
public class PriceInfosBeanDto implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Prices : [{"Type":"DisplayCurrency","ExclusiveAmount":1563,"InclusiveAmount":1563,"Currency":"CNY"},{"Type":"OriginalCurrency","ExclusiveAmount":1563.33,"InclusiveAmount":1563.33,"Currency":"CNY"}]
     * DailyPrices : [{"MealInfo":{"NumberOfBreakfast":0,"NumberOfLunch":0,"NumberOfDinner":0,"IsOptionalMeal":false},"Prices":[{"Type":"DisplayCurrency","ExclusiveAmount":1564,"InclusiveAmount":1564,"Currency":"CNY"},{"Type":"OriginalCurrency","ExclusiveAmount":1563.33,"InclusiveAmount":1563.33,"Currency":"CNY"}],"Cashbacks":[{"Type":"OriginalCurrency","Amount":1564,"Currency":"CNY"},{"Type":"DisplayCurrency","Amount":1564,"Currency":"CNY"}],"DiscountDetailInfos":[],"EffectiveDate":"2019-08-01T00:00:00.0000000","GuaranteeCode":"2"}]
     * Taxes : []
     * Fees : [{"Amount":[{"Type":"OriginalCurrency","Amount":24.09,"Currency":"CNY"},{"Type":"DisplayCurrency","Amount":25,"Currency":"CNY"}],"Name":"城市税","Type":"ExcludedFromTotalPrice","ID":"3"},{"Amount":[{"Type":"OriginalCurrency","Amount":236.96,"Currency":"CNY"},{"Type":"DisplayCurrency","Amount":237,"Currency":"CNY"}],"Name":"度假村费","Type":"ExcludedFromTotalPrice","ID":"8"},{"Amount":[{"Type":"OriginalCurrency","Amount":200.96,"Currency":"CNY"},{"Type":"DisplayCurrency","Amount":201,"Currency":"CNY"}],"Name":"增值税","Type":"IncludedInTotalPrice","ID":"4"}]
     * PayType : PP
     * RatePlanCategory : 501
     * IsCanReserve : true
     * IsGuarantee : true
     * IsInstantConfirm : true
     * RemainingRooms : 8
     * BookingCode : 490529948
     * RatePlanID : 872831302
     * IsPromotion : F
     */

    @JSONField(name = "PayType")
    private String PayType;
    @JSONField(name = "RatePlanCategory")
    private String RatePlanCategory;
    @JSONField(name = "IsCanReserve")
    private boolean IsCanReserve;
    @JSONField(name = "IsGuarantee")
    private boolean IsGuarantee;
    @JSONField(name = "IsInstantConfirm")
    private boolean IsInstantConfirm;
    @JSONField(name = "RemainingRooms")
    private String RemainingRooms;
    @JSONField(name = "BookingCode")
    private String BookingCode;
    @JSONField(name = "RatePlanID")
    private String RatePlanID;
    @JSONField(name = "IsPromotion")
    private String IsPromotion;
    @JSONField(serializeUsing=StringToJsonSerializer.class, name = "Prices")
    private Object Prices;
    @JSONField(name = "DailyPrices")
    private List<DailyPricesDto> DailyPrices;
    @JSONField(serializeUsing=StringToJsonSerializer.class, name = "Taxes")
    private Object Taxes;
    @JSONField(serializeUsing= StringToJsonSerializer.class, name = "Fees")
    private Object Fees;

    public String getPayType() {
        return PayType;
    }

    public void setPayType(String PayType) {
        this.PayType = PayType;
    }

    public String getRatePlanCategory() {
        return RatePlanCategory;
    }

    public void setRatePlanCategory(String RatePlanCategory) {
        this.RatePlanCategory = RatePlanCategory;
    }

    public boolean isIsCanReserve() {
        return IsCanReserve;
    }

    public void setIsCanReserve(boolean IsCanReserve) {
        this.IsCanReserve = IsCanReserve;
    }

    public boolean isIsGuarantee() {
        return IsGuarantee;
    }

    public void setIsGuarantee(boolean IsGuarantee) {
        this.IsGuarantee = IsGuarantee;
    }

    public boolean isIsInstantConfirm() {
        return IsInstantConfirm;
    }

    public void setIsInstantConfirm(boolean IsInstantConfirm) {
        this.IsInstantConfirm = IsInstantConfirm;
    }

    public String getRemainingRooms() {
        return RemainingRooms;
    }

    public void setRemainingRooms(String RemainingRooms) {
        this.RemainingRooms = RemainingRooms;
    }

    public String getBookingCode() {
        return BookingCode;
    }

    public void setBookingCode(String BookingCode) {
        this.BookingCode = BookingCode;
    }

    public String getRatePlanID() {
        return RatePlanID;
    }

    public void setRatePlanID(String RatePlanID) {
        this.RatePlanID = RatePlanID;
    }

    public String getIsPromotion() {
        return IsPromotion;
    }

    public void setIsPromotion(String IsPromotion) {
        this.IsPromotion = IsPromotion;
    }

    public boolean isCanReserve() {
        return IsCanReserve;
    }

    public void setCanReserve(boolean canReserve) {
        IsCanReserve = canReserve;
    }

    public boolean isGuarantee() {
        return IsGuarantee;
    }

    public void setGuarantee(boolean guarantee) {
        IsGuarantee = guarantee;
    }

    public boolean isInstantConfirm() {
        return IsInstantConfirm;
    }

    public void setInstantConfirm(boolean instantConfirm) {
        IsInstantConfirm = instantConfirm;
    }

    public Object getPrices() {
        return Prices;
    }

    public void setPrices(Object prices) {
        Prices = prices;
    }

    public Object getTaxes() {
        return Taxes;
    }

    public void setTaxes(Object taxes) {
        Taxes = taxes;
    }

    public Object getFees() {
        return Fees;
    }

    public void setFees(Object fees) {
        Fees = fees;
    }

    public List<DailyPricesDto> getDailyPrices() {
        return DailyPrices;
    }

    public void setDailyPrices(List<DailyPricesDto> dailyPrices) {
        DailyPrices = dailyPrices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (!(o instanceof PriceInfosBeanDto)) {return false;}
        PriceInfosBeanDto that = (PriceInfosBeanDto) o;
        return IsCanReserve == that.IsCanReserve &&
                IsGuarantee == that.IsGuarantee &&
                IsInstantConfirm == that.IsInstantConfirm &&
                Objects.equals(PayType, that.PayType) &&
                Objects.equals(RatePlanCategory, that.RatePlanCategory) &&
                Objects.equals(RemainingRooms, that.RemainingRooms) &&
                Objects.equals(BookingCode, that.BookingCode) &&
                Objects.equals(RatePlanID, that.RatePlanID) &&
                Objects.equals(IsPromotion, that.IsPromotion) &&
                Objects.equals(Prices, that.Prices) &&
                Objects.equals(DailyPrices, that.DailyPrices) &&
                Objects.equals(Taxes, that.Taxes) &&
                Objects.equals(Fees, that.Fees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(PayType, RatePlanCategory, IsCanReserve, IsGuarantee, IsInstantConfirm, RemainingRooms, BookingCode, RatePlanID, IsPromotion, Prices, DailyPrices, Taxes, Fees);
    }

    @Override
    public String toString() {
        return "PriceInfosBeanDto{" +
                "PayType='" + PayType + '\'' +
                ", RatePlanCategory='" + RatePlanCategory + '\'' +
                ", IsCanReserve=" + IsCanReserve +
                ", IsGuarantee=" + IsGuarantee +
                ", IsInstantConfirm=" + IsInstantConfirm +
                ", RemainingRooms='" + RemainingRooms + '\'' +
                ", BookingCode='" + BookingCode + '\'' +
                ", RatePlanID='" + RatePlanID + '\'' +
                ", IsPromotion='" + IsPromotion + '\'' +
                ", Prices=" + Prices +
                ", DailyPrices=" + DailyPrices +
                ", Taxes=" + Taxes +
                ", Fees=" + Fees +
                '}';
    }
}
