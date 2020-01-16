package com.fntx.common.domain.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: HotelIncrRoomtypeMgDto
 * @Author: 王俊文
 * @Date: 19-8-6 上午10:57
 * @Description: 房型上下架实体-mongodb相关
 */
public class HotelIncrRoomtypeMgDto implements Serializable
{
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
    private String HotelID;
    @JSONField(name = "Status")
    private String Status;
    @JSONField(name = "IsSupplier")
    private String IsSupplier;
    @JSONField(name = "DateChangeLastTime")
    private String DateChangeLastTime;

    public int getRoomID() {
        return RoomID;
    }

    public void setRoomID(int roomID) {
        RoomID = roomID;
    }

    public String getHotelID() {
        return HotelID;
    }

    public void setHotelID(String hotelID) {
        HotelID = hotelID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getIsSupplier() {
        return IsSupplier;
    }

    public void setIsSupplier(String isSupplier) {
        IsSupplier = isSupplier;
    }

    public String getDateChangeLastTime() {
        return DateChangeLastTime;
    }

    public void setDateChangeLastTime(String dateChangeLastTime) {
        DateChangeLastTime = dateChangeLastTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HotelIncrRoomtypeMgDto)) return false;
        HotelIncrRoomtypeMgDto that = (HotelIncrRoomtypeMgDto) o;
        return RoomID == that.RoomID &&
                Objects.equals(HotelID, that.HotelID) &&
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
        return "HotelIncrRoomtypeMgDto{" +
                "RoomID=" + RoomID +
                ", HotelID='" + HotelID + '\'' +
                ", Status='" + Status + '\'' +
                ", IsSupplier='" + IsSupplier + '\'' +
                ", DateChangeLastTime='" + DateChangeLastTime + '\'' +
                '}';
    }
}
