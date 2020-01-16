package com.fntx.common.domain.dto;

import com.fntx.common.domain.ErrorResp;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 酒店静态房型信息响应model
 * @author: 魏世杰
 * @date: Created in 2019/8/5 10:28
 * @version: 1.0
 * @modified By:
 */
public class HotelRoomDetailResModel extends ErrorResp implements Serializable {


    /**
     * ResponseStatus : {"Timestamp":"2019-08-05T10:47:51.813+08:00","Ack":"Success","Errors":[],"Extension":[]}
     * RoomStaticInfos : [{"RoomTypeInfo":{"BroadNet":{"HasBroadnet":0,"HasWirelessBroadnet":"F","WirelessBroadnetRoom":"1","WirelessBroadnetFee":"0","HasWiredBroadnet":"F","WiredBroadnetRoom":"1","WiredBroadnetFee":"0","WirelessBroadnet":0,"WiredBroadnet":0},"ChildLimit":{"MaxOccupancy":0,"MaxAge":3,"MinAge":0},"Facilities":[{"FacilityItem":[{"ID":"415","Name":"International Direct-dial","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"447","Name":"Slippers  ","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"384","Name":"Umbrella","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"392","Name":"Desk","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"448","Name":"Bathroom Magnifying Make-up Mirrior","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"385","Name":"Hot Water （24hrs）","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"433","Name":"Electric Kettle","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"434","Name":"Coffee/Tea Pot","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"435","Name":"Free Bottle of Water","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"437","Name":"Mini Bar","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"393","Name":"Ironing Facilities","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"436","Name":"Mini Refrigerator","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"449","Name":"Bathrobe  ","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"387","Name":"110V Socket","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"450","Name":"Bathtub","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"451","Name":"Separate Shower Room","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"394","Name":"In-Room Safety","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"388","Name":"Air conditioning","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"453","Name":"Electric Water Heater","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"481","Name":"Movie（charged）","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"418","Name":"Cable Channels ","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"426","Name":"Satellite Channels ","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"428","Name":"TV","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"423","Name":"Game Consoles","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"427","Name":"3D TV","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"417","Name":"CD Player  ","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"419","Name":"Computer /Laptop ","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"430","Name":"Video","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"431","Name":"iPad","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"425","Name":"Radio","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"432","Name":"iPod Docking Station","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"438","Name":"Coffee Machine","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"446","Name":"Toaster","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"444","Name":"Oven","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"445","Name":"Stove ","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"402","Name":"Fireplace  ","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"439","Name":"Dining Area","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"441","Name":"Kitchen","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"442","Name":"Kitchenware ","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"401","Name":"Fan  ","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"396","Name":"Electric Blankets  ","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"399","Name":"Private Entrance  ","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"443","Name":"Microwave","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"397","Name":"Mosquito Net ","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"440","Name":"Dishwasher","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"407","Name":"Washing Machine","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"406","Name":"Wardrobe/Closet ","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"404","Name":"Connecting Room","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"478","Name":"Terrace","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"403","Name":"Heating  ","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"475","Name":"Alarm Clock","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"484","Name":"Sewing Kit","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"408","Name":"220V Socket","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"487","Name":"Shade Curtain","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"488","Name":"Automatic curtain","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"400","Name":"Bed (over 200 cm length)","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"492","Name":"Spare Bedding","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"490","Name":"Bedding: Quilt","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"491","Name":"Bedding: Blankets or Duvet","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"479","Name":"Private Hot Spring","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"398","Name":"Private Swimming Pool","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"482","Name":"Voice Message","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"483","Name":"Welcome Gift","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"476","Name":"Executive Lounge","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"463","Name":"Free Toiletries （less 6）","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"495","Name":"Shared Hairdryer","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"460","Name":"Jacuzzi","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"459","Name":"Shared Toilet ","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"466","Name":"City View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"473","Name":"Sea View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"472","Name":"River View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"470","Name":"Mountain View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"468","Name":"Lake View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"467","Name":"Garden View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"474","Name":"View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"465","Name":"Balcony  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"421","Name":"Fax","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"461","Name":"Bathroom TV","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"462","Name":"Bathroom Phone","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"405","Name":"Sofa","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"416","Name":"Stereo","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"420","Name":"DVD Player","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"493","Name":"Free Newspaper","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"412","Name":"Turndown Service","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"410","Name":"Electronic Scale","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"494","Name":"High Speed Internet","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"424","Name":"Pay TV (on demand)","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"429","Name":"Telephone","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"653","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"654","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"722","Name":"SmartLock","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"833","Name":"Washlet","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"855","Name":"Air Cleaner","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"856","Name":"Air Detector","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"896","Name":"Fast food","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"897","Name":"Smoke Lampblack Machine","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"898","Name":"Condiment","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"1096","Name":"Facilities list (CN)","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"1097","Name":"Tea bag","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"1175","Name":"Smart RCU","Status":"1"}],"CategoryName":"Services & Others"}],"Pictures":[{"Type":"6","URL":"http://dimg04.c-ctrip.com/images//200d16000000zd0kfC615_R_550_412.jpg"},{"Type":"6","URL":"http://dimg04.c-ctrip.com/images//200c16000000zh4kz0BEB_R_550_412.jpg"}],"Descriptions":[{"Text":"","Category":"1"}],"RoomBedInfos":[{"BedInfo":[{"ID":"365","Name":"Double Bed","NumberOfBeds":"1","BedWidth":"1.45"}],"ID":"360","Name":"Large Bed"}],"RoomTypeID":1977056,"StandardRoomType":"","RoomQuantity":25,"MaxOccupancy":5,"AreaRange":"25","FloorRange":"2-6","HasWindow":"2","ExtraBedFee":"50.0000","BathRoomType":"Unknown"},"RoomInfos":[{"ApplicabilityInfo":{"Applicability":"10000000"},"Smoking":{"IsAllowSmoking":"-100"},"BroadNet":{"HasBroadnet":0,"HasWirelessBroadnet":"F","WirelessBroadnetRoom":"1","WirelessBroadnetFee":"0","HasWiredBroadnet":"F","WiredBroadnetRoom":"1","WiredBroadnetFee":"0","WirelessBroadnet":0,"WiredBroadnet":0},"RoomBedInfos":[{"BedInfo":[{"ID":"365","Name":"Double Bed","NumberOfBeds":"1","BedWidth":"1.45"}],"ID":"360","Name":"Large Bed"}],"RoomFGToPPInfo":{"CanFGToPP":false},"ChannelLimit":{"IsApp":true,"IsWeb":true,"IsWeChat":true},"ExpressCheckout":{"DepositAmount":[{"Amount":100}],"IsSupported":true,"DepositRatio":"-2.0","AuditTime":"37"},"RoomTags":[{"Code":"RateCodeID","Name":"RateCodeID","Value":"888737"},{"Code":"IsAgent","Name":"IsAgent","Value":"F"},{"Code":"VendorID","Name":"VendorID","Value":"0"},{"Code":"GenderType","Name":"GenderType","Value":"1"},{"Code":"IsStandardPrice","Name":"","Value":"true"},{"Code":"Promotion","Name":"SpecialPriceForTonight","Value":"6","Desc":""}],"BookingRules":[{"TimeLimitInfo":[{"DateRestrictions":[{"Scope":"Booking","DateType":"DateTime","Start":"2016-08-24 00:00:00","End":"2099-12-31 00:00:00","IsIntraday":"T"},{"Scope":"Booking","DateType":"Time","Start":"18:00:00","End":"30:00:00","IsIntraday":"T"},{"Scope":"StayIn","DateType":"Time","Start":"18:00:00","End":"30:00:00","IsIntraday":"T"}]}]}],"Descriptions":[{"Text":"2016-08-242099-12-3118:0023:59,！","Category":"Promotion"}],"RoomID":30206159,"PayType":"FG","RoomQuantity":25,"MaxOccupancy":5,"AreaRange":"25","FloorRange":"2-6","HasWindow":"2","ExtraBedFee":"Unknown","IsHourlyRoom":false,"IsFromAPI":false,"IsShowAgencyTag":true,"InvoiceType":2,"InvoiceMode":"0","IsSupportSpecialInvoice":"true","ReceiveTextRemark":false},{"ApplicabilityInfo":{"Applicability":"10000000"},"Smoking":{"IsAllowSmoking":"-100"},"BroadNet":{"HasBroadnet":0,"HasWirelessBroadnet":"F","WirelessBroadnetRoom":"1","WirelessBroadnetFee":"0","HasWiredBroadnet":"F","WiredBroadnetRoom":"1","WiredBroadnetFee":"0","WirelessBroadnet":0,"WiredBroadnet":0},"RoomBedInfos":[{"BedInfo":[{"ID":"365","Name":"Double Bed","NumberOfBeds":"1","BedWidth":"1.45"}],"ID":"360","Name":"Large Bed"}],"RoomFGToPPInfo":{"CanFGToPP":true},"ChannelLimit":{"IsApp":true,"IsWeb":true,"IsWeChat":true},"ExpressCheckout":{"DepositAmount":[{"Amount":100}],"IsSupported":true,"DepositRatio":"-2.0","AuditTime":"37"},"RoomTags":[{"Code":"RateCodeID","Name":"RateCodeID","Value":"12468455"},{"Code":"IsAgent","Name":"IsAgent","Value":"F"},{"Code":"VendorID","Name":"VendorID","Value":"0"},{"Code":"GenderType","Name":"GenderType","Value":"1"}],"BookingRules":[{}],"RoomID":357962984,"PayType":"PP","RoomQuantity":25,"MaxOccupancy":5,"AreaRange":"25","FloorRange":"2-6","HasWindow":"2","ExtraBedFee":"Unknown","IsHourlyRoom":false,"IsFromAPI":false,"IsShowAgencyTag":true,"InvoiceType":2,"InvoiceMode":"0","IsSupportSpecialInvoice":"true","ReceiveTextRemark":false},{"ApplicabilityInfo":{"Applicability":"10000000"},"Smoking":{"IsAllowSmoking":"-100"},"BroadNet":{"HasBroadnet":0,"HasWirelessBroadnet":"F","WirelessBroadnetRoom":"1","WirelessBroadnetFee":"0","HasWiredBroadnet":"F","WiredBroadnetRoom":"1","WiredBroadnetFee":"0","WirelessBroadnet":0,"WiredBroadnet":0},"RoomBedInfos":[{"BedInfo":[{"ID":"365","Name":"Double Bed","NumberOfBeds":"1","BedWidth":"1.45"}],"ID":"360","Name":"Large Bed"}],"RoomFGToPPInfo":{"CanFGToPP":true},"ChannelLimit":{"IsApp":true,"IsWeb":true,"IsWeChat":true},"ExpressCheckout":{"DepositAmount":[{"Amount":100}],"IsSupported":true,"DepositRatio":"-2.0","AuditTime":"37"},"RoomTags":[{"Code":"RateCodeID","Name":"RateCodeID","Value":"2740578"},{"Code":"IsAgent","Name":"IsAgent","Value":"F"},{"Code":"VendorID","Name":"VendorID","Value":"0"},{"Code":"GenderType","Name":"GenderType","Value":"1"}],"BookingRules":[{}],"RoomID":358383484,"PayType":"PP","RoomQuantity":25,"MaxOccupancy":5,"AreaRange":"25","FloorRange":"2-6","HasWindow":"2","ExtraBedFee":"Unknown","IsHourlyRoom":false,"IsFromAPI":false,"IsShowAgencyTag":true,"InvoiceType":2,"InvoiceMode":"0","IsSupportSpecialInvoice":"true","ReceiveTextRemark":false},{"ApplicabilityInfo":{"Applicability":"10000000"},"Smoking":{"IsAllowSmoking":"-100"},"BroadNet":{"HasBroadnet":0,"HasWirelessBroadnet":"F","WirelessBroadnetRoom":"1","WirelessBroadnetFee":"0","HasWiredBroadnet":"F","WiredBroadnetRoom":"1","WiredBroadnetFee":"0","WirelessBroadnet":0,"WiredBroadnet":0},"RoomBedInfos":[{"BedInfo":[{"ID":"365","Name":"Double Bed","NumberOfBeds":"1","BedWidth":"1.45"}],"ID":"360","Name":"Large Bed"}],"RoomFGToPPInfo":{"CanFGToPP":true},"ChannelLimit":{"IsApp":true,"IsWeb":true,"IsWeChat":true},"ExpressCheckout":{"DepositAmount":[{"Amount":100}],"IsSupported":true,"DepositRatio":"-2.0","AuditTime":"37"},"RoomTags":[{"Code":"RateCodeID","Name":"RateCodeID","Value":"7240214"},{"Code":"IsAgent","Name":"IsAgent","Value":"F"},{"Code":"VendorID","Name":"VendorID","Value":"0"},{"Code":"GenderType","Name":"GenderType","Value":"1"}],"BookingRules":[{}],"RoomID":358383485,"PayType":"PP","RoomQuantity":25,"MaxOccupancy":5,"AreaRange":"25","FloorRange":"2-6","HasWindow":"2","ExtraBedFee":"Unknown","IsHourlyRoom":false,"IsFromAPI":false,"IsShowAgencyTag":true,"InvoiceType":2,"InvoiceMode":"0","IsSupportSpecialInvoice":"true","ReceiveTextRemark":false}]},{"RoomTypeInfo":{"BroadNet":{"HasBroadnet":1,"HasWirelessBroadnet":"F","WirelessBroadnetRoom":"1","WirelessBroadnetFee":"0","HasWiredBroadnet":"T","WiredBroadnetRoom":"1","WiredBroadnetFee":"0","WirelessBroadnet":0,"WiredBroadnet":2},"ChildLimit":{"MaxOccupancy":2,"MaxAge":12,"MinAge":-1},"Facilities":[{"FacilityItem":[{"ID":"415","Name":"International Direct-dial","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"447","Name":"Slippers  ","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"384","Name":"Umbrella","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"392","Name":"Desk","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"448","Name":"Bathroom Magnifying Make-up Mirrior","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"385","Name":"Hot Water （24hrs）","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"433","Name":"Electric Kettle","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"434","Name":"Coffee/Tea Pot","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"435","Name":"Free Bottle of Water","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"437","Name":"Mini Bar","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"393","Name":"Ironing Facilities","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"436","Name":"Mini Refrigerator","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"449","Name":"Bathrobe  ","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"386","Name":"Multi-type Outlets","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"387","Name":"110V Socket","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"450","Name":"Bathtub","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"451","Name":"Separate Shower Room","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"394","Name":"In-Room Safety","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"388","Name":"Air conditioning","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"453","Name":"Electric Water Heater","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"481","Name":"Movie（charged）","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"418","Name":"Cable Channels ","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"426","Name":"Satellite Channels ","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"428","Name":"TV","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"423","Name":"Game Consoles","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"427","Name":"3D TV","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"417","Name":"CD Player  ","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"419","Name":"Computer /Laptop ","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"430","Name":"Video","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"431","Name":"iPad","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"425","Name":"Radio","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"432","Name":"iPod Docking Station","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"438","Name":"Coffee Machine","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"446","Name":"Toaster","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"444","Name":"Oven","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"445","Name":"Stove ","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"402","Name":"Fireplace  ","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"439","Name":"Dining Area","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"441","Name":"Kitchen","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"442","Name":"Kitchenware ","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"401","Name":"Fan  ","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"396","Name":"Electric Blankets  ","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"399","Name":"Private Entrance  ","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"443","Name":"Microwave","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"397","Name":"Mosquito Net ","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"440","Name":"Dishwasher","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"407","Name":"Washing Machine","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"406","Name":"Wardrobe/Closet ","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"404","Name":"Connecting Room","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"478","Name":"Terrace","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"403","Name":"Heating  ","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"475","Name":"Alarm Clock","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"484","Name":"Sewing Kit","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"408","Name":"220V Socket","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"487","Name":"Shade Curtain","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"488","Name":"Automatic curtain","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"400","Name":"Bed (over 200 cm length)","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"492","Name":"Spare Bedding","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"490","Name":"Bedding: Quilt","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"491","Name":"Bedding: Blankets or Duvet","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"479","Name":"Private Hot Spring","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"398","Name":"Private Swimming Pool","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"482","Name":"Voice Message","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"483","Name":"Welcome Gift","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"476","Name":"Executive Lounge","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"463","Name":"Free Toiletries （less 6）","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"495","Name":"Shared Hairdryer","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"460","Name":"Jacuzzi","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"459","Name":"Shared Toilet ","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"466","Name":"City View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"473","Name":"Sea View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"472","Name":"River View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"470","Name":"Mountain View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"468","Name":"Lake View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"467","Name":"Garden View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"474","Name":"View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"465","Name":"Balcony  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"421","Name":"Fax","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"461","Name":"Bathroom TV","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"462","Name":"Bathroom Phone","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"405","Name":"Sofa","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"416","Name":"Stereo","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"420","Name":"DVD Player","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"493","Name":"Free Newspaper","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"412","Name":"Turndown Service","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"410","Name":"Electronic Scale","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"494","Name":"High Speed Internet","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"424","Name":"Pay TV (on demand)","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"429","Name":"Telephone","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"653","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"654","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"722","Name":"SmartLock","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"833","Name":"Washlet","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"855","Name":"Air Cleaner","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"856","Name":"Air Detector","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"896","Name":"Fast food","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"897","Name":"Smoke Lampblack Machine","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"898","Name":"Condiment","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"1096","Name":"Facilities list (CN)","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"1097","Name":"Tea bag","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"1175","Name":"Smart RCU","Status":"1"}],"CategoryName":"Services & Others"}],"Pictures":[{"Type":"6","URL":"http://dimg04.c-ctrip.com/images//200413000000tzj6hAD77_R_550_412.jpg"},{"Type":"6","URL":"http://dimg04.c-ctrip.com/images//200l14000000x07866EAF_R_550_412.png"},{"Type":"6","URL":"http://dimg04.c-ctrip.com/images//200j160000010cjanE760_R_550_412.jpg"},{"Type":"6","URL":"http://dimg04.c-ctrip.com/images//200k12000000s4iwh8A08_R_550_412.jpg"}],"RoomBedInfos":[{"BedInfo":[{"ID":"367","Name":"Water Bed","NumberOfBeds":"1","BedWidth":"1.0"}],"ID":"363","Name":"Multiple Beds"},{"BedInfo":[{"ID":"366","Name":"Round Bed","NumberOfBeds":"2","BedWidth":"1.0"}],"ID":"363","Name":"Multiple Beds"},{"BedInfo":[{"ID":"4061","Name":"King Bed","NumberOfBeds":"1","BedWidth":"1.81"}],"ID":"363","Name":"Multiple Beds"}],"RoomTypeID":7397218,"StandardRoomType":"","RoomQuantity":6,"MaxOccupancy":1,"AreaRange":"50.5","FloorRange":"5-10","HasWindow":"1","ExtraBedFee":"50.0000","BathRoomType":"Unknown"},"RoomInfos":[{"ApplicabilityInfo":{"Applicability":"10000000"},"Smoking":{"IsAllowSmoking":"2"},"BroadNet":{"HasBroadnet":1,"HasWirelessBroadnet":"F","WirelessBroadnetRoom":"1","WirelessBroadnetFee":"0","HasWiredBroadnet":"T","WiredBroadnetRoom":"1","WiredBroadnetFee":"0","WirelessBroadnet":0,"WiredBroadnet":2},"RoomBedInfos":[{"BedInfo":[{"ID":"367","Name":"Water Bed","NumberOfBeds":"1","BedWidth":"1.0"}],"ID":"363","Name":"Multiple Beds"},{"BedInfo":[{"ID":"366","Name":"Round Bed","NumberOfBeds":"2","BedWidth":"1.0"}],"ID":"363","Name":"Multiple Beds"},{"BedInfo":[{"ID":"4061","Name":"King Bed","NumberOfBeds":"1","BedWidth":"1.81"}],"ID":"363","Name":"Multiple Beds"}],"RoomFGToPPInfo":{"CanFGToPP":true},"ChannelLimit":{"IsApp":true,"IsWeb":true,"IsWeChat":true},"ExpressCheckout":{"DepositAmount":[{"Amount":100}],"IsSupported":true,"DepositRatio":"-2.0","AuditTime":"37"},"RoomTags":[{"Code":"RateCodeID","Name":"RateCodeID","Value":"40437062"},{"Code":"IsAgent","Name":"IsAgent","Value":"F"},{"Code":"VendorID","Name":"VendorID","Value":"0"},{"Code":"GenderType","Name":"GenderType","Value":"1"},{"Code":"IsStandardPrice","Name":"","Value":"true"},{"Code":"Promotion","Name":"LimitedBuying","Value":"5","Desc":""}],"BookingRules":[{"TimeLimitInfo":[{"DateRestrictions":[{"Scope":"Booking","DateType":"DateTime","Start":"2016-11-21 17:25:52","End":"2016-12-30 23:59:59","IsIntraday":"F"},{"Scope":"Booking","DateType":"Time","Start":"09:00","End":"23:00","IsIntraday":"F"}],"AvailableDaysOfWeek":[{"Scope":"Booking","WeeklyIndex":"1111111"}]}]}],"Descriptions":[{"Text":"2016-11-21 17:25:522016-12-30 23:59:59 923，，。","Category":"Promotion"}],"RoomID":30206160,"PayType":"PP","RoomQuantity":6,"MaxOccupancy":1,"AreaRange":"50.5","FloorRange":"5-10","HasWindow":"1","ExtraBedFee":"50.0000","IsHourlyRoom":false,"IsFromAPI":false,"IsShowAgencyTag":true,"InvoiceType":2,"InvoiceMode":"0","IsSupportSpecialInvoice":"true","ReceiveTextRemark":false},{"ApplicabilityInfo":{"Applicability":"10000000"},"Smoking":{"IsAllowSmoking":"2"},"BroadNet":{"HasBroadnet":1,"HasWirelessBroadnet":"F","WirelessBroadnetRoom":"1","WirelessBroadnetFee":"0","HasWiredBroadnet":"T","WiredBroadnetRoom":"1","WiredBroadnetFee":"0","WirelessBroadnet":0,"WiredBroadnet":2},"RoomBedInfos":[{"BedInfo":[{"ID":"367","Name":"Water Bed","NumberOfBeds":"1","BedWidth":"1.0"}],"ID":"363","Name":"Multiple Beds"},{"BedInfo":[{"ID":"366","Name":"Round Bed","NumberOfBeds":"2","BedWidth":"1.0"}],"ID":"363","Name":"Multiple Beds"},{"BedInfo":[{"ID":"4061","Name":"King Bed","NumberOfBeds":"1","BedWidth":"1.81"}],"ID":"363","Name":"Multiple Beds"}],"RoomFGToPPInfo":{"CanFGToPP":false},"ChannelLimit":{"IsApp":true,"IsWeb":true,"IsWeChat":true},"ExpressCheckout":{"DepositAmount":[{"Amount":100}],"IsSupported":true,"DepositRatio":"-2.0","AuditTime":"37"},"RoomTags":[{"Code":"RateCodeID","Name":"RateCodeID","Value":"12468419"},{"Code":"IsAgent","Name":"IsAgent","Value":"F"},{"Code":"VendorID","Name":"VendorID","Value":"0"},{"Code":"GenderType","Name":"GenderType","Value":"1"},{"Code":"IsStandardPrice","Name":"","Value":"true"}],"BookingRules":[{}],"RoomID":407592529,"PayType":"FG","RoomQuantity":6,"MaxOccupancy":1,"AreaRange":"50.5","FloorRange":"5-10","HasWindow":"1","ExtraBedFee":"50.0000","IsHourlyRoom":false,"IsFromAPI":false,"IsShowAgencyTag":true,"InvoiceType":2,"InvoiceMode":"0","IsSupportSpecialInvoice":"true","ReceiveTextRemark":false},{"ApplicabilityInfo":{"Applicability":"10000000"},"Smoking":{"IsAllowSmoking":"2"},"BroadNet":{"HasBroadnet":1,"HasWirelessBroadnet":"F","WirelessBroadnetRoom":"1","WirelessBroadnetFee":"0","HasWiredBroadnet":"T","WiredBroadnetRoom":"1","WiredBroadnetFee":"0","WirelessBroadnet":0,"WiredBroadnet":2},"RoomBedInfos":[{"BedInfo":[{"ID":"367","Name":"Water Bed","NumberOfBeds":"1","BedWidth":"1.0"}],"ID":"363","Name":"Multiple Beds"},{"BedInfo":[{"ID":"366","Name":"Round Bed","NumberOfBeds":"2","BedWidth":"1.0"}],"ID":"363","Name":"Multiple Beds"},{"BedInfo":[{"ID":"4061","Name":"King Bed","NumberOfBeds":"1","BedWidth":"1.81"}],"ID":"363","Name":"Multiple Beds"}],"RoomFGToPPInfo":{"CanFGToPP":true},"ChannelLimit":{"IsApp":true,"IsWeb":true,"IsWeChat":true},"ExpressCheckout":{"DepositAmount":[{"Amount":100}],"IsSupported":true,"DepositRatio":"-2.0","AuditTime":"37"},"RoomTags":[{"Code":"RateCodeID","Name":"RateCodeID","Value":"7240214"},{"Code":"IsAgent","Name":"IsAgent","Value":"F"},{"Code":"VendorID","Name":"VendorID","Value":"0"},{"Code":"GenderType","Name":"GenderType","Value":"1"},{"Code":"IsStandardPrice","Name":"","Value":"true"}],"BookingRules":[{}],"RoomID":407597315,"PayType":"FG","RoomQuantity":6,"MaxOccupancy":1,"AreaRange":"50.5","FloorRange":"5-10","HasWindow":"1","ExtraBedFee":"50.0000","IsHourlyRoom":false,"IsFromAPI":false,"IsShowAgencyTag":true,"InvoiceType":2,"InvoiceMode":"0","IsSupportSpecialInvoice":"true","ReceiveTextRemark":false},{"ApplicabilityInfo":{"Applicability":"10000000"},"Smoking":{"IsAllowSmoking":"2"},"BroadNet":{"HasBroadnet":1,"HasWirelessBroadnet":"F","WirelessBroadnetRoom":"1","WirelessBroadnetFee":"0","HasWiredBroadnet":"T","WiredBroadnetRoom":"1","WiredBroadnetFee":"0","WirelessBroadnet":0,"WiredBroadnet":2},"RoomBedInfos":[{"BedInfo":[{"ID":"367","Name":"Water Bed","NumberOfBeds":"1","BedWidth":"1.0"}],"ID":"363","Name":"Multiple Beds"},{"BedInfo":[{"ID":"366","Name":"Round Bed","NumberOfBeds":"2","BedWidth":"1.0"}],"ID":"363","Name":"Multiple Beds"},{"BedInfo":[{"ID":"4061","Name":"King Bed","NumberOfBeds":"1","BedWidth":"1.81"}],"ID":"363","Name":"Multiple Beds"}],"RoomFGToPPInfo":{"CanFGToPP":true},"ChannelLimit":{"IsApp":true,"IsWeb":true,"IsWeChat":true},"ExpressCheckout":{"DepositAmount":[{"Amount":100}],"IsSupported":true,"DepositRatio":"-2.0","AuditTime":"37"},"RoomTags":[{"Code":"RateCodeID","Name":"RateCodeID","Value":"38400697"},{"Code":"IsAgent","Name":"IsAgent","Value":"F"},{"Code":"VendorID","Name":"VendorID","Value":"0"},{"Code":"GenderType","Name":"GenderType","Value":"1"},{"Code":"Promotion","Name":"PromotionOfLimitedBookingDays","Value":"3","Desc":""}],"BookingRules":[{"LengthsOfStay":[{"MinMaxType":"Min","Time":4,"TimeUnit":"Day","MustBeMultiple":false}]}],"Descriptions":[{"Text":"4，，，。","Category":"Promotion"}],"RoomID":438506669,"PayType":"PP","RoomQuantity":6,"MaxOccupancy":1,"AreaRange":"50.5","FloorRange":"5-10","HasWindow":"1","ExtraBedFee":"50.0000","IsHourlyRoom":false,"IsFromAPI":false,"IsShowAgencyTag":true,"InvoiceType":2,"InvoiceMode":"0","IsSupportSpecialInvoice":"true","ReceiveTextRemark":false},{"ApplicabilityInfo":{"Applicability":"10000000"},"Smoking":{"IsAllowSmoking":"2"},"BroadNet":{"HasBroadnet":1,"HasWirelessBroadnet":"F","WirelessBroadnetRoom":"1","WirelessBroadnetFee":"0","HasWiredBroadnet":"T","WiredBroadnetRoom":"1","WiredBroadnetFee":"0","WirelessBroadnet":0,"WiredBroadnet":2},"RoomBedInfos":[{"BedInfo":[{"ID":"367","Name":"Water Bed","NumberOfBeds":"1","BedWidth":"1.0"}],"ID":"363","Name":"Multiple Beds"},{"BedInfo":[{"ID":"366","Name":"Round Bed","NumberOfBeds":"2","BedWidth":"1.0"}],"ID":"363","Name":"Multiple Beds"},{"BedInfo":[{"ID":"4061","Name":"King Bed","NumberOfBeds":"1","BedWidth":"1.81"}],"ID":"363","Name":"Multiple Beds"}],"RoomFGToPPInfo":{"CanFGToPP":false},"ChannelLimit":{"IsApp":true,"IsWeb":true,"IsWeChat":true},"ExpressCheckout":{"DepositAmount":[{"Amount":100}],"IsSupported":true,"DepositRatio":"-2.0","AuditTime":"37"},"RoomTags":[{"Code":"RateCodeID","Name":"RateCodeID","Value":"12469793"},{"Code":"IsAgent","Name":"IsAgent","Value":"F"},{"Code":"VendorID","Name":"VendorID","Value":"0"},{"Code":"GenderType","Name":"GenderType","Value":"1"},{"Code":"IsStandardPrice","Name":"","Value":"true"},{"Code":"Promotion","Name":"Buy2Get1Free","Value":"1","Desc":"21"}],"BookingRules":[{"LengthsOfStay":[{"MinMaxType":"Min","Time":3,"TimeUnit":"Day","MustBeMultiple":true}],"Discount":{"NightsRequired":2,"NightsDiscounted":1,"DiscountPattern":"001"}}],"Descriptions":[{"Text":"21，，，，，，。","Category":"Promotion"}],"RoomID":450693392,"PayType":"FG","RoomQuantity":6,"MaxOccupancy":1,"AreaRange":"50.5","FloorRange":"5-10","HasWindow":"1","ExtraBedFee":"50.0000","IsHourlyRoom":false,"IsFromAPI":false,"IsShowAgencyTag":true,"InvoiceType":2,"InvoiceMode":"0","IsSupportSpecialInvoice":"true","ReceiveTextRemark":false}]},{"RoomTypeInfo":{"BroadNet":{"HasBroadnet":0,"HasWirelessBroadnet":"F","WirelessBroadnetRoom":"1","WirelessBroadnetFee":"0","HasWiredBroadnet":"F","WiredBroadnetRoom":"1","WiredBroadnetFee":"0","WirelessBroadnet":0,"WiredBroadnet":0},"ChildLimit":{"MaxOccupancy":1,"MaxAge":12,"MinAge":-1},"Facilities":[{"FacilityItem":[{"ID":"415","Name":"International Direct-dial","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"447","Name":"Slippers  ","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"384","Name":"Umbrella","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"392","Name":"Desk","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"448","Name":"Bathroom Magnifying Make-up Mirrior","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"385","Name":"Hot Water （24hrs）","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"433","Name":"Electric Kettle","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"434","Name":"Coffee/Tea Pot","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"435","Name":"Free Bottle of Water","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"437","Name":"Mini Bar","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"393","Name":"Ironing Facilities","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"436","Name":"Mini Refrigerator","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"449","Name":"Bathrobe  ","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"386","Name":"Multi-type Outlets","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"387","Name":"110V Socket","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"450","Name":"Bathtub","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"451","Name":"Separate Shower Room","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"394","Name":"In-Room Safety","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"388","Name":"Air conditioning","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"453","Name":"Electric Water Heater","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"481","Name":"Movie（charged）","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"418","Name":"Cable Channels ","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"426","Name":"Satellite Channels ","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"428","Name":"TV","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"423","Name":"Game Consoles","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"427","Name":"3D TV","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"417","Name":"CD Player  ","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"419","Name":"Computer /Laptop ","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"430","Name":"Video","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"431","Name":"iPad","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"425","Name":"Radio","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"432","Name":"iPod Docking Station","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"438","Name":"Coffee Machine","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"446","Name":"Toaster","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"444","Name":"Oven","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"445","Name":"Stove ","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"402","Name":"Fireplace  ","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"439","Name":"Dining Area","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"441","Name":"Kitchen","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"442","Name":"Kitchenware ","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"401","Name":"Fan  ","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"396","Name":"Electric Blankets  ","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"399","Name":"Private Entrance  ","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"443","Name":"Microwave","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"397","Name":"Mosquito Net ","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"440","Name":"Dishwasher","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"407","Name":"Washing Machine","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"406","Name":"Wardrobe/Closet ","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"404","Name":"Connecting Room","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"478","Name":"Terrace","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"403","Name":"Heating  ","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"475","Name":"Alarm Clock","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"484","Name":"Sewing Kit","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"408","Name":"220V Socket","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"487","Name":"Shade Curtain","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"488","Name":"Automatic curtain","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"400","Name":"Bed (over 200 cm length)","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"492","Name":"Spare Bedding","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"490","Name":"Bedding: Quilt","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"491","Name":"Bedding: Blankets or Duvet","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"479","Name":"Private Hot Spring","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"398","Name":"Private Swimming Pool","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"482","Name":"Voice Message","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"483","Name":"Welcome Gift","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"476","Name":"Executive Lounge","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"463","Name":"Free Toiletries （less 6）","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"495","Name":"Shared Hairdryer","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"460","Name":"Jacuzzi","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"459","Name":"Shared Toilet ","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"466","Name":"City View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"473","Name":"Sea View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"472","Name":"River View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"470","Name":"Mountain View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"468","Name":"Lake View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"467","Name":"Garden View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"474","Name":"View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"465","Name":"Balcony  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"421","Name":"Fax","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"461","Name":"Bathroom TV","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"462","Name":"Bathroom Phone","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"405","Name":"Sofa","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"416","Name":"Stereo","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"420","Name":"DVD Player","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"493","Name":"Free Newspaper","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"412","Name":"Turndown Service","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"410","Name":"Electronic Scale","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"494","Name":"High Speed Internet","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"424","Name":"Pay TV (on demand)","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"429","Name":"Telephone","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"653","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"654","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"722","Name":"SmartLock","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"833","Name":"Washlet","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"855","Name":"Air Cleaner","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"856","Name":"Air Detector","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"896","Name":"Fast food","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"897","Name":"Smoke Lampblack Machine","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"898","Name":"Condiment","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"1096","Name":"Facilities list (CN)","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"1097","Name":"Tea bag","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"1175","Name":"Smart RCU","Status":"1"}],"CategoryName":"Services & Others"}],"Pictures":[{"Type":"6","URL":"http://dimg04.c-ctrip.com/images//200714000000xgnmhC4D5_R_550_412.jpg"},{"Type":"6","URL":"http://dimg04.c-ctrip.com/images//200l170000010rys8E955_R_550_412.jpg"},{"Type":"6","URL":"http://dimg04.c-ctrip.com/images//2004160000010b4l9AB95_R_550_412.jpg"},{"Type":"6","URL":"http://dimg04.c-ctrip.com/images//200n12000000rzeh31D21_R_550_412.jpg"}],"RoomBedInfos":[{"BedInfo":[{"ID":"365","Name":"Double Bed","NumberOfBeds":"1","BedWidth":"1.5"}],"ID":"360","Name":"Large Bed"},{"BedInfo":[{"ID":"365","Name":"Double Bed","NumberOfBeds":"2","BedWidth":"1.5"}],"ID":"361","Name":"Two Beds"}],"RoomTypeID":55189414,"StandardRoomType":"","RoomQuantity":4,"MaxOccupancy":2,"AreaRange":"15","FloorRange":"1","HasWindow":"4","BathRoomType":"Unknown"},"RoomInfos":[{"ApplicabilityInfo":{"Applicability":"10000000"},"Smoking":{"IsAllowSmoking":"1"},"BroadNet":{"HasBroadnet":0,"HasWirelessBroadnet":"F","WirelessBroadnetRoom":"1","WirelessBroadnetFee":"0","HasWiredBroadnet":"F","WiredBroadnetRoom":"1","WiredBroadnetFee":"0","WirelessBroadnet":0,"WiredBroadnet":0},"RoomBedInfos":[{"BedInfo":[{"ID":"365","Name":"Double Bed","NumberOfBeds":"1","BedWidth":"1.5"}],"ID":"360","Name":"Large Bed"},{"BedInfo":[{"ID":"365","Name":"Double Bed","NumberOfBeds":"2","BedWidth":"1.5"}],"ID":"361","Name":"Two Beds"}],"RoomFGToPPInfo":{"CanFGToPP":false},"ChannelLimit":{"IsApp":true,"IsWeb":true,"IsWeChat":true},"ExpressCheckout":{"DepositAmount":[{"Amount":100}],"IsSupported":true,"DepositRatio":"-2.0","AuditTime":"37"},"RoomTags":[{"Code":"RateCodeID","Name":"RateCodeID","Value":"1224763"},{"Code":"IsAgent","Name":"IsAgent","Value":"F"},{"Code":"VendorID","Name":"VendorID","Value":"0"},{"Code":"GenderType","Name":"GenderType","Value":"1"},{"Code":"IsStandardPrice","Name":"","Value":"true"}],"BookingRules":[{}],"RoomID":167505917,"PayType":"FG","RoomQuantity":4,"MaxOccupancy":2,"AreaRange":"15","FloorRange":"1","HasWindow":"4","IsHourlyRoom":false,"IsFromAPI":false,"IsShowAgencyTag":true,"InvoiceType":2,"InvoiceMode":"0","IsSupportSpecialInvoice":"true","ReceiveTextRemark":false},{"ApplicabilityInfo":{"Applicability":"10000000"},"Smoking":{"IsAllowSmoking":"1"},"BroadNet":{"HasBroadnet":0,"HasWirelessBroadnet":"F","WirelessBroadnetRoom":"1","WirelessBroadnetFee":"0","HasWiredBroadnet":"F","WiredBroadnetRoom":"1","WiredBroadnetFee":"0","WirelessBroadnet":0,"WiredBroadnet":0},"RoomBedInfos":[{"BedInfo":[{"ID":"365","Name":"Double Bed","NumberOfBeds":"1","BedWidth":"1.5"}],"ID":"360","Name":"Large Bed"},{"BedInfo":[{"ID":"365","Name":"Double Bed","NumberOfBeds":"2","BedWidth":"1.5"}],"ID":"361","Name":"Two Beds"}],"RoomFGToPPInfo":{"CanFGToPP":false},"ChannelLimit":{"IsApp":true,"IsWeb":true,"IsWeChat":true},"ExpressCheckout":{"DepositAmount":[{"Amount":100}],"IsSupported":true,"DepositRatio":"-2.0","AuditTime":"37"},"RoomTags":[{"Code":"RateCodeID","Name":"RateCodeID","Value":"12468226"},{"Code":"IsAgent","Name":"IsAgent","Value":"F"},{"Code":"VendorID","Name":"VendorID","Value":"0"},{"Code":"GenderType","Name":"GenderType","Value":"1"},{"Code":"IsStandardPrice","Name":"","Value":"true"},{"Code":"Promotion","Name":"SpecialPriceForTonight","Value":"6","Desc":""}],"BookingRules":[{"TimeLimitInfo":[{"DateRestrictions":[{"Scope":"Booking","DateType":"DateTime","Start":"2014-01-01 00:00:00","End":"2999-12-31 00:00:00","IsIntraday":"T"},{"Scope":"Booking","DateType":"Time","Start":"16:00:00","End":"30:00:00","IsIntraday":"T"},{"Scope":"StayIn","DateType":"Time","Start":"16:00:00","End":"30:00:00","IsIntraday":"T"}]}]}],"Descriptions":[{"Text":"2014-01-012999-12-3116:0023:59,！","Category":"Promotion"}],"RoomID":183872842,"PayType":"FG","RoomQuantity":4,"MaxOccupancy":2,"AreaRange":"15","FloorRange":"1","HasWindow":"4","IsHourlyRoom":false,"IsFromAPI":false,"IsShowAgencyTag":true,"InvoiceType":2,"InvoiceMode":"0","IsSupportSpecialInvoice":"true","ReceiveTextRemark":false},{"ApplicabilityInfo":{"Applicability":"10000000"},"Smoking":{"IsAllowSmoking":"1"},"BroadNet":{"HasBroadnet":0,"HasWirelessBroadnet":"F","WirelessBroadnetRoom":"1","WirelessBroadnetFee":"0","HasWiredBroadnet":"F","WiredBroadnetRoom":"1","WiredBroadnetFee":"0","WirelessBroadnet":0,"WiredBroadnet":0},"RoomBedInfos":[{"BedInfo":[{"ID":"365","Name":"Double Bed","NumberOfBeds":"1","BedWidth":"1.5"}],"ID":"360","Name":"Large Bed"},{"BedInfo":[{"ID":"365","Name":"Double Bed","NumberOfBeds":"2","BedWidth":"1.5"}],"ID":"361","Name":"Two Beds"}],"RoomFGToPPInfo":{"CanFGToPP":true},"ChannelLimit":{"IsApp":true,"IsWeb":true,"IsWeChat":true},"ExpressCheckout":{"DepositAmount":[{"Amount":100}],"IsSupported":true,"DepositRatio":"-2.0","AuditTime":"37"},"RoomTags":[{"Code":"RateCodeID","Name":"RateCodeID","Value":"31692444"},{"Code":"IsAgent","Name":"IsAgent","Value":"F"},{"Code":"VendorID","Name":"VendorID","Value":"0"},{"Code":"GenderType","Name":"GenderType","Value":"1"},{"Code":"IsStandardPrice","Name":"","Value":"true"}],"BookingRules":[{}],"RoomID":211551414,"PayType":"FG","RoomQuantity":4,"MaxOccupancy":2,"AreaRange":"15","FloorRange":"1","HasWindow":"4","IsHourlyRoom":false,"IsFromAPI":false,"IsShowAgencyTag":true,"InvoiceType":2,"InvoiceMode":"0","IsSupportSpecialInvoice":"true","ReceiveTextRemark":false}]},{"RoomTypeInfo":{"BroadNet":{"HasBroadnet":1,"HasWirelessBroadnet":"T","WirelessBroadnetRoom":"1","WirelessBroadnetFee":"0","HasWiredBroadnet":"F","WiredBroadnetRoom":"1","WiredBroadnetFee":"0","WirelessBroadnet":2,"WiredBroadnet":0},"ChildLimit":{"MaxOccupancy":1,"MaxAge":12,"MinAge":-1},"Facilities":[{"FacilityItem":[{"ID":"415","Name":"International Direct-dial","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"447","Name":"Slippers  ","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"384","Name":"Umbrella","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"392","Name":"Desk","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"448","Name":"Bathroom Magnifying Make-up Mirrior","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"385","Name":"Hot Water （24hrs）","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"433","Name":"Electric Kettle","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"434","Name":"Coffee/Tea Pot","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"435","Name":"Free Bottle of Water","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"437","Name":"Mini Bar","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"393","Name":"Ironing Facilities","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"436","Name":"Mini Refrigerator","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"449","Name":"Bathrobe  ","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"387","Name":"110V Socket","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"450","Name":"Bathtub","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"451","Name":"Separate Shower Room","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"394","Name":"In-Room Safety","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"388","Name":"Air conditioning","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"453","Name":"Electric Water Heater","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"481","Name":"Movie（charged）","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"418","Name":"Cable Channels ","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"426","Name":"Satellite Channels ","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"428","Name":"TV","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"423","Name":"Game Consoles","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"427","Name":"3D TV","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"417","Name":"CD Player  ","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"419","Name":"Computer /Laptop ","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"430","Name":"Video","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"431","Name":"iPad","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"425","Name":"Radio","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"432","Name":"iPod Docking Station","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"438","Name":"Coffee Machine","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"446","Name":"Toaster","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"444","Name":"Oven","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"445","Name":"Stove ","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"402","Name":"Fireplace  ","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"439","Name":"Dining Area","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"441","Name":"Kitchen","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"442","Name":"Kitchenware ","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"401","Name":"Fan  ","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"396","Name":"Electric Blankets  ","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"399","Name":"Private Entrance  ","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"443","Name":"Microwave","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"397","Name":"Mosquito Net ","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"440","Name":"Dishwasher","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"407","Name":"Washing Machine","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"406","Name":"Wardrobe/Closet ","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"404","Name":"Connecting Room","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"478","Name":"Terrace","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"403","Name":"Heating  ","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"475","Name":"Alarm Clock","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"484","Name":"Sewing Kit","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"408","Name":"220V Socket","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"487","Name":"Shade Curtain","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"488","Name":"Automatic curtain","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"400","Name":"Bed (over 200 cm length)","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"492","Name":"Spare Bedding","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"490","Name":"Bedding: Quilt","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"491","Name":"Bedding: Blankets or Duvet","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"479","Name":"Private Hot Spring","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"398","Name":"Private Swimming Pool","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"482","Name":"Voice Message","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"483","Name":"Welcome Gift","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"476","Name":"Executive Lounge","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"463","Name":"Free Toiletries （less 6）","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"495","Name":"Shared Hairdryer","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"460","Name":"Jacuzzi","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"459","Name":"Shared Toilet ","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"466","Name":"City View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"473","Name":"Sea View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"472","Name":"River View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"470","Name":"Mountain View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"468","Name":"Lake View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"467","Name":"Garden View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"474","Name":"View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"465","Name":"Balcony  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"421","Name":"Fax","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"461","Name":"Bathroom TV","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"462","Name":"Bathroom Phone","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"405","Name":"Sofa","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"416","Name":"Stereo","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"420","Name":"DVD Player","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"493","Name":"Free Newspaper","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"412","Name":"Turndown Service","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"410","Name":"Electronic Scale","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"494","Name":"High Speed Internet","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"424","Name":"Pay TV (on demand)","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"429","Name":"Telephone","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"653","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"654","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"722","Name":"SmartLock","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"833","Name":"Washlet","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"855","Name":"Air Cleaner","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"856","Name":"Air Detector","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"896","Name":"Fast food","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"897","Name":"Smoke Lampblack Machine","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"898","Name":"Condiment","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"1096","Name":"Facilities list (CN)","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"1097","Name":"Tea bag","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"1175","Name":"Smart RCU","Status":"1"}],"CategoryName":"Services & Others"}],"RoomBedInfos":[{"BedInfo":[{"ID":"365","Name":"Double Bed","NumberOfBeds":"1","BedWidth":"1.43"}],"ID":"360","Name":"Large Bed"}],"RoomTypeID":118098348,"StandardRoomType":"","RoomQuantity":1,"MaxOccupancy":2,"AreaRange":"15","FloorRange":"1","HasWindow":"2","BathRoomType":"Unknown"},"RoomInfos":[{"ApplicabilityInfo":{"Applicability":"10000000"},"Smoking":{"IsAllowSmoking":"1"},"BroadNet":{"HasBroadnet":1,"HasWirelessBroadnet":"T","WirelessBroadnetRoom":"1","WirelessBroadnetFee":"0","HasWiredBroadnet":"F","WiredBroadnetRoom":"1","WiredBroadnetFee":"0","WirelessBroadnet":2,"WiredBroadnet":0},"RoomBedInfos":[{"BedInfo":[{"ID":"365","Name":"Double Bed","NumberOfBeds":"1","BedWidth":"1.43"}],"ID":"360","Name":"Large Bed"}],"RoomFGToPPInfo":{"CanFGToPP":true},"ChannelLimit":{"IsApp":true,"IsWeb":true,"IsWeChat":true},"ExpressCheckout":{"DepositAmount":[{"Amount":100}],"IsSupported":true,"DepositRatio":"-2.0","AuditTime":"37"},"RoomTags":[{"Code":"RateCodeID","Name":"RateCodeID","Value":"3331272"},{"Code":"IsAgent","Name":"IsAgent","Value":"F"},{"Code":"VendorID","Name":"VendorID","Value":"0"},{"Code":"GenderType","Name":"GenderType","Value":"1"},{"Code":"IsStandardPrice","Name":"","Value":"true"}],"BookingRules":[{}],"RoomID":313686648,"PayType":"FG","RoomQuantity":1,"MaxOccupancy":2,"AreaRange":"15","FloorRange":"1","HasWindow":"2","IsHourlyRoom":false,"IsFromAPI":false,"IsShowAgencyTag":true,"InvoiceType":2,"InvoiceMode":"0","IsSupportSpecialInvoice":"true","ReceiveTextRemark":false}]}]
     */

