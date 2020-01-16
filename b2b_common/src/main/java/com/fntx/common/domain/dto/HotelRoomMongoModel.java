package com.fntx.common.domain.dto;

import java.io.Serializable;

/**
 * @description: 携程酒店房型静态信息存入mongo的model
 * @author: 魏世杰
 * @date: Created in 2019/7/26 17:27
 * @version: 1.0
 * @modified By:
 */
public class HotelRoomMongoModel implements Serializable {

    private String hotelId;

    private String hotelRoomDetail;

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelRoomDetail() {
        return hotelRoomDetail;
    }

    public void setHotelRoomDetail(String hotelRoomDetail) {
        this.hotelRoomDetail = hotelRoomDetail;
    }
}
