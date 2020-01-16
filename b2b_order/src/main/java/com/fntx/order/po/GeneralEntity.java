package com.fntx.order.po;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @FileName: 公用实体
 * @Author: 胡庆康
 * @Date: 2019/7/12 11:31
 */
@Data
public class GeneralEntity {
    @Data
    public static class Warnings {
        public String Type;
        public String ShortText;
        public String Code;
        public String Value;
    }

    @Data
    public static class Errors {
        public String Type;
        public String ShortText;
        public String Code;
        public String Value;
    }

    @Data
    public static class ResponseStatus {
        //响应报文时间戳
        @JSONField(name = "Timestamp")
        public String Timestamp;
        //返回结果
        @JSONField(name = "Ack")
        public String Ack;
        //错误列表
        public List<Error> Errors;
        public List<Extension> Extension;

        public static class Error {
            public String ErrCode;
            public String ErrMsg;
        }

        public static class Extension {
        }

        public static class UniqueIDItem {
            public String Type;
            public String ID;
        }

        @Data
        public static class HotelReservations {
            public HotelReservation HotelReservation;
        }

        @Data
        public static class HotelReservation {
            public RoomStays RoomStays;
            public ResGlobalInfo ResGlobalInfo;
            public TPA_Extensions TPA_Extensions;
            public String CreateDateTime;
            public String ResStatus;
            public String IsInstantConfirm;

            public ResGuests ResGuests;
        }

        public static class ResGuests {

            public ResGuest ResGuest;
        }

        public static class ResGuest {

            public Profiles Profiles;

            public TPA_Extensions TPA_Extensions;

            public String ArrivalTime;
        }

        public static class Profiles {
            public ProfileInfo ProfileInfo;

        }

        public static class ProfileInfo {

            public Profile Profile;
        }

        public static class Profile {

            public Customer Customer;
        }

        public static class Customer {

            public List<PersonNameItem> PersonName;

            public ContactPerson ContactPerson;
        }

        public static class PersonNameItem {

            public String Name;
        }

        public static class ContactPerson {

            public PersonName PersonName;

            public List<TelephoneItem> Telephone;

            public String Email;

            public String ContactType;
        }

        public static class PersonName {

            public String Name;
        }

        public static class ResGlobalInfo {

            public GuestCounts GuestCounts;

            public TimeSpan TimeSpan;

            public List<SpecialRequestsItem> SpecialRequests;

            public List<DepositPaymentsItem> DepositPayments;

            public Total Total;

            public TPA_Extensions TPA_Extensions;

            //public List<DepositPayments> DepositPayments;
            public List<CancelPenalties> CancelPenalties;
            //public Total Total;
            public List<HotelReservationIDs> HotelReservationIDs;
        }

        public static class TimeSpan {

            public String Start;

            public String End;
        }

        public static class SpecialRequestsItem {

            public String Text;

            public String Name;

            public String ParagraphNumber;

            public String RequestCode;
            // 尽量安排无烟房，如无请无烟处理
            public String CodeContext;
        }

        public static class DepositPaymentsItem {

            public AmountPercent AmountPercent;

            public String GuaranteeCode;

            public String GuaranteeType;
        }

        @Data
        public static class HotelReservationIDs {
            public String ResID_Type;
            public String ResID_Value;
        }

        public static class GuestCounts {

            public GuestCount GuestCount;
        }

        public static class GuestCount {

            public int Count;
        }

        @Data
        public static class RoomStays {
            public RoomStay RoomStay;
        }

        @Data
        public static class RoomStay {
            public RatePlans RatePlans;
            public RoomRates RoomRates;
            public Total Total;
            public BasicPropertyInfo BasicPropertyInfo;
            public List<DepositPayments> DepositPayments;
            public CancelPenalties CancelPenalties;
            public TPA_Extensions TPA_Extensions;
            public List<CancelPenaltiesV2> CancelPenaltiesV2;
            public String AvailabilityStatus;
            public RoomTypes RoomTypes;

        }

        public static class RoomTypes {
            public RoomTypeResponse RoomType;
        }


        public static class CancelPenaltiesV2 {
        }

        public static class CancelPenalties {
            public CancelPenalty CancelPenalty;
        }

        public static class CancelPenalty {
            public AmountPercent AmountPercent;
            public String Start;
            public String End;
        }