    private RoomDetailResponseStatus ResponseStatus;
    private List<RoomStaticInfosBean> RoomStaticInfos;

    public RoomDetailResponseStatus getResponseStatus() {
        return ResponseStatus;
    }

    public void setResponseStatus(RoomDetailResponseStatus ResponseStatus) {
        this.ResponseStatus = ResponseStatus;
    }

    public List<RoomStaticInfosBean> getRoomStaticInfos() {
        return RoomStaticInfos;
    }

    public void setRoomStaticInfos(List<RoomStaticInfosBean> RoomStaticInfos) {
        this.RoomStaticInfos = RoomStaticInfos;
    }

    public static class RoomDetailResponseStatus {
        /**
         * Timestamp : 2019-08-05T10:47:51.813+08:00
         * Ack : Success
         * Errors : []
         * Extension : []
         */

        private String Timestamp;
        private String Ack;
        private List<?> Errors;
        private List<?> Extension;

        public String getTimestamp() {
            return Timestamp;
        }

        public void setTimestamp(String Timestamp) {
            this.Timestamp = Timestamp;
        }

        public String getAck() {
            return Ack;
        }

        public void setAck(String Ack) {
            this.Ack = Ack;
        }

        public List<?> getErrors() {
            return Errors;
        }

        public void setErrors(List<?> Errors) {
            this.Errors = Errors;
        }

