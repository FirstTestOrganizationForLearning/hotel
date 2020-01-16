package com.fntx.common.domain.dto;

import java.io.Serializable;

/**
 * @description: 携程酒店静态信息存入mongo的model
 * @author: 魏世杰
 * @date: Created in 2019/7/26 17:27
 * @version: 1.0
 * @modified By:
 */
public class HotelMongoModel implements Serializable {

    private String hotelId;

    private String hotelDetail;

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelDetail() {
        return hotelDetail;
    }

    public void setHotelDetail(String hotelDetail) {
        this.hotelDetail = hotelDetail;
    }

    @Override
    public String toString() {
        return "HotelMongoModel{" +
                "hotelId='" + hotelId + '\'' +
                ", hotelDetail='" + hotelDetail + '\'' +
                '}';
    }
}
