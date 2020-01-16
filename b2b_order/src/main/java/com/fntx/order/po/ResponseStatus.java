package com.fntx.order.po;

import lombok.Data;

import java.util.List;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @FileName:
 * @Author: 胡庆康
 * @Date: 2019/7/12 15:07
 */
@Data
public class ResponseStatus {
    public static class Error
    {
        public String ErrCode;
        public String ErrMsg;
    }

    public static class Extension
    {

    }
    public String Timestamp;
    public String Ack;
    public List<Error> Errors;
    public List<Extension> Extension;
}
