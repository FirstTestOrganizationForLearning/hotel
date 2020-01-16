package com.fntx.common.utils;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: StringToJsonSerializer
 * @Author: 王俊文
 * @Date: 19-7-23 上午9:30
 * @Description: TODO
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * 序列化时将string转为json格式,避免出现\转义符号
 * @author lenovo
 *
 */
public class StringToJsonSerializer implements ObjectSerializer {

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType,
                      int features) throws IOException {
        //判断待处理字符串是json对象  还是 json数组
        if ( JSONObject.parse((String) object).getClass() == JSONObject.class )
        {
            //json对象
            JSONObject jsonObject = JSONObject.parseObject((String) object);
                    serializer.write(jsonObject);
        }else
        {
            //json数组
            JSONArray jsonObject = JSONArray.parseArray((String) object);
            serializer.write(jsonObject);
        }
    }
}
