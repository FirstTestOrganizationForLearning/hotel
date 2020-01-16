package com.fntx.common.domain.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: RoomStatusChangeItemsDto
 * @Author: 王俊文
 * @Date: 19-8-1 下午2:04
 * @Description: 房型上下线
 */
public class RoomStatusChangeItemsDto implements Serializable
{
    private static final long serialVersionUID = 6545964750440111148L;
    /**
     * RoomID : 347883580
     * HotelID : 7422039
     * Status : T
     * DateChangeLastTime : 2019-07-12T10:20:00.263+08:00
     */

    @JSONField(name = "RoomID")
    private int RoomID;
    @JSONField(name = "HotelID")
    private int HotelID;
    @JSONField(name = "Status")
    private String Status;
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

    public String getDateChangeLastTime() {
        return DateChangeLastTime;
    }

    public void setDateChangeLastTime(String DateChangeLastTime) {
        this.DateChangeLastTime = DateChangeLastTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoomStatusChangeItemsDto)) return false;
        RoomStatusChangeItemsDto that = (RoomStatusChangeItemsDto) o;
        return RoomID == that.RoomID &&
                HotelID == that.HotelID &&
                Objects.equals(Status, that.Status) &&
                Objects.equals(DateChangeLastTime, that.DateChangeLastTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(RoomID, HotelID, Status, DateChangeLastTime);
    }

    @Override
    public String toString() {
        return "RoomStatusChangeItems{" +
                "RoomID=" + RoomID +
                ", HotelID=" + HotelID +
                ", Status='" + Status + '\'' +
                ", DateChangeLastTime='" + DateChangeLastTime + '\'' +
                '}';
    }
}