        public static class DepositPayments {
            public AmountPercent AmountPercent;
            public List<String> Description;
            public List<TPA_Extensions> TPA_Extensions;
            public String GuaranteeCode;
            public String Start;
            public String End;
        }

        public static class AmountPercent {
            public List<DisplayCurrency> DisplayCurrency;
            public String Amount;
            public String CurrencyCode;
        }

        public static class BasicPropertyInfo {
            public List<VendorMessages> VendorMessages;

            public String HotelCode;
        }

        public static class VendorMessages {
            public List<SubSection> SubSection;
            public String Title;
            public String Language;
            public String InfoType;
        }

        public static class SubSection {
            public List<String> Paragraph;
            public String SubTitle;
        }

        public static class Total {
            public List<TPA_Extensions> TPA_Extensions;
            public String ExclusiveAmount;
            public double InclusiveAmount;
            public String CurrencyCode;
        }

        @Data
        public static class RatePlans {
            public RatePlan RatePlan;
        }

        @Data
        public static class RoomRates {
            public RoomRate RoomRate;
        }

        public static class RoomRate {
            public List<Rates> Rates;
            public String RatePlanCode;
            public String RoomID;
            public String RoomTypeCode;
        }

        public static class Rates {
            public Base Base;
            public TPA_Extensions TPA_Extensions;
            public String MaxGuestApplicable;
            public String EffectiveDate;
            public String ExpireDate;
        }

        public static class Base {
            public List<TPA_Extensions> TPA_Extensions;
            public String ExclusiveAmount;
            public String InclusiveAmount;
            public String CurrencyCode;
        }

        public static class TPA_Extensions {
            public List<SpecialRequestOptions> SpecialRequestOptions;
            public LastArrivalDateTime LastArrivalDateTime;
            public List<DiscountTotalAmount> DiscountTotalAmount;
            public List<DiscountDetailInfos> DiscountDetailInfos;
            public List<DiscountOptionalDetailInfos> DiscountOptionalDetailInfos;

            public String ExclusiveAmount;
            public String InclusiveAmount;
            public String CurrencyCode;

            public String LateArrivalTime;

            public Invoice Invoice;

            public ShadowRoomInfo ShadowRoomInfo;

            public Cost Cost;
        }

        public static class RatePlan {

            public String RoomID;

            public String RatePlanCategory;

            public String PrepaidIndicator;

            public String BookingCode;

            public String RatePlanID;

            public List<String> RatePlanDescription;
            public String RatePlanCode;
            public String RatePlanName;
            //public String RoomID;
            public String RoomName;
            //public String PrepaidIndicator;
            public String AvailableQuantity;
            public String IsInstantConfirm;
            public String ReceiveTextRemark;
            public String SupplierID;
            public String InvoiceTargetType;
            public String MinQtyPerOrder;
        }

        public static class Invoice {

            public ContactInfo ContactInfo;

            public PostAddress PostAddress;

            public String Body;

            public String FullDescription;

            public String ShortDescription;

            public String Title;
        }

        public static class SpecialRequestOptions {
            public String Name;
            public String ParagraphNumber;
            public String RequestCode;
            public String CodeContext;
            public String IsNeedUserValue;
            public String IsDefaultOption;
            public String IsUnique;
        }

        public static class LastArrivalDateTime {
            public String DateTime;
            public String HourSpan;
            public String TimeZoneRPH;
        }

        public static class DiscountTotalAmount {
        }

        public static class DiscountDetailInfos {
        }

        public static class DiscountOptionalDetailInfos {
        }

        public static class ContactInfo {

            public String PersonName;

            public List<TelephoneItem> Telephone;

            public String Email;
        }

        public static class TelephoneItem {

            public String PhoneTechType;

            public String PhoneNumber;
        }

        public static class PostAddress {

            public String AddressLine;

            public String PostalCode;
        }

        public static class ShadowRoomInfo {

            public int ShadowID;
        }

        public static class Cost {
            public List<DisplayCurrency> DisplayCurrency;
            public String Amount;
            public String CurrencyCode;
        }

        public static class DisplayCurrency {
            public String ExclusiveAmount;
            public String InclusiveAmount;
            public String CurrencyCode;
        }
    }
}