        public List<?> getExtension() {
            return Extension;
        }

        public void setExtension(List<?> Extension) {
            this.Extension = Extension;
        }
    }

    public static class RoomStaticInfosBean {
        /**
         * RoomTypeInfo : {"BroadNet":{"HasBroadnet":0,"HasWirelessBroadnet":"F","WirelessBroadnetRoom":"1","WirelessBroadnetFee":"0","HasWiredBroadnet":"F","WiredBroadnetRoom":"1","WiredBroadnetFee":"0","WirelessBroadnet":0,"WiredBroadnet":0},"ChildLimit":{"MaxOccupancy":0,"MaxAge":3,"MinAge":0},"Facilities":[{"FacilityItem":[{"ID":"415","Name":"International Direct-dial","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"447","Name":"Slippers  ","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"384","Name":"Umbrella","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"392","Name":"Desk","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"448","Name":"Bathroom Magnifying Make-up Mirrior","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"385","Name":"Hot Water （24hrs）","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"433","Name":"Electric Kettle","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"434","Name":"Coffee/Tea Pot","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"435","Name":"Free Bottle of Water","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"437","Name":"Mini Bar","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"393","Name":"Ironing Facilities","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"436","Name":"Mini Refrigerator","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"449","Name":"Bathrobe  ","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"387","Name":"110V Socket","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"450","Name":"Bathtub","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"451","Name":"Separate Shower Room","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"394","Name":"In-Room Safety","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"388","Name":"Air conditioning","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"453","Name":"Electric Water Heater","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"481","Name":"Movie（charged）","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"418","Name":"Cable Channels ","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"426","Name":"Satellite Channels ","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"428","Name":"TV","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"423","Name":"Game Consoles","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"427","Name":"3D TV","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"417","Name":"CD Player  ","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"419","Name":"Computer /Laptop ","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"430","Name":"Video","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"431","Name":"iPad","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"425","Name":"Radio","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"432","Name":"iPod Docking Station","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"438","Name":"Coffee Machine","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"446","Name":"Toaster","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"444","Name":"Oven","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"445","Name":"Stove ","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"402","Name":"Fireplace  ","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"439","Name":"Dining Area","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"441","Name":"Kitchen","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"442","Name":"Kitchenware ","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"401","Name":"Fan  ","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"396","Name":"Electric Blankets  ","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"399","Name":"Private Entrance  ","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"443","Name":"Microwave","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"397","Name":"Mosquito Net ","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"440","Name":"Dishwasher","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"407","Name":"Washing Machine","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"406","Name":"Wardrobe/Closet ","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"404","Name":"Connecting Room","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"478","Name":"Terrace","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"403","Name":"Heating  ","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"475","Name":"Alarm Clock","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"484","Name":"Sewing Kit","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"408","Name":"220V Socket","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"487","Name":"Shade Curtain","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"488","Name":"Automatic curtain","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"400","Name":"Bed (over 200 cm length)","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"492","Name":"Spare Bedding","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"490","Name":"Bedding: Quilt","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"491","Name":"Bedding: Blankets or Duvet","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"479","Name":"Private Hot Spring","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"398","Name":"Private Swimming Pool","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"482","Name":"Voice Message","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"483","Name":"Welcome Gift","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"476","Name":"Executive Lounge","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"463","Name":"Free Toiletries （less 6）","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"495","Name":"Shared Hairdryer","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"460","Name":"Jacuzzi","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"459","Name":"Shared Toilet ","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"466","Name":"City View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"473","Name":"Sea View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"472","Name":"River View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"470","Name":"Mountain View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"468","Name":"Lake View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"467","Name":"Garden View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"474","Name":"View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"465","Name":"Balcony  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"421","Name":"Fax","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"461","Name":"Bathroom TV","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"462","Name":"Bathroom Phone","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"405","Name":"Sofa","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"416","Name":"Stereo","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"420","Name":"DVD Player","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"493","Name":"Free Newspaper","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"412","Name":"Turndown Service","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"410","Name":"Electronic Scale","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"494","Name":"High Speed Internet","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"424","Name":"Pay TV (on demand)","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"429","Name":"Telephone","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"653","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"654","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"722","Name":"SmartLock","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"833","Name":"Washlet","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"855","Name":"Air Cleaner","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"856","Name":"Air Detector","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"896","Name":"Fast food","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"897","Name":"Smoke Lampblack Machine","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"898","Name":"Condiment","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"1096","Name":"Facilities list (CN)","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"1097","Name":"Tea bag","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"1175","Name":"Smart RCU","Status":"1"}],"CategoryName":"Services & Others"}],"Pictures":[{"Type":"6","URL":"http://dimg04.c-ctrip.com/images//200d16000000zd0kfC615_R_550_412.jpg"},{"Type":"6","URL":"http://dimg04.c-ctrip.com/images//200c16000000zh4kz0BEB_R_550_412.jpg"}],"Descriptions":[{"Text":"","Category":"1"}],"RoomBedInfos":[{"BedInfo":[{"ID":"365","Name":"Double Bed","NumberOfBeds":"1","BedWidth":"1.45"}],"ID":"360","Name":"Large Bed"}],"RoomTypeID":1977056,"StandardRoomType":"","RoomQuantity":25,"MaxOccupancy":5,"AreaRange":"25","FloorRange":"2-6","HasWindow":"2","ExtraBedFee":"50.0000","BathRoomType":"Unknown"}
         * RoomInfos : [{"ApplicabilityInfo":{"Applicability":"10000000"},"Smoking":{"IsAllowSmoking":"-100"},"BroadNet":{"HasBroadnet":0,"HasWirelessBroadnet":"F","WirelessBroadnetRoom":"1","WirelessBroadnetFee":"0","HasWiredBroadnet":"F","WiredBroadnetRoom":"1","WiredBroadnetFee":"0","WirelessBroadnet":0,"WiredBroadnet":0},"RoomBedInfos":[{"BedInfo":[{"ID":"365","Name":"Double Bed","NumberOfBeds":"1","BedWidth":"1.45"}],"ID":"360","Name":"Large Bed"}],"RoomFGToPPInfo":{"CanFGToPP":false},"ChannelLimit":{"IsApp":true,"IsWeb":true,"IsWeChat":true},"ExpressCheckout":{"DepositAmount":[{"Amount":100}],"IsSupported":true,"DepositRatio":"-2.0","AuditTime":"37"},"RoomTags":[{"Code":"RateCodeID","Name":"RateCodeID","Value":"888737"},{"Code":"IsAgent","Name":"IsAgent","Value":"F"},{"Code":"VendorID","Name":"VendorID","Value":"0"},{"Code":"GenderType","Name":"GenderType","Value":"1"},{"Code":"IsStandardPrice","Name":"","Value":"true"},{"Code":"Promotion","Name":"SpecialPriceForTonight","Value":"6","Desc":""}],"BookingRules":[{"TimeLimitInfo":[{"DateRestrictions":[{"Scope":"Booking","DateType":"DateTime","Start":"2016-08-24 00:00:00","End":"2099-12-31 00:00:00","IsIntraday":"T"},{"Scope":"Booking","DateType":"Time","Start":"18:00:00","End":"30:00:00","IsIntraday":"T"},{"Scope":"StayIn","DateType":"Time","Start":"18:00:00","End":"30:00:00","IsIntraday":"T"}]}]}],"Descriptions":[{"Text":"2016-08-242099-12-3118:0023:59,！","Category":"Promotion"}],"RoomID":30206159,"PayType":"FG","RoomQuantity":25,"MaxOccupancy":5,"AreaRange":"25","FloorRange":"2-6","HasWindow":"2","ExtraBedFee":"Unknown","IsHourlyRoom":false,"IsFromAPI":false,"IsShowAgencyTag":true,"InvoiceType":2,"InvoiceMode":"0","IsSupportSpecialInvoice":"true","ReceiveTextRemark":false},{"ApplicabilityInfo":{"Applicability":"10000000"},"Smoking":{"IsAllowSmoking":"-100"},"BroadNet":{"HasBroadnet":0,"HasWirelessBroadnet":"F","WirelessBroadnetRoom":"1","WirelessBroadnetFee":"0","HasWiredBroadnet":"F","WiredBroadnetRoom":"1","WiredBroadnetFee":"0","WirelessBroadnet":0,"WiredBroadnet":0},"RoomBedInfos":[{"BedInfo":[{"ID":"365","Name":"Double Bed","NumberOfBeds":"1","BedWidth":"1.45"}],"ID":"360","Name":"Large Bed"}],"RoomFGToPPInfo":{"CanFGToPP":true},"ChannelLimit":{"IsApp":true,"IsWeb":true,"IsWeChat":true},"ExpressCheckout":{"DepositAmount":[{"Amount":100}],"IsSupported":true,"DepositRatio":"-2.0","AuditTime":"37"},"RoomTags":[{"Code":"RateCodeID","Name":"RateCodeID","Value":"12468455"},{"Code":"IsAgent","Name":"IsAgent","Value":"F"},{"Code":"VendorID","Name":"VendorID","Value":"0"},{"Code":"GenderType","Name":"GenderType","Value":"1"}],"BookingRules":[{}],"RoomID":357962984,"PayType":"PP","RoomQuantity":25,"MaxOccupancy":5,"AreaRange":"25","FloorRange":"2-6","HasWindow":"2","ExtraBedFee":"Unknown","IsHourlyRoom":false,"IsFromAPI":false,"IsShowAgencyTag":true,"InvoiceType":2,"InvoiceMode":"0","IsSupportSpecialInvoice":"true","ReceiveTextRemark":false},{"ApplicabilityInfo":{"Applicability":"10000000"},"Smoking":{"IsAllowSmoking":"-100"},"BroadNet":{"HasBroadnet":0,"HasWirelessBroadnet":"F","WirelessBroadnetRoom":"1","WirelessBroadnetFee":"0","HasWiredBroadnet":"F","WiredBroadnetRoom":"1","WiredBroadnetFee":"0","WirelessBroadnet":0,"WiredBroadnet":0},"RoomBedInfos":[{"BedInfo":[{"ID":"365","Name":"Double Bed","NumberOfBeds":"1","BedWidth":"1.45"}],"ID":"360","Name":"Large Bed"}],"RoomFGToPPInfo":{"CanFGToPP":true},"ChannelLimit":{"IsApp":true,"IsWeb":true,"IsWeChat":true},"ExpressCheckout":{"DepositAmount":[{"Amount":100}],"IsSupported":true,"DepositRatio":"-2.0","AuditTime":"37"},"RoomTags":[{"Code":"RateCodeID","Name":"RateCodeID","Value":"2740578"},{"Code":"IsAgent","Name":"IsAgent","Value":"F"},{"Code":"VendorID","Name":"VendorID","Value":"0"},{"Code":"GenderType","Name":"GenderType","Value":"1"}],"BookingRules":[{}],"RoomID":358383484,"PayType":"PP","RoomQuantity":25,"MaxOccupancy":5,"AreaRange":"25","FloorRange":"2-6","HasWindow":"2","ExtraBedFee":"Unknown","IsHourlyRoom":false,"IsFromAPI":false,"IsShowAgencyTag":true,"InvoiceType":2,"InvoiceMode":"0","IsSupportSpecialInvoice":"true","ReceiveTextRemark":false},{"ApplicabilityInfo":{"Applicability":"10000000"},"Smoking":{"IsAllowSmoking":"-100"},"BroadNet":{"HasBroadnet":0,"HasWirelessBroadnet":"F","WirelessBroadnetRoom":"1","WirelessBroadnetFee":"0","HasWiredBroadnet":"F","WiredBroadnetRoom":"1","WiredBroadnetFee":"0","WirelessBroadnet":0,"WiredBroadnet":0},"RoomBedInfos":[{"BedInfo":[{"ID":"365","Name":"Double Bed","NumberOfBeds":"1","BedWidth":"1.45"}],"ID":"360","Name":"Large Bed"}],"RoomFGToPPInfo":{"CanFGToPP":true},"ChannelLimit":{"IsApp":true,"IsWeb":true,"IsWeChat":true},"ExpressCheckout":{"DepositAmount":[{"Amount":100}],"IsSupported":true,"DepositRatio":"-2.0","AuditTime":"37"},"RoomTags":[{"Code":"RateCodeID","Name":"RateCodeID","Value":"7240214"},{"Code":"IsAgent","Name":"IsAgent","Value":"F"},{"Code":"VendorID","Name":"VendorID","Value":"0"},{"Code":"GenderType","Name":"GenderType","Value":"1"}],"BookingRules":[{}],"RoomID":358383485,"PayType":"PP","RoomQuantity":25,"MaxOccupancy":5,"AreaRange":"25","FloorRange":"2-6","HasWindow":"2","ExtraBedFee":"Unknown","IsHourlyRoom":false,"IsFromAPI":false,"IsShowAgencyTag":true,"InvoiceType":2,"InvoiceMode":"0","IsSupportSpecialInvoice":"true","ReceiveTextRemark":false}]
         */

