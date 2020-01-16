package com.fntx.common.domain.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fntx.common.utils.StringToJsonSerializer;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: RoomPriceInfosBeanDto
 * @Author: 王俊文
 * @Date: 19-7-22 下午5:43
 * @Description: TODO
 */
public class RoomPriceInfosBeanDto  implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * CancelPolicyInfos : [{"PenaltyAmount":[{"Type":"DisplayCurrency","Amount":1564,"Currency":"CNY"},{"Type":"OriginalCurrency","Amount":1564,"Currency":"CNY"}],"Start":"2018-06-11T21:00:00.0000000","End":"2019-08-02T00:00:00.0000000+08:00"}]
     * ReserveTimeLimitInfo : {"LatestReserveTime":"2019-07-30T00:00:00.0000000"}
     * HoldDeadline : {}
     * PriceInfos : [{"Prices":[{"Type":"DisplayCurrency","ExclusiveAmount":1563,"InclusiveAmount":1563,"Currency":"CNY"},{"Type":"OriginalCurrency","ExclusiveAmount":1563.33,"InclusiveAmount":1563.33,"Currency":"CNY"}],"DailyPrices":[{"MealInfo":{"NumberOfBreakfast":0,"NumberOfLunch":0,"NumberOfDinner":0,"IsOptionalMeal":false},"Prices":[{"Type":"DisplayCurrency","ExclusiveAmount":1564,"InclusiveAmount":1564,"Currency":"CNY"},{"Type":"OriginalCurrency","ExclusiveAmount":1563.33,"InclusiveAmount":1563.33,"Currency":"CNY"}],"Cashbacks":[{"Type":"OriginalCurrency","Amount":1564,"Currency":"CNY"},{"Type":"DisplayCurrency","Amount":1564,"Currency":"CNY"}],"DiscountDetailInfos":[],"EffectiveDate":"2019-08-01T00:00:00.0000000","GuaranteeCode":"2"}],"Taxes":[],"Fees":[{"Amount":[{"Type":"OriginalCurrency","Amount":24.09,"Currency":"CNY"},{"Type":"DisplayCurrency","Amount":25,"Currency":"CNY"}],"Name":"城市税","Type":"ExcludedFromTotalPrice","ID":"3"},{"Amount":[{"Type":"OriginalCurrency","Amount":236.96,"Currency":"CNY"},{"Type":"DisplayCurrency","Amount":237,"Currency":"CNY"}],"Name":"度假村费","Type":"ExcludedFromTotalPrice","ID":"8"},{"Amount":[{"Type":"OriginalCurrency","Amount":200.96,"Currency":"CNY"},{"Type":"DisplayCurrency","Amount":201,"Currency":"CNY"}],"Name":"增值税","Type":"IncludedInTotalPrice","ID":"4"}],"PayType":"PP","RatePlanCategory":"501","IsCanReserve":true,"IsGuarantee":true,"IsInstantConfirm":true,"RemainingRooms":"8","BookingCode":"490529948","RatePlanID":"872831302","IsPromotion":"F"}]
     * RoomID : 490529948
     * RoomName : 都市双人房（特大床）
     */

    @JSONField(serializeUsing= StringToJsonSerializer.class, name = "ReserveTimeLimitInfo")
    private Object ReserveTimeLimitInfo;
    @JSONField(serializeUsing= StringToJsonSerializer.class, name = "HoldDeadline")
    private Object HoldDeadline;
    @JSONField(name = "RoomID")
    private int RoomID;
    @JSONField(name = "RoomName")
    private String RoomName;
    @JSONField(serializeUsing= StringToJsonSerializer.class, name = "CancelPolicyInfos")
    private Object CancelPolicyInfos;
    @JSONField(name = "PriceInfos")
    private List<PriceInfosBeanDto> PriceInfos;

    public Object getReserveTimeLimitInfo() {
        return ReserveTimeLimitInfo;
    }

    public void setReserveTimeLimitInfo(Object reserveTimeLimitInfo) {
        ReserveTimeLimitInfo = reserveTimeLimitInfo;
    }

    public Object getHoldDeadline() {
        return HoldDeadline;
    }

    public void setHoldDeadline(Object holdDeadline) {
        HoldDeadline = holdDeadline;
    }

    public Object getCancelPolicyInfos() {
        return CancelPolicyInfos;
    }

    public void setCancelPolicyInfos(Object cancelPolicyInfos) {
        CancelPolicyInfos = cancelPolicyInfos;
    }

    public int getRoomID() {
        return RoomID;
    }

    public void setRoomID(int RoomID) {
        this.RoomID = RoomID;
    }

    public String getRoomName() {
        return RoomName;
    }

    public void setRoomName(String RoomName) {
        this.RoomName = RoomName;
    }

    public List<PriceInfosBeanDto> getPriceInfos() {
        return PriceInfos;
    }

    public void setPriceInfos(List<PriceInfosBeanDto> PriceInfos) {
        this.PriceInfos = PriceInfos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (!(o instanceof RoomPriceInfosBeanDto)) {return false;}
        RoomPriceInfosBeanDto that = (RoomPriceInfosBeanDto) o;
        return RoomID == that.RoomID &&
                Objects.equals(ReserveTimeLimitInfo, that.ReserveTimeLimitInfo) &&
                Objects.equals(HoldDeadline, that.HoldDeadline) &&
                Objects.equals(RoomName, that.RoomName) &&
                Objects.equals(CancelPolicyInfos, that.CancelPolicyInfos) &&
                Objects.equals(PriceInfos, that.PriceInfos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ReserveTimeLimitInfo, HoldDeadline, RoomID, RoomName, CancelPolicyInfos, PriceInfos);
    }

    @Override
    public String toString() {
        return "RoomPriceInfosBeanDto{" +
                "ReserveTimeLimitInfo=" + ReserveTimeLimitInfo +
                ", HoldDeadline=" + HoldDeadline +
                ", RoomID=" + RoomID +
                ", RoomName='" + RoomName + '\'' +
                ", CancelPolicyInfos=" + CancelPolicyInfos +
                ", PriceInfos=" + PriceInfos +
                '}';
    }
}
