package com.fntx.order.po;

import java.util.List;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @FileName: 房间类型响应
 * @Author: 胡庆康
 * @Date: 2019/7/12 15:00
 */
public class RoomTypeResponse {
    public static class Smoking {
        public String HasRoomInNonSmokingArea;
        public String HasNonSmokingRoom;
        public String HasDeoderizedRoom;
        public String NotAllowSmoking;
    }

    public static class BroadNet {
        public String HasBroadnet;
        public String HasWirelessBroadnet;
        public String WirelessBroadnetRoom;
        public String WirelessBroadnetFee;
        public String HasWiredBroadnet;
        public String WiredBroadnetRoom;
        public String WiredBroadnetFee;
        public String BroadnetFeeDetail;
        public String WirelessBroadnet;
        public String WiredBroadnet;
    }

    public static class ChildLimit {
        public String MaxOccupancy;
        public String MaxAge;
        public String MinAge;
    }

    public static class FacilityItem {
        public String ID;
        public String Name;
        public String Status;
    }

    public static class Facilities {
        public List<FacilityItem> FacilityItem;
        public String CategoryName;
    }

    public static class ExtraAttribute {
        public String Resolution;
        public String Rank;
        public String Sharpness;
        public String AestheticScore;
        public String Source;
    }

    public static class Pictures {
        public ExtraAttribute ExtraAttribute;
        public String ID;
        public String Type;
        public String Caption;
        public String URL;
    }

    public static class Descriptions {
    }

    public static class BedInfo {
        public String ID;
        public String Name;
        public String NumberOfBeds;
        public String BedWidth;
    }

    public static class RoomBedInfos {
        public List<BedInfo> BedInfo;
        public String ID;
        public String Name;
    }

    public static class RoomTypeTags {
    }

    public static class RoomTypeInfo {
        public Smoking Smoking;
        public BroadNet BroadNet;
        public ChildLimit ChildLimit;
        public List<Facilities> Facilities;
        public List<Pictures> Pictures;
        public List<Descriptions> Descriptions;
        public List<RoomBedInfos> RoomBedInfos;
        public List<RoomTypeTags> RoomTypeTags;
        public String RoomTypeID;
        public String RoomTypeName;
        public String StandardRoomType;
        public String RoomQuantity;
        public String MaxOccupancy;
        public String AreaRange;
        public String FloorRange;
        public String HasWindow;
        public String ExtraBedFee;
        public String BathRoomType;
    }

    public static class ApplicabilityInfo {
        public List<Integer> ApplicabilityIDs;
        public String Applicability;
        public String OtherDescription;
    }

    public static class Details {
        public String ContinentID;
        public String ContinentName;
        public String IsApplicative;
    }

    public static class AreaApplicabilityInfo {
        public List<Details> Details;
    }


    public static class RoomFGToPPInfo {
        public String CanFGToPP;
    }

    public static class RoomGiftInfos {
    }

    public static class ChannelLimit {
        public String IsApp;
        public String IsWeb;
    }

    public static class ExpressCheckout {
        public String IsSupported;
        public String DepositRatio;
    }

    public static class RoomTags {
        public String Code;
        public String Name;
        public String Value;
    }

    public static class BookingOffsets {
    }

    public static class LengthsOfStay {
    }

    public static class TimeLimitInfo {
    }

    public static class BookingRules {
        public List<BookingOffsets> BookingOffsets;
        public List<LengthsOfStay> LengthsOfStay;
        public List<TimeLimitInfo> TimeLimitInfo;
    }

    public static class HotelPromotions {
    }

    public static class RoomPromotions {
    }

    public static class MaskCampaignInfos {
    }

    public static class RoomInfos {
        public ApplicabilityInfo ApplicabilityInfo;
        public AreaApplicabilityInfo AreaApplicabilityInfo;
        public Smoking Smoking;
        public BroadNet BroadNet;
        public List<RoomBedInfos> RoomBedInfos;
        public RoomFGToPPInfo RoomFGToPPInfo;
        public List<RoomGiftInfos> RoomGiftInfos;
        public ChannelLimit ChannelLimit;
        public ExpressCheckout ExpressCheckout;
        public List<RoomTags> RoomTags;
        public List<BookingRules> BookingRules;
        public List<Descriptions> Descriptions;
        public List<HotelPromotions> HotelPromotions;
        public List<RoomPromotions> RoomPromotions;
        public List<MaskCampaignInfos> MaskCampaignInfos;
        public String RoomID;
        public String RoomName;
        public String RoomQuantity;
        public String MaxOccupancy;
        public String AreaRange;
        public String FloorRange;
        public String HasWindow;
        public String ExtraBedFee;
        public String IsHourlyRoom;
        public String IsFromAPI;
        public String IsShowAgencyTag;
        public String InvoiceType;
        public String InvoiceMode;
        public String IsSupportSpecialInvoice;
        public String ReceiveTextRemark;
    }

    public static class RoomStaticInfos {
        public RoomTypeInfo RoomTypeInfo;
        public List<RoomInfos> RoomInfos;
    }


    public ResponseStatus ResponseStatus;
    public List<RoomStaticInfos> RoomStaticInfos;

    public int NumberOfUnits;
}