        private RoomTypeInfoBean RoomTypeInfo;
        private List<RoomInfosBean> RoomInfos;

        public RoomTypeInfoBean getRoomTypeInfo() {
            return RoomTypeInfo;
        }

        public void setRoomTypeInfo(RoomTypeInfoBean RoomTypeInfo) {
            this.RoomTypeInfo = RoomTypeInfo;
        }

        public List<RoomInfosBean> getRoomInfos() {
            return RoomInfos;
        }

        public void setRoomInfos(List<RoomInfosBean> RoomInfos) {
            this.RoomInfos = RoomInfos;
        }

        public static class RoomTypeInfoBean {
            /**
             * BroadNet : {"HasBroadnet":0,"HasWirelessBroadnet":"F","WirelessBroadnetRoom":"1","WirelessBroadnetFee":"0","HasWiredBroadnet":"F","WiredBroadnetRoom":"1","WiredBroadnetFee":"0","WirelessBroadnet":0,"WiredBroadnet":0}
             * ChildLimit : {"MaxOccupancy":0,"MaxAge":3,"MinAge":0}
             * Facilities : [{"FacilityItem":[{"ID":"415","Name":"International Direct-dial","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"447","Name":"Slippers  ","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"384","Name":"Umbrella","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"392","Name":"Desk","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"448","Name":"Bathroom Magnifying Make-up Mirrior","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"385","Name":"Hot Water （24hrs）","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"433","Name":"Electric Kettle","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"434","Name":"Coffee/Tea Pot","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"435","Name":"Free Bottle of Water","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"437","Name":"Mini Bar","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"393","Name":"Ironing Facilities","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"436","Name":"Mini Refrigerator","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"449","Name":"Bathrobe  ","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"387","Name":"110V Socket","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"450","Name":"Bathtub","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"451","Name":"Separate Shower Room","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"394","Name":"In-Room Safety","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"388","Name":"Air conditioning","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"453","Name":"Electric Water Heater","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"481","Name":"Movie（charged）","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"418","Name":"Cable Channels ","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"426","Name":"Satellite Channels ","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"428","Name":"TV","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"423","Name":"Game Consoles","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"427","Name":"3D TV","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"417","Name":"CD Player  ","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"419","Name":"Computer /Laptop ","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"430","Name":"Video","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"431","Name":"iPad","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"425","Name":"Radio","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"432","Name":"iPod Docking Station","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"438","Name":"Coffee Machine","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"446","Name":"Toaster","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"444","Name":"Oven","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"445","Name":"Stove ","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"402","Name":"Fireplace  ","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"439","Name":"Dining Area","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"441","Name":"Kitchen","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"442","Name":"Kitchenware ","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"401","Name":"Fan  ","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"396","Name":"Electric Blankets  ","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"399","Name":"Private Entrance  ","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"443","Name":"Microwave","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"397","Name":"Mosquito Net ","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"440","Name":"Dishwasher","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"407","Name":"Washing Machine","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"406","Name":"Wardrobe/Closet ","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"404","Name":"Connecting Room","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"478","Name":"Terrace","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"403","Name":"Heating  ","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"475","Name":"Alarm Clock","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"484","Name":"Sewing Kit","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"408","Name":"220V Socket","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"487","Name":"Shade Curtain","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"488","Name":"Automatic curtain","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"400","Name":"Bed (over 200 cm length)","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"492","Name":"Spare Bedding","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"490","Name":"Bedding: Quilt","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"491","Name":"Bedding: Blankets or Duvet","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"479","Name":"Private Hot Spring","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"398","Name":"Private Swimming Pool","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"482","Name":"Voice Message","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"483","Name":"Welcome Gift","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"476","Name":"Executive Lounge","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"463","Name":"Free Toiletries （less 6）","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"495","Name":"Shared Hairdryer","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"460","Name":"Jacuzzi","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"459","Name":"Shared Toilet ","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"466","Name":"City View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"473","Name":"Sea View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"472","Name":"River View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"470","Name":"Mountain View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"468","Name":"Lake View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"467","Name":"Garden View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"474","Name":"View  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"465","Name":"Balcony  ","Status":"1"}],"CategoryName":"Outdoors/Views"},{"FacilityItem":[{"ID":"421","Name":"Fax","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"461","Name":"Bathroom TV","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"462","Name":"Bathroom Phone","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"405","Name":"Sofa","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"416","Name":"Stereo","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"420","Name":"DVD Player","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"493","Name":"Free Newspaper","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"412","Name":"Turndown Service","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"410","Name":"Electronic Scale","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"494","Name":"High Speed Internet","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"424","Name":"Pay TV (on demand)","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"429","Name":"Telephone","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"653","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"654","Status":"1"}],"CategoryName":"Convenient facilities"},{"FacilityItem":[{"ID":"722","Name":"SmartLock","Status":"1"}],"CategoryName":"Media/Technology"},{"FacilityItem":[{"ID":"833","Name":"Washlet","Status":"1"}],"CategoryName":"Bathroom"},{"FacilityItem":[{"ID":"855","Name":"Air Cleaner","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"856","Name":"Air Detector","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"896","Name":"Fast food","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"897","Name":"Smoke Lampblack Machine","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"898","Name":"Condiment","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"1096","Name":"Facilities list (CN)","Status":"1"}],"CategoryName":"Services & Others"},{"FacilityItem":[{"ID":"1097","Name":"Tea bag","Status":"1"}],"CategoryName":"Food/Beverages"},{"FacilityItem":[{"ID":"1175","Name":"Smart RCU","Status":"1"}],"CategoryName":"Services & Others"}]
             * Pictures : [{"Type":"6","URL":"http://dimg04.c-ctrip.com/images//200d16000000zd0kfC615_R_550_412.jpg"},{"Type":"6","URL":"http://dimg04.c-ctrip.com/images//200c16000000zh4kz0BEB_R_550_412.jpg"}]
             * Descriptions : [{"Text":"","Category":"1"}]
             * RoomBedInfos : [{"BedInfo":[{"ID":"365","Name":"Double Bed","NumberOfBeds":"1","BedWidth":"1.45"}],"ID":"360","Name":"Large Bed"}]
             * RoomTypeID : 1977056
             * StandardRoomType :
             * RoomQuantity : 25
             * MaxOccupancy : 5
             * AreaRange : 25
             * FloorRange : 2-6
             * HasWindow : 2
             * ExtraBedFee : 50.0000
             * BathRoomType : Unknown
             */

