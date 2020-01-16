package com.fntx.common.domain.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: HotelIncrRoomtypeDto
 * @Author: 王俊文
 * @Date: 19-7-15 下午4:44
 * @Description: 房型上下架实体-入库相关
 */
public class HotelIncrRoomtypeDto implements Serializable
{
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS+08:00");
    private static SimpleDateFormat sdfm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final long serialVersionUID = 705949093604289060L;
    /**
     * RoomID : 121311554
     * HotelID : 5639584
     * Status : T
     * IsSupplier : T
     * DateChangeLastTime : 2017-11-28T14:00:00.5956810
     */

    @JSONField(name = "RoomID")
    private int RoomID;
    @JSONField(name = "HotelID")
    private int HotelID;
    @JSONField(name = "Status")
    private String Status;
    @JSONField(name = "IsSupplier")
    private String IsSupplier;
    @JSONField(name = "DateChangeLastTime")
    private String DateChangeLastTime;

    public int getRoomID() {
        return RoomID;
    }

    public void setRoomID(int RoomID) {
        this.RoomID = RoomID;
    }

    public int getHotelID() {
        return HotelID;
    }

    public void setHotelID(int HotelID) {
        this.HotelID = HotelID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getIsSupplier() {
        return IsSupplier;
    }

    public void setIsSupplier(String IsSupplier) {
        if ( IsSupplier.equals("T") )
        {
            this.IsSupplier = "1";
        }else if ( IsSupplier.equals("F") )
        {
            this.IsSupplier = "0";
        }else if ( IsSupplier.equals("1") )
        {
            this.IsSupplier = "T";
        }else if ( IsSupplier.equals("0") )
        {
            this.IsSupplier = "F";
        }
    }

    public String getDateChangeLastTime() {
        return DateChangeLastTime;
    }

    public void setDateChangeLastTime(String DateChangeLastTime) {

        try {
            this.DateChangeLastTime = sdfm.format(sdf.parse(DateChangeLastTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){ return true;}
        if (!(o instanceof HotelIncrRoomtypeDto)) {return false;}
        HotelIncrRoomtypeDto that = (HotelIncrRoomtypeDto) o;
        return RoomID == that.RoomID &&
                HotelID == that.HotelID &&
                Objects.equals(Status, that.Status) &&
                Objects.equals(IsSupplier, that.IsSupplier) &&
                Objects.equals(DateChangeLastTime, that.DateChangeLastTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(RoomID, HotelID, Status, IsSupplier, DateChangeLastTime);
    }

    @Override
    public String toString() {
        return "HotelIncrRoomtypeDto{" +
                "RoomID=" + RoomID +
                ", HotelID=" + HotelID +
                ", Status='" + Status + '\'' +
                ", IsSupplier='" + IsSupplier + '\'' +
                ", DateChangeLastTime='" + DateChangeLastTime + '\'' +
                '}';
    }
}
