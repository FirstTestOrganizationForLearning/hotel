package com.fntx.common.domain.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: BSaleRoomDto
 * @Author: 王俊文
 * @Date: 19-8-1 上午9:20
 * @Description: 售卖房型-物理房型信息实体
 */
public class BSaleRoomDto implements Serializable
{
    private static final long serialVersionUID = 2138352506168129954L;

    /**
     * FG现付，PP预付
     */
    private String paytype;

    /**
     * 最大入住人数
     */
    private Integer maxOccupancy;

    /**
     * 是否可预定
     * 1：是  0：不是
     */
    private Integer isAllowBooking;

    /**
     * 儿童入住人数上限

     */
    private Integer childMaxoccupancy;

    /**
     * 儿童年龄上限

     */
    private Integer childMaxage;

    /**
     * 儿童年龄下限

     */
    private Integer childMinage;

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        //FG现付，PP预付
//        OnlyFGPrice: 到付   OnlyPPPrice: 预付
        if ( paytype.equals("FG") )
        {
            this.paytype = "OnlyFGPrice";
        }else
        {
            this.paytype = "OnlyPPPrice";
        }
    }

    public Integer getMaxOccupancy() {
        return maxOccupancy;
    }

    public void setMaxOccupancy(Integer maxOccupancy) {
        this.maxOccupancy = maxOccupancy;
    }

    public Integer getIsAllowBooking() {
        return isAllowBooking;
    }

    public void setIsAllowBooking(Integer isAllowBooking) {
        this.isAllowBooking = isAllowBooking;
    }

    public Integer getChildMaxoccupancy() {
        return childMaxoccupancy;
    }

    public void setChildMaxoccupancy(Integer childMaxoccupancy) {
        this.childMaxoccupancy = childMaxoccupancy;
    }

    public Integer getChildMaxage() {
        return childMaxage;
    }

    public void setChildMaxage(Integer childMaxage) {
        this.childMaxage = childMaxage;
    }

    public Integer getChildMinage() {
        return childMinage;
    }

    public void setChildMinage(Integer childMinage) {
        this.childMinage = childMinage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (!(o instanceof BSaleRoomDto)) {return false;}
        BSaleRoomDto that = (BSaleRoomDto) o;
        return paytype.equals(that.paytype) &&
                maxOccupancy.equals(that.maxOccupancy) &&
                isAllowBooking.equals(that.isAllowBooking) &&
                childMaxoccupancy.equals(that.childMaxoccupancy) &&
                childMaxage.equals(that.childMaxage) &&
                childMinage.equals(that.childMinage) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(paytype, maxOccupancy, isAllowBooking, childMaxoccupancy, childMaxage, childMinage);
    }

    @Override
    public String toString() {
        return "BSaleRoomDto{" +
                "paytype='" + paytype + '\'' +
                ", maxOccupancy=" + maxOccupancy +
                ", isAllowBooking=" + isAllowBooking +
                ", childMaxoccupancy=" + childMaxoccupancy +
                ", childMaxage=" + childMaxage +
                ", childMinage=" + childMinage +
                '}';
    }
}