            private BroadNetBean BroadNet;
            private ChildLimitBean ChildLimit;
            private int RoomTypeID;
            private String StandardRoomType;
            private int RoomQuantity;
            private int MaxOccupancy;
            private String AreaRange;
            private String FloorRange;
            private String HasWindow;
            private String ExtraBedFee;
            private String BathRoomType;
            private List<Facilities> Facilities;
            private List<Pictures> Pictures;
            private List<Descriptions> Descriptions;
            private List<RoomBedInfosBean> RoomBedInfos;

            public BroadNetBean getBroadNet() {
                return BroadNet;
            }

            public void setBroadNet(BroadNetBean BroadNet) {
                this.BroadNet = BroadNet;
            }

            public ChildLimitBean getChildLimit() {
                return ChildLimit;
            }

            public void setChildLimit(ChildLimitBean ChildLimit) {
                this.ChildLimit = ChildLimit;
            }

            public int getRoomTypeID() {
                return RoomTypeID;
            }

            public void setRoomTypeID(int RoomTypeID) {
                this.RoomTypeID = RoomTypeID;
            }

            public String getStandardRoomType() {
                return StandardRoomType;
            }

            public void setStandardRoomType(String StandardRoomType) {
                this.StandardRoomType = StandardRoomType;
            }

            public int getRoomQuantity() {
                return RoomQuantity;
            }

