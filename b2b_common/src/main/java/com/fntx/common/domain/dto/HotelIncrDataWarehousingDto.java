package com.fntx.common.domain.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: HotelIncrDataWarehousingDto
 * @Author: 王俊文
 * @Date: 19-7-15 上午10:24
 * @Description: 静态信息增量-oracle逻辑实体
 */
public class HotelIncrDataWarehousingDto implements Serializable
{
    private static final long serialVersionUID = 1L;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS+08:00");
    private static SimpleDateFormat sdfm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    /**
     * ChangeDetails : [{"Name":"IsOpen","NewValue":"F"}]
     * Category : RoomType
     * HotelID : 6268029
     * RoomTypeID : 33234614
     * RoomID : 0
     * ChangeTime : 2018-03-09T07:45:00.000+08:00
     */

    private String changeId;
    /**
    发生变化的实体类型，类别如下：
    Hotel-酒店
    RoomType-物理房型
    Room-售卖房型
     */
    @JSONField(name = "Category")
    private String Category;
    /**
    酒店ID
     */
    @JSONField(name = "HotelID")
    private String HotelID;
    /**
    物理房型ID
     */
    @JSONField(name = "RoomTypeID")
    private String RoomTypeID;
    /**
    售卖房型ID
     */
    @JSONField(name = "RoomID")
    private String RoomID;
    /**
    静态信息发生变化的时间
     */
    @JSONField(name = "ChangeTime")
    private String ChangeTime;
    /**
     增量明细信息，请求体传T时才能获取到，否则返回null；Name的枚举值如下：
     */
    @JSONField(name = "ChangeDetails")
    private List<ChangeDetailsBean> ChangeDetails;

    public String getChangeId() {
        return changeId;
    }

    public void setChangeId(String changeId) {
            this.changeId = changeId;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String Category) {
        this.Category = Category;
    }

    public String getHotelID() {
        return HotelID;
    }

    public void setHotelID(String HotelID) {
        this.HotelID = HotelID;
        String  changeIds = "";
        changeIds = new Date().getTime() + UUID.randomUUID().toString().replaceAll("-","");
        if ( HotelID != null && !"".equals(HotelID) )
        {
             changeIds = changeIds + HotelID;
        }
        this.changeId = changeIds;
    }

    public String getRoomTypeID() {
        return RoomTypeID;
    }

    public void setRoomTypeID(String RoomTypeID) {
        this.RoomTypeID = RoomTypeID;
    }

    public String getRoomID() {
        return RoomID;
    }

    public void setRoomID(String RoomID) {
        this.RoomID = RoomID;
    }

    public String getChangeTime() {
        return ChangeTime;
    }

    public void setChangeTime(String ChangeTime) {
        try {
            this.ChangeTime = sdfm.format(sdf.parse(ChangeTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public List<ChangeDetailsBean> getChangeDetails() {
        return ChangeDetails;
    }

    public void setChangeDetails(List<ChangeDetailsBean> ChangeDetails) {
        this.ChangeDetails = ChangeDetails;
    }

    public static class ChangeDetailsBean {
        /**
         * Name : IsOpen
         * NewValue : F
         */

        private String changedetailId;
        private String changeId;
        /**
            IsOpen 房型上下线，T上线F下线
            address 酒店地址
            hotelname、hotelnameintl、hotelcnname 酒店名称
            hotelstar 挂牌星级
            starlicence 是否挂牌
            ctripstar 携程星级
            gdlat、gdlon、glat、glon、lat、lon 经纬度
            telephone 联系电话
            bookable 酒店是否可预订
            bookablechangereason 不可订原因
            roomname
            addbed 是否加床
            addbedfee 加床费
            haskingbed、hassinglebed、hastwinbed 床型
            kingbedwidth、singlebedwidth、twinbedwidth 床宽
         */
        @JSONField(name = "Name")
        private String Name;
        @JSONField(name = "NewValue")
        private String NewValue;

        public String getChangedetailId() {
            return changedetailId;
        }

        public void setChangedetailId(String changedetailId) {
            this.changedetailId = changedetailId;
        }

        public String getChangeId() {
            return changeId;
        }

        public void setChangeId(String changeId) {
            this.changeId = changeId;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getNewValue() {
            return NewValue;
        }

        public void setNewValue(String NewValue) {
            this.NewValue = NewValue;
            Integer romCH = (int)Math.random()*(999-1+1);
            String changedetailIds = new Date().getTime()+UUID.randomUUID().toString().replaceAll("-","");
            if ( NewValue != null && !"".equals(NewValue) )
            {
                 changedetailIds = changedetailIds + NewValue.length();
            }
            this.changedetailId = changedetailIds;
        }
    }
}
