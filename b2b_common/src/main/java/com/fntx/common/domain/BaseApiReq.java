package com.fntx.common.domain;

import lombok.Data;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @FileName:
 * @Author: 胡庆康
 * @Date: 2019/7/22 10:54
 */
@Data
public class BaseApiReq {
    /**
     * 携程AID
     */
    private String SID;
    /**
     * 携程SID
     */
    private String AID;
    /**
     * 携程接口调用UUID
     */
    private String UUID;
    /**
     * 默认为1
     */
    private String Mode;
    /**
     * JSON或XML  只能是JSON(默认是JSON)
     */
    private String Format;
    /**
     * key  授权秘钥
     */
    private String KEY;
    /**
     * api调用URL
     */
    private String url;
}
