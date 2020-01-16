package com.fntx.common.constant;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @Author: 渠猛
 * @Date: 2019/7/10 0010 下午 2:30
 * @Description:
 * 酒店redis 过期时间
 */
  
public enum HtRedisExpireTime {
    
    TOKEN(600L, "token过期时间"),
    REFRESH_TOKEN(900L, "refresh_token过期时间");




    private Long code;
    private String name;

    HtRedisExpireTime(Long code, String name) {
        this.code = code;
        this.name = name;
    }

    public Long getCode() {
        return code;
    }

    public String getName() {
        return name;
    }


    public static HtRedisExpireTime getByCode(Long value) {
        for (HtRedisExpireTime code : values()) {
            if (code.getCode().equals(value)) {
                return code;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "OperateTypeNo{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