            public void setRoomQuantity(int RoomQuantity) {
                this.RoomQuantity = RoomQuantity;
            }

            public int getMaxOccupancy() {
                return MaxOccupancy;
            }

            public void setMaxOccupancy(int MaxOccupancy) {
                this.MaxOccupancy = MaxOccupancy;
            }

            public String getAreaRange() {
                return AreaRange;
            }

            public void setAreaRange(String AreaRange) {
                this.AreaRange = AreaRange;
            }

            public String getFloorRange() {
                return FloorRange;
            }

            public void setFloorRange(String FloorRange) {
                this.FloorRange = FloorRange;
            }

            public String getHasWindow() {
                return HasWindow;
            }

            public void setHasWindow(String HasWindow) {
                this.HasWindow = HasWindow;
            }

            public String getExtraBedFee() {
                return ExtraBedFee;
            }

            public void setExtraBedFee(String ExtraBedFee) {
                this.ExtraBedFee = ExtraBedFee;
            }

            public String getBathRoomType() {
                return BathRoomType;
            }

            public void setBathRoomType(String BathRoomType) {
                this.BathRoomType = BathRoomType;
            }

            public List<Facilities> getFacilities() {
                return Facilities;
            }

            public void setFacilities(List<Facilities> Facilities) {
                this.Facilities = Facilities;
            }

            public List<Pictures> getPictures() {
                return Pictures;
            }

            public void setPictures(List<Pictures> Pictures) {
                this.Pictures = Pictures;
            }

            public List<Descriptions> getDescriptions() {
                return Descriptions;
            }

            public void setDescriptions(List<Descriptions> Descriptions) {
                this.Descriptions = Descriptions;
            }

            public List<RoomBedInfosBean> getRoomBedInfos() {
                return RoomBedInfos;
            }

            public void setRoomBedInfos(List<RoomBedInfosBean> RoomBedInfos) {
                this.RoomBedInfos = RoomBedInfos;
            }

            public static class BroadNetBean {
                /**
                 * HasBroadnet : 0
                 * HasWirelessBroadnet : F
                 * WirelessBroadnetRoom : 1
                 * WirelessBroadnetFee : 0
                 * HasWiredBroadnet : F
                 * WiredBroadnetRoom : 1
                 * WiredBroadnetFee : 0
                 * WirelessBroadnet : 0
                 * WiredBroadnet : 0
                 */

                private int HasBroadnet;
                private String HasWirelessBroadnet;
                private String WirelessBroadnetRoom;
                private String WirelessBroadnetFee;
                private String HasWiredBroadnet;
                private String WiredBroadnetRoom;
                private String WiredBroadnetFee;
                private int WirelessBroadnet;
                private int WiredBroadnet;

                public int getHasBroadnet() {
                    return HasBroadnet;
                }

                public void setHasBroadnet(int HasBroadnet) {
                    this.HasBroadnet = HasBroadnet;
                }

                public String getHasWirelessBroadnet() {
                    return HasWirelessBroadnet;
                }

                public void setHasWirelessBroadnet(String HasWirelessBroadnet) {
                    this.HasWirelessBroadnet = HasWirelessBroadnet;
                }

