package com.fntx.common.domain.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: RoomPriceItems
 * @Author: 王俊文
 * @Date: 19-7-22 上午9:59
 * @Description: TODO
 */
public class RoomPriceItems implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * RoomPriceInfos : [{"CancelPolicyInfos":[{"PenaltyAmount":[{"Type":"DisplayCurrency","Amount":1564,"Currency":"CNY"},{"Type":"OriginalCurrency","Amount":1564,"Currency":"CNY"}],"Start":"2018-06-11T21:00:00.0000000","End":"2019-08-02T00:00:00.0000000+08:00"}],"ReserveTimeLimitInfo":{"LatestReserveTime":"2019-07-30T00:00:00.0000000"},"HoldDeadline":{},"PriceInfos":[{"Prices":[{"Type":"DisplayCurrency","ExclusiveAmount":1563,"InclusiveAmount":1563,"Currency":"CNY"},{"Type":"OriginalCurrency","ExclusiveAmount":1563.33,"InclusiveAmount":1563.33,"Currency":"CNY"}],"DailyPrices":[{"MealInfo":{"NumberOfBreakfast":0,"NumberOfLunch":0,"NumberOfDinner":0,"IsOptionalMeal":false},"Prices":[{"Type":"DisplayCurrency","ExclusiveAmount":1564,"InclusiveAmount":1564,"Currency":"CNY"},{"Type":"OriginalCurrency","ExclusiveAmount":1563.33,"InclusiveAmount":1563.33,"Currency":"CNY"}],"Cashbacks":[{"Type":"OriginalCurrency","Amount":1564,"Currency":"CNY"},{"Type":"DisplayCurrency","Amount":1564,"Currency":"CNY"}],"DiscountDetailInfos":[],"EffectiveDate":"2019-08-01T00:00:00.0000000","GuaranteeCode":"2"}],"Taxes":[],"Fees":[{"Amount":[{"Type":"OriginalCurrency","Amount":24.09,"Currency":"CNY"},{"Type":"DisplayCurrency","Amount":25,"Currency":"CNY"}],"Name":"城市税","Type":"ExcludedFromTotalPrice","ID":"3"},{"Amount":[{"Type":"OriginalCurrency","Amount":236.96,"Currency":"CNY"},{"Type":"DisplayCurrency","Amount":237,"Currency":"CNY"}],"Name":"度假村费","Type":"ExcludedFromTotalPrice","ID":"8"},{"Amount":[{"Type":"OriginalCurrency","Amount":200.96,"Currency":"CNY"},{"Type":"DisplayCurrency","Amount":201,"Currency":"CNY"}],"Name":"增值税","Type":"IncludedInTotalPrice","ID":"4"}],"PayType":"PP","RatePlanCategory":"501","IsCanReserve":true,"IsGuarantee":true,"IsInstantConfirm":true,"RemainingRooms":"8","BookingCode":"490529948","RatePlanID":"872831302","IsPromotion":"F"}],"RoomID":490529948,"RoomName":"都市双人房（特大床）"}]
     * RoomTypeID : 76426112
     */

    @JSONField(name = "RoomTypeID")
    private int RoomTypeID;
    @JSONField(name = "RoomPriceInfos")
    private List<RoomPriceInfosBean> RoomPriceInfos;

    public int getRoomTypeID() {
        return RoomTypeID;
    }

    public void setRoomTypeID(int RoomTypeID) {
        this.RoomTypeID = RoomTypeID;
    }

    public List<RoomPriceInfosBean> getRoomPriceInfos() {
        return RoomPriceInfos;
    }

    public void setRoomPriceInfos(List<RoomPriceInfosBean> RoomPriceInfos) {
        this.RoomPriceInfos = RoomPriceInfos;
    }

    public static class RoomPriceInfosBean implements Serializable{
        private static final long serialVersionUID = 3596404074344322713L;
        /**
         * CancelPolicyInfos : [{"PenaltyAmount":[{"Type":"DisplayCurrency","Amount":1564,"Currency":"CNY"},{"Type":"OriginalCurrency","Amount":1564,"Currency":"CNY"}],"Start":"2018-06-11T21:00:00.0000000","End":"2019-08-02T00:00:00.0000000+08:00"}]
         * ReserveTimeLimitInfo : {"LatestReserveTime":"2019-07-30T00:00:00.0000000"}
         * HoldDeadline : {}
         * PriceInfos : [{"Prices":[{"Type":"DisplayCurrency","ExclusiveAmount":1563,"InclusiveAmount":1563,"Currency":"CNY"},{"Type":"OriginalCurrency","ExclusiveAmount":1563.33,"InclusiveAmount":1563.33,"Currency":"CNY"}],"DailyPrices":[{"MealInfo":{"NumberOfBreakfast":0,"NumberOfLunch":0,"NumberOfDinner":0,"IsOptionalMeal":false},"Prices":[{"Type":"DisplayCurrency","ExclusiveAmount":1564,"InclusiveAmount":1564,"Currency":"CNY"},{"Type":"OriginalCurrency","ExclusiveAmount":1563.33,"InclusiveAmount":1563.33,"Currency":"CNY"}],"Cashbacks":[{"Type":"OriginalCurrency","Amount":1564,"Currency":"CNY"},{"Type":"DisplayCurrency","Amount":1564,"Currency":"CNY"}],"DiscountDetailInfos":[],"EffectiveDate":"2019-08-01T00:00:00.0000000","GuaranteeCode":"2"}],"Taxes":[],"Fees":[{"Amount":[{"Type":"OriginalCurrency","Amount":24.09,"Currency":"CNY"},{"Type":"DisplayCurrency","Amount":25,"Currency":"CNY"}],"Name":"城市税","Type":"ExcludedFromTotalPrice","ID":"3"},{"Amount":[{"Type":"OriginalCurrency","Amount":236.96,"Currency":"CNY"},{"Type":"DisplayCurrency","Amount":237,"Currency":"CNY"}],"Name":"度假村费","Type":"ExcludedFromTotalPrice","ID":"8"},{"Amount":[{"Type":"OriginalCurrency","Amount":200.96,"Currency":"CNY"},{"Type":"DisplayCurrency","Amount":201,"Currency":"CNY"}],"Name":"增值税","Type":"IncludedInTotalPrice","ID":"4"}],"PayType":"PP","RatePlanCategory":"501","IsCanReserve":true,"IsGuarantee":true,"IsInstantConfirm":true,"RemainingRooms":"8","BookingCode":"490529948","RatePlanID":"872831302","IsPromotion":"F"}]
         * RoomID : 490529948
         * RoomName : 都市双人房（特大床）
         */

        @JSONField(name = "ReserveTimeLimitInfo")
        private ReserveTimeLimitInfoBean ReserveTimeLimitInfo;
        @JSONField(name = "HoldDeadline")
        private HoldDeadlineBean HoldDeadline;
        @JSONField(name = "RoomID")
        private int RoomID;
        @JSONField(name = "RoomName")
        private String RoomName;
        @JSONField(name = "CancelPolicyInfos")
        private List<CancelPolicyInfosBean> CancelPolicyInfos;
        @JSONField(name = "PriceInfos")
        private List<PriceInfosBean> PriceInfos;

        public ReserveTimeLimitInfoBean getReserveTimeLimitInfo() {
            return ReserveTimeLimitInfo;
        }

        public void setReserveTimeLimitInfo(ReserveTimeLimitInfoBean ReserveTimeLimitInfo) {
            this.ReserveTimeLimitInfo = ReserveTimeLimitInfo;
        }

        public HoldDeadlineBean getHoldDeadline() {
            return HoldDeadline;
        }

        public void setHoldDeadline(HoldDeadlineBean HoldDeadline) {
            this.HoldDeadline = HoldDeadline;
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

        public List<CancelPolicyInfosBean> getCancelPolicyInfos() {
            return CancelPolicyInfos;
        }

        public void setCancelPolicyInfos(List<CancelPolicyInfosBean> CancelPolicyInfos) {
            this.CancelPolicyInfos = CancelPolicyInfos;
        }

        public List<PriceInfosBean> getPriceInfos() {
            return PriceInfos;
        }

        public void setPriceInfos(List<PriceInfosBean> PriceInfos) {
            this.PriceInfos = PriceInfos;
        }

        public static class ReserveTimeLimitInfoBean implements Serializable{
            private static final long serialVersionUID = -8696547394294637808L;
            /**
             * LatestReserveTime : 2019-07-30T00:00:00.0000000
             */

            @JSONField(name = "LatestReserveTime")
            private String LatestReserveTime;

            public String getLatestReserveTime() {
                return LatestReserveTime;
            }

            public void setLatestReserveTime(String LatestReserveTime) {
                this.LatestReserveTime = LatestReserveTime;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) {return true;}
                if (!(o instanceof ReserveTimeLimitInfoBean)) {return false;}
                ReserveTimeLimitInfoBean that = (ReserveTimeLimitInfoBean) o;
                return Objects.equals(LatestReserveTime, that.LatestReserveTime);
            }

            @Override
            public int hashCode() {
                return Objects.hash(LatestReserveTime);
            }

            @Override
            public String toString() {
                return "ReserveTimeLimitInfoBean{" +
                        "LatestReserveTime='" + LatestReserveTime + '\'' +
                        '}';
            }
        }

        public static class HoldDeadlineBean implements Serializable
        {
            private static final long serialVersionUID = -504130142804424011L;
            private String HoldTime;

            public String getHoldTime() {
                return HoldTime;
            }

            public void setHoldTime(String holdTime) {
                HoldTime = holdTime;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) {return true;}
                if (!(o instanceof HoldDeadlineBean)) {return false;}
                HoldDeadlineBean that = (HoldDeadlineBean) o;
                return Objects.equals(HoldTime, that.HoldTime);
            }

            @Override
            public int hashCode() {
                return Objects.hash(HoldTime);
            }

            @Override
            public String toString() {
                return "HoldDeadlineBean{" +
                        "HoldTime='" + HoldTime + '\'' +
                        '}';
            }
        }

        public static class CancelPolicyInfosBean  implements Serializable {

            private static final long serialVersionUID = -5041301428044240101L;
            /**
             * PenaltyAmount : [{"Type":"DisplayCurrency","Amount":1564,"Currency":"CNY"},{"Type":"OriginalCurrency","Amount":1564,"Currency":"CNY"}]
             * Start : 2018-06-11T21:00:00.0000000
             * End : 2019-08-02T00:00:00.0000000+08:00
             */

            @JSONField(name = "Start")
            private String Start;
            @JSONField(name = "End")
            private String End;
            @JSONField(name = "PenaltyAmount")
            private List<PenaltyAmountBean> PenaltyAmount;

            public String getStart() {
                return Start;
            }

            public void setStart(String Start) {
                this.Start = Start;
            }

            public String getEnd() {
                return End;
            }

            public void setEnd(String End) {
                this.End = End;
            }

            public List<PenaltyAmountBean> getPenaltyAmount() {
                return PenaltyAmount;
            }

            public void setPenaltyAmount(List<PenaltyAmountBean> PenaltyAmount) {
                this.PenaltyAmount = PenaltyAmount;
            }

            public static class PenaltyAmountBean  implements Serializable{
                private static final long serialVersionUID = 377904550848515148L;
                /**
                 * Type : DisplayCurrency
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

                @Override
                public boolean equals(Object o) {
                    if (this == o) {return true;}
                    if (!(o instanceof PenaltyAmountBean)) {return false;}
                    PenaltyAmountBean that = (PenaltyAmountBean) o;
                    return Amount == that.Amount &&
                            Objects.equals(Type, that.Type) &&
                            Objects.equals(Currency, that.Currency);
                }

                @Override
                public int hashCode() {
                    return Objects.hash(Type, Amount, Currency);
                }

                @Override
                public String toString() {
                    return "PenaltyAmountBean{" +
                            "Type='" + Type + '\'' +
                            ", Amount=" + Amount +
                            ", Currency='" + Currency + '\'' +
                            '}';
                }
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) {return true;}
                if (!(o instanceof CancelPolicyInfosBean)) {return false;}
                CancelPolicyInfosBean that = (CancelPolicyInfosBean) o;
                return Objects.equals(Start, that.Start) &&
                        Objects.equals(End, that.End) &&
                        Objects.equals(PenaltyAmount, that.PenaltyAmount);
            }

            @Override
            public int hashCode() {
                return Objects.hash(Start, End, PenaltyAmount);
            }

            @Override
            public String toString() {
                return "CancelPolicyInfosBean{" +
                        "Start='" + Start + '\'' +
                        ", End='" + End + '\'' +
                        ", PenaltyAmount=" + PenaltyAmount +
                        '}';
            }
        }

        public static class PriceInfosBean  implements Serializable{
            private static final long serialVersionUID = 8755252439826635067L;
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
            @JSONField(name = "Prices")
            private List<PricesBean> Prices;
            @JSONField(name = "DailyPrices")
            private List<DailyPricesBean> DailyPrices;
            @JSONField(name = "Taxes")
            private List<Taxes> Taxes;
            @JSONField(name = "Fees")
            private List<FeesBean> Fees;

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

            public List<PricesBean> getPrices() {
                return Prices;
            }

            public void setPrices(List<PricesBean> Prices) {
                this.Prices = Prices;
            }

            public List<DailyPricesBean> getDailyPrices() {
                return DailyPrices;
            }

            public void setDailyPrices(List<DailyPricesBean> DailyPrices) {
                this.DailyPrices = DailyPrices;
            }

            public List<Taxes> getTaxes() {
                return Taxes;
            }

            public void setTaxes(List<Taxes> taxes) {
                Taxes = taxes;
            }

            public List<FeesBean> getFees() {
                return Fees;
            }

            public void setFees(List<FeesBean> Fees) {
                this.Fees = Fees;
            }

            public static class Taxes {
                /**
                 * Amount : {"Type":"定义相邻节点的金额为原币种还是自定义币种。目前仅两种取值：DisplayCurrency; OriginalCurrency;","Amount":"单间房在入离时间内，某一税项的总金额","Currency":"币种"}
                 * Name : 税项名称
                 * Type : 该税项是否包含在单间房多间夜的税后总价中
                 */

                @JSONField(name = "Amount")
                private List<FeesBean.AmountBean> Amount;
                @JSONField(name = "Name")
                private String Name;
                @JSONField(name = "Type")
                private String Type;
                @JSONField(name = "ID")
                private String ID;

                public List<FeesBean.AmountBean> getAmount() {
                    return Amount;
                }

                public void setAmount(List<FeesBean.AmountBean> amount) {
                    Amount = amount;
                }

                public String getName() {
                    return Name;
                }

                public void setName(String Name) {
                    this.Name = Name;
                }

                public String getType() {
                    return Type;
                }

                public void setType(String Type) {
                    this.Type = Type;
                }

                public String getID() {
                    return ID;
                }

                public void setID(String ID) {
                    this.ID = ID;
                }

                @Override
                public boolean equals(Object o) {
                    if (this == o) {return true;}
                    if (!(o instanceof Taxes)) {return false;}
                    Taxes taxes = (Taxes) o;
                    return Objects.equals(Amount, taxes.Amount) &&
                            Objects.equals(Name, taxes.Name) &&
                            Objects.equals(Type, taxes.Type) &&
                            Objects.equals(ID, taxes.ID);
                }

                @Override
                public int hashCode() {
                    return Objects.hash(Amount, Name, Type, ID);
                }

                @Override
                public String toString() {
                    return "Taxes{" +
                            "Amount=" + Amount +
                            ", Name='" + Name + '\'' +
                            ", Type='" + Type + '\'' +
                            ", ID='" + ID + '\'' +
                            '}';
                }
            }


            public static class PricesBean {
                /**
                 * Type : DisplayCurrency
                 * ExclusiveAmount : 1563
                 * InclusiveAmount : 1563
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

                @Override
                public boolean equals(Object o) {
                    if (this == o) {return true;}
                    if (!(o instanceof PricesBean)) {return false;}
                    PricesBean that = (PricesBean) o;
                    return ExclusiveAmount == that.ExclusiveAmount &&
                            InclusiveAmount == that.InclusiveAmount &&
                            Objects.equals(Type, that.Type) &&
                            Objects.equals(Currency, that.Currency);
                }

                @Override
                public int hashCode() {
                    return Objects.hash(Type, ExclusiveAmount, InclusiveAmount, Currency);
                }

                @Override
                public String toString() {
                    return "PricesBean{" +
                            "Type='" + Type + '\'' +
                            ", ExclusiveAmount=" + ExclusiveAmount +
                            ", InclusiveAmount=" + InclusiveAmount +
                            ", Currency='" + Currency + '\'' +
                            '}';
                }
            }

            public static class DailyPricesBean {
                /**
                 * MealInfo : {"NumberOfBreakfast":0,"NumberOfLunch":0,"NumberOfDinner":0,"IsOptionalMeal":false}
                 * Prices : [{"Type":"DisplayCurrency","ExclusiveAmount":1564,"InclusiveAmount":1564,"Currency":"CNY"},{"Type":"OriginalCurrency","ExclusiveAmount":1563.33,"InclusiveAmount":1563.33,"Currency":"CNY"}]
                 * Cashbacks : [{"Type":"OriginalCurrency","Amount":1564,"Currency":"CNY"},{"Type":"DisplayCurrency","Amount":1564,"Currency":"CNY"}]
                 * DiscountDetailInfos : []
                 * EffectiveDate : 2019-08-01T00:00:00.0000000
                 * GuaranteeCode : 2
                 */

                @JSONField(name = "MealInfo")
                private MealInfoBean MealInfo;
                @JSONField(name = "EffectiveDate")
                private String EffectiveDate;
                @JSONField(name = "GuaranteeCode")
                private String GuaranteeCode;
                @JSONField(name = "Prices")
                private List<PricesBeanX> Prices;
                @JSONField(name = "Cashbacks")
                private List<CashbacksBean> Cashbacks;
                @JSONField(name = "DiscountDetailInfos")
                private List<?> DiscountDetailInfos;

                public MealInfoBean getMealInfo() {
                    return MealInfo;
                }

                public void setMealInfo(MealInfoBean MealInfo) {
                    this.MealInfo = MealInfo;
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

                public List<PricesBeanX> getPrices() {
                    return Prices;
                }

                public void setPrices(List<PricesBeanX> Prices) {
                    this.Prices = Prices;
                }

                public List<CashbacksBean> getCashbacks() {
                    return Cashbacks;
                }

                public void setCashbacks(List<CashbacksBean> Cashbacks) {
                    this.Cashbacks = Cashbacks;
                }

                public List<?> getDiscountDetailInfos() {
                    return DiscountDetailInfos;
                }

                public void setDiscountDetailInfos(List<?> DiscountDetailInfos) {
                    this.DiscountDetailInfos = DiscountDetailInfos;
                }

                public static class MealInfoBean {
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

                    @Override
                    public boolean equals(Object o) {
                        if (this == o) {return true;}
                        if (!(o instanceof MealInfoBean)) {return false;}
                        MealInfoBean that = (MealInfoBean) o;
                        return NumberOfBreakfast == that.NumberOfBreakfast &&
                                NumberOfLunch == that.NumberOfLunch &&
                                NumberOfDinner == that.NumberOfDinner &&
                                IsOptionalMeal == that.IsOptionalMeal;
                    }

                    @Override
                    public int hashCode() {
                        return Objects.hash(NumberOfBreakfast, NumberOfLunch, NumberOfDinner, IsOptionalMeal);
                    }

                    @Override
                    public String toString() {
                        return "MealInfoBean{" +
                                "NumberOfBreakfast=" + NumberOfBreakfast +
                                ", NumberOfLunch=" + NumberOfLunch +
                                ", NumberOfDinner=" + NumberOfDinner +
                                ", IsOptionalMeal=" + IsOptionalMeal +
                                '}';
                    }
                }

                public static class PricesBeanX {
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

                    @Override
                    public boolean equals(Object o) {
                        if (this == o) {return true;}
                        if (!(o instanceof PricesBeanX)) {return false;}
                        PricesBeanX that = (PricesBeanX) o;
                        return ExclusiveAmount == that.ExclusiveAmount &&
                                InclusiveAmount == that.InclusiveAmount &&
                                Objects.equals(Type, that.Type) &&
                                Objects.equals(Currency, that.Currency);
                    }

                    @Override
                    public int hashCode() {
                        return Objects.hash(Type, ExclusiveAmount, InclusiveAmount, Currency);
                    }

                    @Override
                    public String toString() {
                        return "PricesBeanX{" +
                                "Type='" + Type + '\'' +
                                ", ExclusiveAmount=" + ExclusiveAmount +
                                ", InclusiveAmount=" + InclusiveAmount +
                                ", Currency='" + Currency + '\'' +
                                '}';
                    }
                }

                public static class CashbacksBean {
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

                    @Override
                    public boolean equals(Object o) {
                        if (this == o) {return true;}
                        if (!(o instanceof CashbacksBean)) {return false;}
                        CashbacksBean that = (CashbacksBean) o;
                        return Amount == that.Amount &&
                                Objects.equals(Type, that.Type) &&
                                Objects.equals(Currency, that.Currency);
                    }

                    @Override
                    public int hashCode() {
                        return Objects.hash(Type, Amount, Currency);
                    }

                    @Override
                    public String toString() {
                        return "CashbacksBean{" +
                                "Type='" + Type + '\'' +
                                ", Amount=" + Amount +
                                ", Currency='" + Currency + '\'' +
                                '}';
                    }
                }

                @Override
                public boolean equals(Object o) {
                    if (this == o) {return true;}
                    if (!(o instanceof DailyPricesBean)) {return false;}
                    DailyPricesBean that = (DailyPricesBean) o;
                    return Objects.equals(MealInfo, that.MealInfo) &&
                            Objects.equals(EffectiveDate, that.EffectiveDate) &&
                            Objects.equals(GuaranteeCode, that.GuaranteeCode) &&
                            Objects.equals(Prices, that.Prices) &&
                            Objects.equals(Cashbacks, that.Cashbacks) &&
                            Objects.equals(DiscountDetailInfos, that.DiscountDetailInfos);
                }

                @Override
                public int hashCode() {
                    return Objects.hash(MealInfo, EffectiveDate, GuaranteeCode, Prices, Cashbacks, DiscountDetailInfos);
                }

                @Override
                public String toString() {
                    return "DailyPricesBean{" +
                            "MealInfo=" + MealInfo +
                            ", EffectiveDate='" + EffectiveDate + '\'' +
                            ", GuaranteeCode='" + GuaranteeCode + '\'' +
                            ", Prices=" + Prices +
                            ", Cashbacks=" + Cashbacks +
                            ", DiscountDetailInfos=" + DiscountDetailInfos +
                            '}';
                }
            }

            public static class FeesBean {
                /**
                 * Amount : [{"Type":"OriginalCurrency","Amount":24.09,"Currency":"CNY"},{"Type":"DisplayCurrency","Amount":25,"Currency":"CNY"}]
                 * Name : 城市税
                 * Type : ExcludedFromTotalPrice
                 * ID : 3
                 */

                @JSONField(name = "Name")
                private String Name;
                @JSONField(name = "Type")
                private String Type;
                @JSONField(name = "ID")
                private String ID;
                @JSONField(name = "Amount")
                private List<AmountBean> Amount;

                public String getName() {
                    return Name;
                }

                public void setName(String Name) {
                    this.Name = Name;
                }

                public String getType() {
                    return Type;
                }

                public void setType(String Type) {
                    this.Type = Type;
                }

                public String getID() {
                    return ID;
                }

                public void setID(String ID) {
                    this.ID = ID;
                }

                public List<AmountBean> getAmount() {
                    return Amount;
                }

                public void setAmount(List<AmountBean> Amount) {
                    this.Amount = Amount;
                }

                public static class AmountBean {
                    /**
                     * Type : OriginalCurrency
                     * Amount : 24.09
                     * Currency : CNY
                     */

                    @JSONField(name = "Type")
                    private String Type;
                    @JSONField(name = "Amount")
                    private double Amount;
                    @JSONField(name = "Currency")
                    private String Currency;

                    public String getType() {
                        return Type;
                    }

                    public void setType(String Type) {
                        this.Type = Type;
                    }

                    public double getAmount() {
                        return Amount;
                    }

                    public void setAmount(double Amount) {
                        this.Amount = Amount;
                    }

                    public String getCurrency() {
                        return Currency;
                    }

                    public void setCurrency(String Currency) {
                        this.Currency = Currency;
                    }

                    @Override
                    public boolean equals(Object o) {
                        if (this == o) {return true;}
                        if (!(o instanceof AmountBean)) {return false;}
                        AmountBean that = (AmountBean) o;
                        return Double.compare(that.Amount, Amount) == 0 &&
                                Objects.equals(Type, that.Type) &&
                                Objects.equals(Currency, that.Currency);
                    }

                    @Override
                    public int hashCode() {
                        return Objects.hash(Type, Amount, Currency);
                    }

                    @Override
                    public String toString() {
                        return "AmountBean{" +
                                "Type='" + Type + '\'' +
                                ", Amount=" + Amount +
                                ", Currency='" + Currency + '\'' +
                                '}';
                    }
                }

                @Override
                public boolean equals(Object o) {
                    if (this == o) {return true;}
                    if (!(o instanceof FeesBean)) {return false;}
                    FeesBean feesBean = (FeesBean) o;
                    return Objects.equals(Name, feesBean.Name) &&
                            Objects.equals(Type, feesBean.Type) &&
                            Objects.equals(ID, feesBean.ID) &&
                            Objects.equals(Amount, feesBean.Amount);
                }

                @Override
                public int hashCode() {
                    return Objects.hash(Name, Type, ID, Amount);
                }

                @Override
                public String toString() {
                    return "FeesBean{" +
                            "Name='" + Name + '\'' +
                            ", Type='" + Type + '\'' +
                            ", ID='" + ID + '\'' +
                            ", Amount=" + Amount +
                            '}';
                }
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) {return true;}
                if (!(o instanceof PriceInfosBean)) {return false;}
                PriceInfosBean that = (PriceInfosBean) o;
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
                return "PriceInfosBean{" +
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

        @Override
        public boolean equals(Object o) {
            if (this == o) {return true;}
            if (!(o instanceof RoomPriceInfosBean)) {return false;}
            RoomPriceInfosBean that = (RoomPriceInfosBean) o;
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
            return "RoomPriceInfosBean{" +
                    "ReserveTimeLimitInfo=" + ReserveTimeLimitInfo +
                    ", HoldDeadline=" + HoldDeadline +
                    ", RoomID=" + RoomID +
                    ", RoomName='" + RoomName + '\'' +
                    ", CancelPolicyInfos=" + CancelPolicyInfos +
                    ", PriceInfos=" + PriceInfos +
                    '}';
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (!(o instanceof RoomPriceItems)) {return false;}
        RoomPriceItems that = (RoomPriceItems) o;
        return RoomTypeID == that.RoomTypeID &&
                Objects.equals(RoomPriceInfos, that.RoomPriceInfos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(RoomTypeID, RoomPriceInfos);
    }

    @Override
    public String toString() {
        return "RoomPriceItems{" +
                "RoomTypeID=" + RoomTypeID +
                ", RoomPriceInfos=" + RoomPriceInfos +
                '}';
    }
}
