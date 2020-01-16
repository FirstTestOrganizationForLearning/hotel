package com.fntx.order.po;

import lombok.Data;

import java.util.List;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @FileName: 获取订单详情响应
 * @Author: 胡庆康
 * @Date: 2019/7/12 15:42
 */
@Data
public  class GetOrderDetailResponse {

    public GeneralEntity.ResponseStatus ResponseStatus;


    public List<GeneralEntity.Warnings> Warnings;


    public List<ReservationsList> ReservationsList;

    public List<GeneralEntity.Errors> Errors;

    public String TimeStamp;

    public String Version;

    public String PrimaryLangID;

    public static class Amenity {

        public String DescriptiveText;

        public String RoomAmenityCode;
    }

    @Data
    public static class RoomType {

        public List<Amenity> Amenities;

        public String Name;

        public String RoomTypeCode;

        public List<String> BedTypeCode;

        public int NumberOfUnits;
    }

    @Data
    public static class RatePlan {

        public List<AdditionalDetail> AdditionalDetails;

        public String RatePlanCode;

        public String RoomID;
    }

    @Data
    public static class AdditionalDetail {
        public String DetailDescription;
        public String Type;
        public String Code;
    }

    public static class Address {

        public List<String> AddressLine;

        public String CityName;

        public String PostalCode;
    }

    public static class ContactNumber {

        public String PhoneTechType;

        public String PhoneNumber;
    }

    @Data
    public static class BasicPropertyInfo {

        public Address Address;

        public List<ContactNumber> ContactNumbers;

        public List<Object> VendorMessages;

        public String HotelName;

        public String HotelCode;
    }

    public static class TPAExtension {

        public double ExclusiveAmount;

        public double InclusiveAmount;

        public String CurrencyCode;
    }

    @Data
    public static class Base {

        public List<TPAExtension> TPAExtensions;

        public double ExclusiveAmount;

        public double InclusiveAmount;

        public String CurrencyCode;
    }

    @Data
    public static class MealsIncluded {

        public boolean Breakfast;

        public int NumberOfBreakfast;
    }

    @Data
    public static class Rate {

        public Base Base;

        public MealsIncluded MealsIncluded;

        public String EffectiveDate;
    }

    @Data
    public static class RoomRate {

        public List<Rate> Rates;
    }

    @Data
    public static class RoomStay {

        public List<RoomType> RoomTypes;

        public List<RatePlan> RatePlans;

        public BasicPropertyInfo BasicPropertyInfo;

        public List<RoomRate> RoomRates;
    }

    public static class BillingInstructionCode {

        public String BillingCode;
    }

    @Data
    public static class GuestCount {

        public int Count;
    }

    @Data
    public static class GuestCounts {

        public GuestCount GuestCount;
    }

    @Data
    public static class TimeSpan {

        public String Start;

        public String End;
    }

    @Data
    public static class DisplayCurrency {

        public double ExclusiveAmount;

        public double InclusiveAmount;

        public String CurrencyCode;
    }

    @Data
    public static class AmountPercent {

        public List<DisplayCurrency> DisplayCurrency;

        public double Amount;

        public String CurrencyCode;
    }

    @Data
    public static class CancelPenalty {

        public AmountPercent AmountPercent;

        public String Start;

        public String End;
    }

    @Data
    public static class CancelPenalties {

        public CancelPenalty CancelPenalty;
    }

    @Data
    public static class NoShowPaid {

        public double Amount;

        public String CurrencyCode;
    }

    @Data
    public static class TPAExtensions {

        public List<DisplayCurrency> DisplayCurrency;

        public NoShowPaid NoShowPaid;
    }

    @Data
    public static class Total {

        public TPAExtensions TPAExtensions;

        public double ExclusiveAmount;

        public double InclusiveAmount;

        public String CurrencyCode;
    }

    @Data
    public static class HotelReservationID {

        public String ResIDType;

        public String ResIDValue;

        public String ResIDSource;

        public String CancelReason;

        public String CancellationDate;
    }

    @Data
    public static class ResGlobalInfo {

        public GuestCounts GuestCounts;

        public TimeSpan TimeSpan;

        public List<Object> DepositPayments;

        public CancelPenalties CancelPenalties;

        public Total Total;

        public List<Object> SpecialRequests;

        public List<HotelReservationID> HotelReservationIDs;
    }

    @Data
    public static class PersonName {

        public String Name;
    }

    @Data
    public static class Telephone {

        public String PhoneTechType;

        public String PhoneNumber;
    }

    public static class ContactPerson {

        public PersonName PersonName;

        public List<Telephone> Telephone;

        public String Email;
    }

    @Data
    public static class Customer {

        public List<PersonName> PersonName;

        public List<ContactPerson> ContactPerson;
    }

    @Data
    public static class Profile {

        public Customer Customer;
    }

    @Data
    public static class ProfileInfo {

        public Profile Profile;
    }

    @Data
    public static class Profiles {

        public ProfileInfo ProfileInfo;
    }

    public static class TPAExtensions2 {

        public String LateArrivalTime;
    }

    @Data
    public static class ResGuest {

        public Profiles Profiles;


        public TPAExtensions2 TPAExtensions;

        public String ArrivalTime;
    }

    @Data
    public static class ResGuests {

        public ResGuest ResGuest;
    }

    @Data
    public static class UniqueID {

        public String Type;


        public String ID;
    }

    public static class ContactInfo {

        public String PersonName;

        public List<Telephone> Telephone;
    }

    public static class PostAddress {

        public String AddressLine;

        public String PostalCode;
    }

    public static class Invoice {

        public ContactInfo ContactInfo;

        public PostAddress PostAddress;

        public String Body;

        public String FullDescription;

        public String ShortDescription;

        public String Title;
    }

    public static class OrderTag {

        public String Code;

        public String Value;
    }

    public static class TPAExtensions3 {

        public Invoice Invoice;

        public List<OrderTag> OrderTags;
    }

    @Data
    public static class ReservationsList {

        public List<RoomStay> RoomStays;

        public List<BillingInstructionCode> BillingInstructionCode;

        public ResGlobalInfo ResGlobalInfo;

        public ResGuests ResGuests;

        public List<UniqueID> UniqueID;


        public TPAExtensions3 TPAExtensions;

        public String CreateDateTime;

        public String LastModifyDateTime;

        public String ResStatus;

        public String OrderStatus;
    }
}