                public String getWirelessBroadnetRoom() {
                    return WirelessBroadnetRoom;
                }

                public void setWirelessBroadnetRoom(String WirelessBroadnetRoom) {
                    this.WirelessBroadnetRoom = WirelessBroadnetRoom;
                }

                public String getWirelessBroadnetFee() {
                    return WirelessBroadnetFee;
                }

                public void setWirelessBroadnetFee(String WirelessBroadnetFee) {
                    this.WirelessBroadnetFee = WirelessBroadnetFee;
                }

                public String getHasWiredBroadnet() {
                    return HasWiredBroadnet;
                }

                public void setHasWiredBroadnet(String HasWiredBroadnet) {
                    this.HasWiredBroadnet = HasWiredBroadnet;
                }

                public String getWiredBroadnetRoom() {
                    return WiredBroadnetRoom;
                }

                public void setWiredBroadnetRoom(String WiredBroadnetRoom) {
                    this.WiredBroadnetRoom = WiredBroadnetRoom;
                }

                public String getWiredBroadnetFee() {
                    return WiredBroadnetFee;
                }

                public void setWiredBroadnetFee(String WiredBroadnetFee) {
                    this.WiredBroadnetFee = WiredBroadnetFee;
                }

                public int getWirelessBroadnet() {
                    return WirelessBroadnet;
                }

                public void setWirelessBroadnet(int WirelessBroadnet) {
                    this.WirelessBroadnet = WirelessBroadnet;
                }

                public int getWiredBroadnet() {
                    return WiredBroadnet;
                }

                public void setWiredBroadnet(int WiredBroadnet) {
                    this.WiredBroadnet = WiredBroadnet;
                }
            }

            public static class ChildLimitBean {
                /**
                 * MaxOccupancy : 0
                 * MaxAge : 3
                 * MinAge : 0
                 */

                private int MaxOccupancy;
                private int MaxAge;
                private int MinAge;

                public int getMaxOccupancy() {
                    return MaxOccupancy;
                }

                public void setMaxOccupancy(int MaxOccupancy) {
                    this.MaxOccupancy = MaxOccupancy;
                }

                public int getMaxAge() {
                    return MaxAge;
                }

                public void setMaxAge(int MaxAge) {
                    this.MaxAge = MaxAge;
                }

                public int getMinAge() {
                    return MinAge;
                }

                public void setMinAge(int MinAge) {
                    this.MinAge = MinAge;
                }
            }

            public static class Facilities {
                /**
                 * FacilityItem : [{"ID":"415","Name":"International Direct-dial","Status":"1"}]
                 * CategoryName : Media/Technology
                 */

                private String CategoryName;
                private List<FacilityItem> FacilityItem;

                public String getCategoryName() {
                    return CategoryName;
                }

                public void setCategoryName(String CategoryName) {
                    this.CategoryName = CategoryName;
                }

                public List<FacilityItem> getFacilityItem() {
                    return FacilityItem;
                }

                public void setFacilityItem(List<FacilityItem> FacilityItem) {
                    this.FacilityItem = FacilityItem;
                }

                public static class FacilityItem {
                    /**
                     * ID : 415
                     * Name : International Direct-dial
                     * Status : 1
                     */

                    private String ID;
                    private String Name;
                    private String Status;

                    public String getID() {
                        return ID;
                    }

                    public void setID(String ID) {
                        this.ID = ID;
                    }

                    public String getName() {
                        return Name;
                    }

                    public void setName(String Name) {
                        this.Name = Name;
                    }

                    public String getStatus() {
                        return Status;
                    }

                    public void setStatus(String Status) {
                        this.Status = Status;
                    }
                }
            }

            public static class Pictures {
                /**
                 * Type : 6
                 * URL : http://dimg04.c-ctrip.com/images//200d16000000zd0kfC615_R_550_412.jpg
                 */

                private String Type;
                private String URL;

                public String getType() {
                    return Type;
                }

                public void setType(String Type) {
                    this.Type = Type;
                }

                public String getURL() {
                    return URL;
                }

                public void setURL(String URL) {
                    this.URL = URL;
                }
            }

            public static class Descriptions {
                /**
                 * Text :
                 * Category : 1
                 */

                private String Text;
                private String Category;

                public String getText() {
                    return Text;
                }

                public void setText(String Text) {
                    this.Text = Text;
                }

                public String getCategory() {
                    return Category;
                }

                public void setCategory(String Category) {
                    this.Category = Category;
                }
            }

            public static class RoomBedInfosBean {
                /**
                 * BedInfo : [{"ID":"365","Name":"Double Bed","NumberOfBeds":"1","BedWidth":"1.45"}]
                 * ID : 360
                 * Name : Large Bed
                 */

                private String ID;
                private String Name;
                private List<BedInfoBean> BedInfo;

                public String getID() {
                    return ID;
                }

                public void setID(String ID) {
                    this.ID = ID;
                }

                public String getName() {
                    return Name;
                }

                public void setName(String Name) {
                    this.Name = Name;
                }

                public List<BedInfoBean> getBedInfo() {
                    return BedInfo;
                }

                public void setBedInfo(List<BedInfoBean> BedInfo) {
                    this.BedInfo = BedInfo;
                }

                public static class BedInfoBean {
                    /**
                     * ID : 365
                     * Name : Double Bed
                     * NumberOfBeds : 1
                     * BedWidth : 1.45
                     */

                    private String ID;
                    private String Name;
                    private String NumberOfBeds;
                    private String BedWidth;

                    public String getID() {
                        return ID;
                    }

                    public void setID(String ID) {
                        this.ID = ID;
                    }

                    public String getName() {
                        return Name;
                    }

                    public void setName(String Name) {
                        this.Name = Name;
                    }

                    public String getNumberOfBeds() {
                        return NumberOfBeds;
                    }

                    public void setNumberOfBeds(String NumberOfBeds) {
                        this.NumberOfBeds = NumberOfBeds;
                    }

                    public String getBedWidth() {
                        return BedWidth;
                    }

                    public void setBedWidth(String BedWidth) {
                        this.BedWidth = BedWidth;
                    }
                }
            }
        }

        public static class RoomInfosBean {
            /**
             * ApplicabilityInfo : {"Applicability":"10000000"}
             * Smoking : {"IsAllowSmoking":"-100"}
             * BroadNet : {"HasBroadnet":0,"HasWirelessBroadnet":"F","WirelessBroadnetRoom":"1","WirelessBroadnetFee":"0","HasWiredBroadnet":"F","WiredBroadnetRoom":"1","WiredBroadnetFee":"0","WirelessBroadnet":0,"WiredBroadnet":0}
             * RoomBedInfos : [{"BedInfo":[{"ID":"365","Name":"Double Bed","NumberOfBeds":"1","BedWidth":"1.45"}],"ID":"360","Name":"Large Bed"}]
             * RoomFGToPPInfo : {"CanFGToPP":false}
             * ChannelLimit : {"IsApp":true,"IsWeb":true,"IsWeChat":true}
             * ExpressCheckout : {"DepositAmount":[{"Amount":100}],"IsSupported":true,"DepositRatio":"-2.0","AuditTime":"37"}
             * RoomTags : [{"Code":"RateCodeID","Name":"RateCodeID","Value":"888737"},{"Code":"IsAgent","Name":"IsAgent","Value":"F"},{"Code":"VendorID","Name":"VendorID","Value":"0"},{"Code":"GenderType","Name":"GenderType","Value":"1"},{"Code":"IsStandardPrice","Name":"","Value":"true"},{"Code":"Promotion","Name":"SpecialPriceForTonight","Value":"6","Desc":""}]
             * BookingRules : [{"TimeLimitInfo":[{"DateRestrictions":[{"Scope":"Booking","DateType":"DateTime","Start":"2016-08-24 00:00:00","End":"2099-12-31 00:00:00","IsIntraday":"T"},{"Scope":"Booking","DateType":"Time","Start":"18:00:00","End":"30:00:00","IsIntraday":"T"},{"Scope":"StayIn","DateType":"Time","Start":"18:00:00","End":"30:00:00","IsIntraday":"T"}]}]}]
             * Descriptions : [{"Text":"2016-08-242099-12-3118:0023:59,！","Category":"Promotion"}]
             * RoomID : 30206159
             * PayType : FG
             * RoomQuantity : 25
             * MaxOccupancy : 5
             * AreaRange : 25
             * FloorRange : 2-6
             * HasWindow : 2
             * ExtraBedFee : Unknown
             * IsHourlyRoom : false
             * IsFromAPI : false
             * IsShowAgencyTag : true
             * InvoiceType : 2
             * InvoiceMode : 0
             * IsSupportSpecialInvoice : true
             * ReceiveTextRemark : false
             */

            private ApplicabilityInfo ApplicabilityInfo;
            private SmokingBean Smoking;
            private BroadNetBeanX BroadNet;
            private RoomFGToPPInfoBean RoomFGToPPInfo;
            private ChannelLimitBean ChannelLimit;
            private ExpressCheckoutBean ExpressCheckout;
            private int RoomID;
            private String PayType;
            private int RoomQuantity;
            private int MaxOccupancy;
            private String AreaRange;
            private String FloorRange;
            private String HasWindow;
            private String ExtraBedFee;
            private boolean IsHourlyRoom;
            private boolean IsFromAPI;
            private boolean IsShowAgencyTag;
            private int InvoiceType;
            private String InvoiceMode;
            private String IsSupportSpecialInvoice;
            private boolean ReceiveTextRemark;
            private List<RoomBedInfosBeanX> RoomBedInfos;
            private List<RoomTagsBean> RoomTags;
            private List<BookingRulesBean> BookingRules;
            private List<DescriptionsBeanY> Descriptions;

            public ApplicabilityInfo getApplicabilityInfo() {
                return ApplicabilityInfo;
            }

            public void setApplicabilityInfo(ApplicabilityInfo ApplicabilityInfo) {
                this.ApplicabilityInfo = ApplicabilityInfo;
            }

            public SmokingBean getSmoking() {
                return Smoking;
            }

            public void setSmoking(SmokingBean Smoking) {
                this.Smoking = Smoking;
            }

            public BroadNetBeanX getBroadNet() {
                return BroadNet;
            }

            public void setBroadNet(BroadNetBeanX BroadNet) {
                this.BroadNet = BroadNet;
            }

            public RoomFGToPPInfoBean getRoomFGToPPInfo() {
                return RoomFGToPPInfo;
            }

            public void setRoomFGToPPInfo(RoomFGToPPInfoBean RoomFGToPPInfo) {
                this.RoomFGToPPInfo = RoomFGToPPInfo;
            }

            public ChannelLimitBean getChannelLimit() {
                return ChannelLimit;
            }

            public void setChannelLimit(ChannelLimitBean ChannelLimit) {
                this.ChannelLimit = ChannelLimit;
            }

            public ExpressCheckoutBean getExpressCheckout() {
                return ExpressCheckout;
            }

            public void setExpressCheckout(ExpressCheckoutBean ExpressCheckout) {
                this.ExpressCheckout = ExpressCheckout;
            }

            public int getRoomID() {
                return RoomID;
            }

            public void setRoomID(int RoomID) {
                this.RoomID = RoomID;
            }

            public String getPayType() {
                return PayType;
            }

            public void setPayType(String PayType) {
                this.PayType = PayType;
            }

            public int getRoomQuantity() {
                return RoomQuantity;
            }

            public void setRoomQuantity(int RoomQuantity) {
                this.RoomQuantity = RoomQuantity;
            }

            public int getMaxOccupancy() {
                return MaxOccupancy;
            }

            public void setMaxOccupancy(int MaxOccupancy) {
                this.MaxOccupancy = MaxOccupancy;
            }

            public String getAreaRange() {
                return AreaRange;
            }

            public void setAreaRange(String AreaRange) {
                this.AreaRange = AreaRange;
            }

            public String getFloorRange() {
                return FloorRange;
            }

            public void setFloorRange(String FloorRange) {
                this.FloorRange = FloorRange;
            }

            public String getHasWindow() {
                return HasWindow;
            }

            public void setHasWindow(String HasWindow) {
                this.HasWindow = HasWindow;
            }

            public String getExtraBedFee() {
                return ExtraBedFee;
            }

            public void setExtraBedFee(String ExtraBedFee) {
                this.ExtraBedFee = ExtraBedFee;
            }

            public boolean isIsHourlyRoom() {
                return IsHourlyRoom;
            }

            public void setIsHourlyRoom(boolean IsHourlyRoom) {
                this.IsHourlyRoom = IsHourlyRoom;
            }

            public boolean isIsFromAPI() {
                return IsFromAPI;
            }

            public void setIsFromAPI(boolean IsFromAPI) {
                this.IsFromAPI = IsFromAPI;
            }

            public boolean isIsShowAgencyTag() {
                return IsShowAgencyTag;
            }

            public void setIsShowAgencyTag(boolean IsShowAgencyTag) {
                this.IsShowAgencyTag = IsShowAgencyTag;
            }

            public int getInvoiceType() {
                return InvoiceType;
            }

            public void setInvoiceType(int InvoiceType) {
                this.InvoiceType = InvoiceType;
            }

            public String getInvoiceMode() {
                return InvoiceMode;
            }

            public void setInvoiceMode(String InvoiceMode) {
                this.InvoiceMode = InvoiceMode;
            }

            public String getIsSupportSpecialInvoice() {
                return IsSupportSpecialInvoice;
            }

            public void setIsSupportSpecialInvoice(String IsSupportSpecialInvoice) {
                this.IsSupportSpecialInvoice = IsSupportSpecialInvoice;
            }

            public boolean isReceiveTextRemark() {
                return ReceiveTextRemark;
            }

            public void setReceiveTextRemark(boolean ReceiveTextRemark) {
                this.ReceiveTextRemark = ReceiveTextRemark;
            }

            public List<RoomBedInfosBeanX> getRoomBedInfos() {
                return RoomBedInfos;
            }

            public void setRoomBedInfos(List<RoomBedInfosBeanX> RoomBedInfos) {
                this.RoomBedInfos = RoomBedInfos;
            }

            public List<RoomTagsBean> getRoomTags() {
                return RoomTags;
            }

            public void setRoomTags(List<RoomTagsBean> RoomTags) {
                this.RoomTags = RoomTags;
            }

            public List<BookingRulesBean> getBookingRules() {
                return BookingRules;
            }

            public void setBookingRules(List<BookingRulesBean> BookingRules) {
                this.BookingRules = BookingRules;
            }

            public List<DescriptionsBeanY> getDescriptions() {
                return Descriptions;
            }

            public void setDescriptions(List<DescriptionsBeanY> Descriptions) {
                this.Descriptions = Descriptions;
            }

            public static class ApplicabilityInfo {
                /**
                 * Applicability : 10000000
                 */

                private String Applicability;

                public String getApplicability() {
                    return Applicability;
                }

                public void setApplicability(String Applicability) {
                    this.Applicability = Applicability;
                }
            }

            public static class SmokingBean {
                /**
                 * IsAllowSmoking : -100
                 */

                private String IsAllowSmoking;

                public String getIsAllowSmoking() {
                    return IsAllowSmoking;
                }

                public void setIsAllowSmoking(String IsAllowSmoking) {
                    this.IsAllowSmoking = IsAllowSmoking;
                }
            }

            public static class BroadNetBeanX {
                /**
                 * HasBroadnet : 0
                 * HasWirelessBroadnet : F
                 * WirelessBroadnetRoom : 1
                 * WirelessBroadnetFee : 0
                 * HasWiredBroadnet : F
                 * WiredBroadnetRoom : 1
                 * WiredBroadnetFee : 0
                 * WirelessBroadnet : 0
                 * WiredBroadnet : 0
                 */

                private int HasBroadnet;
                private String HasWirelessBroadnet;
                private String WirelessBroadnetRoom;
                private String WirelessBroadnetFee;
                private String HasWiredBroadnet;
                private String WiredBroadnetRoom;
                private String WiredBroadnetFee;
                private int WirelessBroadnet;
                private int WiredBroadnet;

                public int getHasBroadnet() {
                    return HasBroadnet;
                }

                public void setHasBroadnet(int HasBroadnet) {
                    this.HasBroadnet = HasBroadnet;
                }

                public String getHasWirelessBroadnet() {
                    return HasWirelessBroadnet;
                }

                public void setHasWirelessBroadnet(String HasWirelessBroadnet) {
                    this.HasWirelessBroadnet = HasWirelessBroadnet;
                }

                public String getWirelessBroadnetRoom() {
                    return WirelessBroadnetRoom;
                }

                public void setWirelessBroadnetRoom(String WirelessBroadnetRoom) {
                    this.WirelessBroadnetRoom = WirelessBroadnetRoom;
                }

                public String getWirelessBroadnetFee() {
                    return WirelessBroadnetFee;
                }

                public void setWirelessBroadnetFee(String WirelessBroadnetFee) {
                    this.WirelessBroadnetFee = WirelessBroadnetFee;
                }

                public String getHasWiredBroadnet() {
                    return HasWiredBroadnet;
                }

                public void setHasWiredBroadnet(String HasWiredBroadnet) {
                    this.HasWiredBroadnet = HasWiredBroadnet;
                }

                public String getWiredBroadnetRoom() {
                    return WiredBroadnetRoom;
                }

                public void setWiredBroadnetRoom(String WiredBroadnetRoom) {
                    this.WiredBroadnetRoom = WiredBroadnetRoom;
                }

                public String getWiredBroadnetFee() {
                    return WiredBroadnetFee;
                }

                public void setWiredBroadnetFee(String WiredBroadnetFee) {
                    this.WiredBroadnetFee = WiredBroadnetFee;
                }

                public int getWirelessBroadnet() {
                    return WirelessBroadnet;
                }

                public void setWirelessBroadnet(int WirelessBroadnet) {
                    this.WirelessBroadnet = WirelessBroadnet;
                }

                public int getWiredBroadnet() {
                    return WiredBroadnet;
                }

                public void setWiredBroadnet(int WiredBroadnet) {
                    this.WiredBroadnet = WiredBroadnet;
                }
            }

            public static class RoomFGToPPInfoBean {
                /**
                 * CanFGToPP : false
                 */

                private boolean CanFGToPP;

                public boolean isCanFGToPP() {
                    return CanFGToPP;
                }

                public void setCanFGToPP(boolean CanFGToPP) {
                    this.CanFGToPP = CanFGToPP;
                }
            }

            public static class ChannelLimitBean {
                /**
                 * IsApp : true
                 * IsWeb : true
                 * IsWeChat : true
                 */

                private boolean IsApp;
                private boolean IsWeb;
                private boolean IsWeChat;

                public boolean isIsApp() {
                    return IsApp;
                }

                public void setIsApp(boolean IsApp) {
                    this.IsApp = IsApp;
                }

                public boolean isIsWeb() {
                    return IsWeb;
                }

                public void setIsWeb(boolean IsWeb) {
                    this.IsWeb = IsWeb;
                }

                public boolean isIsWeChat() {
                    return IsWeChat;
                }

                public void setIsWeChat(boolean IsWeChat) {
                    this.IsWeChat = IsWeChat;
                }
            }

            public static class ExpressCheckoutBean {
                /**
                 * DepositAmount : [{"Amount":100}]
                 * IsSupported : true
                 * DepositRatio : -2.0
                 * AuditTime : 37
                 */

                private boolean IsSupported;
                private String DepositRatio;
                private String AuditTime;
                private List<DepositAmountBean> DepositAmount;

                public boolean isIsSupported() {
                    return IsSupported;
                }

                public void setIsSupported(boolean IsSupported) {
                    this.IsSupported = IsSupported;
                }

                public String getDepositRatio() {
                    return DepositRatio;
                }

                public void setDepositRatio(String DepositRatio) {
                    this.DepositRatio = DepositRatio;
                }

                public String getAuditTime() {
                    return AuditTime;
                }

                public void setAuditTime(String AuditTime) {
                    this.AuditTime = AuditTime;
                }

                public List<DepositAmountBean> getDepositAmount() {
                    return DepositAmount;
                }

                public void setDepositAmount(List<DepositAmountBean> DepositAmount) {
                    this.DepositAmount = DepositAmount;
                }

                public static class DepositAmountBean {
                    /**
                     * Amount : 100
                     */

                    private int Amount;

                    public int getAmount() {
                        return Amount;
                    }

                    public void setAmount(int Amount) {
                        this.Amount = Amount;
                    }
                }
            }

            public static class RoomBedInfosBeanX {
                /**
                 * BedInfo : [{"ID":"365","Name":"Double Bed","NumberOfBeds":"1","BedWidth":"1.45"}]
                 * ID : 360
                 * Name : Large Bed
                 */

                private String ID;
                private String Name;
                private List<BedInfoBeanX> BedInfo;

                public String getID() {
                    return ID;
                }

                public void setID(String ID) {
                    this.ID = ID;
                }

                public String getName() {
                    return Name;
                }

                public void setName(String Name) {
                    this.Name = Name;
                }

                public List<BedInfoBeanX> getBedInfo() {
                    return BedInfo;
                }

                public void setBedInfo(List<BedInfoBeanX> BedInfo) {
                    this.BedInfo = BedInfo;
                }

                public static class BedInfoBeanX {
                    /**
                     * ID : 365
                     * Name : Double Bed
                     * NumberOfBeds : 1
                     * BedWidth : 1.45
                     */

                    private String ID;
                    private String Name;
                    private String NumberOfBeds;
                    private String BedWidth;

                    public String getID() {
                        return ID;
                    }

                    public void setID(String ID) {
                        this.ID = ID;
                    }

                    public String getName() {
                        return Name;
                    }

                    public void setName(String Name) {
                        this.Name = Name;
                    }

                    public String getNumberOfBeds() {
                        return NumberOfBeds;
                    }

                    public void setNumberOfBeds(String NumberOfBeds) {
                        this.NumberOfBeds = NumberOfBeds;
                    }

                    public String getBedWidth() {
                        return BedWidth;
                    }

                    public void setBedWidth(String BedWidth) {
                        this.BedWidth = BedWidth;
                    }
                }
            }

            public static class RoomTagsBean {
                /**
                 * Code : RateCodeID
                 * Name : RateCodeID
                 * Value : 888737
                 * Desc :
                 */

                private String Code;
                private String Name;
                private String Value;
                private String Desc;

                public String getCode() {
                    return Code;
                }

                public void setCode(String Code) {
                    this.Code = Code;
                }

                public String getName() {
                    return Name;
                }

                public void setName(String Name) {
                    this.Name = Name;
                }

                public String getValue() {
                    return Value;
                }

                public void setValue(String Value) {
                    this.Value = Value;
                }

                public String getDesc() {
                    return Desc;
                }

                public void setDesc(String Desc) {
                    this.Desc = Desc;
                }
            }

            public static class BookingRulesBean {
                private List<TimeLimitInfoBean> TimeLimitInfo;

                public List<TimeLimitInfoBean> getTimeLimitInfo() {
                    return TimeLimitInfo;
                }

                public void setTimeLimitInfo(List<TimeLimitInfoBean> TimeLimitInfo) {
                    this.TimeLimitInfo = TimeLimitInfo;
                }

                public static class TimeLimitInfoBean {
                    private List<DateRestrictionsBean> DateRestrictions;

                    public List<DateRestrictionsBean> getDateRestrictions() {
                        return DateRestrictions;
                    }

                    public void setDateRestrictions(List<DateRestrictionsBean> DateRestrictions) {
                        this.DateRestrictions = DateRestrictions;
                    }

                    public static class DateRestrictionsBean {
                        /**
                         * Scope : Booking
                         * DateType : DateTime
                         * Start : 2016-08-24 00:00:00
                         * End : 2099-12-31 00:00:00
                         * IsIntraday : T
                         */

                        private String Scope;
                        private String DateType;
                        private String Start;
                        private String End;
                        private String IsIntraday;

                        public String getScope() {
                            return Scope;
                        }

                        public void setScope(String Scope) {
                            this.Scope = Scope;
                        }

                        public String getDateType() {
                            return DateType;
                        }

                        public void setDateType(String DateType) {
                            this.DateType = DateType;
                        }

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

                        public String getIsIntraday() {
                            return IsIntraday;
                        }

                        public void setIsIntraday(String IsIntraday) {
                            this.IsIntraday = IsIntraday;
                        }
                    }
                }
            }

            public static class DescriptionsBeanY {
                /**
                 * Text : 2016-08-242099-12-3118:0023:59,！
                 * Category : Promotion
                 */

                private String Text;
                private String Category;

                public String getText() {
                    return Text;
                }

                public void setText(String Text) {
                    this.Text = Text;
                }

                public String getCategory() {
                    return Category;
                }

                public void setCategory(String Category) {
                    this.Category = Category;
                }
            }
        }
    }
}
