package com.fntx.search.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: ErrorUtils
 * @Author: 王俊文
 * @Date: 19-7-31 上午10:07
 * @Description: 异常返回封装
 */
@Component
public class ReturnUtils
{
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS+08:00");
    private static JSONObject resultModel;
    private static JSONArray errors;
    private static JSONObject errorsObject;
    private static JSONArray extension;
    private static JSONObject extensionObject;

    public void Initialization()
    {
        resultModel  = new JSONObject(16);
        errors  = new JSONArray(16);
        errorsObject = new JSONObject(16);
        extension = new JSONArray(16);
        extensionObject = new JSONObject(16);
    }


    public JSONObject responseStatus()
    {
        this.Initialization();
        JSONObject responseStatus = new JSONObject(16);
        responseStatus.put("Timestamp", sdf.format(new Date()));
        responseStatus.put("Ack", "Success");
        responseStatus.put("Errors", new ArrayList<>());
        responseStatus.put("Extension", new ArrayList<>());
        return responseStatus;
    }

    public JSONObject parameterEmpty(JSONObject ResponseStatus)
    {
        this.Initialization();
        ResponseStatus.put("Ack", "Warning");
        errorsObject.put("Message", "请求参数不能为空。");
        errorsObject.put("ErrorCode", "100001");
        errorsObject.put("ErrorFields", new ArrayList<>());
        errorsObject.put("ErrorClassification", "ValidationError");
        errors.add(errorsObject);
        extensionObject.put("Id", "100001");
        extensionObject.put("Value", "请求参数不能为空。");
        extension.add(extensionObject);

        ResponseStatus.put("Errors", errors);
        ResponseStatus.put("Extension", extension);
        resultModel.put("ResponseStatus", ResponseStatus);
        return resultModel;
    }

    public JSONObject timeRange(JSONObject ResponseStatus)
    {
        this.Initialization();
        ResponseStatus.put("Ack", "Warning");
        errorsObject.put("Message", "Required field missing:TimeRange");
        errorsObject.put("ErrorCode", "100004775100007");
        errorsObject.put("ErrorFields", new ArrayList<>());
        errorsObject.put("ErrorClassification", "ValidationError");
        errors.add(errorsObject);
        extensionObject.put("Id", "100004775100007");
        extensionObject.put("Value", "Required field missing:TimeRange");
        extension.add(extensionObject);

        ResponseStatus.put("Errors", errors);
        ResponseStatus.put("Extension", extension);
        resultModel.put("ResponseStatus", ResponseStatus);
        return resultModel;
    }

    public JSONObject timeIllegality(JSONObject ResponseStatus)
    {
        this.Initialization();
        ResponseStatus.put("Ack", "Warning");
        errorsObject.put("Message", "起始时间不合法,超过限制,默认往前一天，往后10分钟");
        errorsObject.put("ErrorCode", "100036");
        errorsObject.put("ErrorFields", new ArrayList<>());
        errorsObject.put("ErrorClassification", "ValidationError");
        errors.add(errorsObject);
        extensionObject.put("Id", "100036");
        extensionObject.put("Value", "起始时间不合法,超过限制,默认往前一天，往后10分钟");
        extension.add(extensionObject);
        ResponseStatus.put("Errors", errors);
        ResponseStatus.put("Extension", extension);
        resultModel.put("ResponseStatus", ResponseStatus);
        return resultModel;
    }

    public JSONObject invalidDate(JSONObject ResponseStatus)
    {
        this.Initialization();
        ResponseStatus.put("Ack", "Warning");
        errorsObject.put("Message", "无效的Date。");
        errorsObject.put("ErrorCode", "100003");
        errorsObject.put("ErrorFields", new ArrayList<>());
        errorsObject.put("ErrorClassification", "ValidationError");
        errors.add(errorsObject);
        extensionObject.put("Id", "100003");
        extensionObject.put("Value", "无效的Date。");
        extension.add(extensionObject);

        ResponseStatus.put("Errors", errors);
        ResponseStatus.put("Extension", extension);
        resultModel.put("ResponseStatus", ResponseStatus);
        return resultModel;
    }

    public JSONObject startTimeGreaterThanEndTime(JSONObject ResponseStatus)
    {
        this.Initialization();
        ResponseStatus.put("Ack", "Warning");
        errorsObject.put("Message", "截至时间不能比起始时间早");
        errorsObject.put("ErrorCode", "100034");
        errorsObject.put("ErrorFields", new ArrayList<>());
        errorsObject.put("ErrorClassification", "ValidationError");
        errors.add(errorsObject);
        extensionObject.put("Id", "100003");
        extensionObject.put("Value", "无效的Date。");
        extension.add(extensionObject);

        ResponseStatus.put("Errors", errors);
        ResponseStatus.put("Extension", extension);
        resultModel.put("ResponseStatus", ResponseStatus);
        return resultModel;
    }

    public JSONObject serviceError(JSONObject ResponseStatus)
    {
        this.Initialization();
        ResponseStatus.put("Ack", "Failure");
        errorsObject.put("Message", "async execution failed with exception: 服务端返回了错误信息，如有疑问请联系服务Owner. 错误信息: " +
                "开始时间和结束时间间隔不能超过1小时, caused by: 服务端返回了错误信息，如有疑问请联系服务Owner. 错误信息: 开始时间和结束时间间隔不能超过1小时");
        errorsObject.put("ErrorCode", "AsyncExecutionFail");
        errorsObject.put("SeverityCode", "Error");
        errorsObject.put("ErrorFields", new ArrayList<>());
        errorsObject.put("ErrorClassification", "ServiceError");
        errors.add(errorsObject);
        ResponseStatus.put("Errors", errors);
        ResponseStatus.put("Extension", new ArrayList<>());

        resultModel.put("ResponseStatus", ResponseStatus);
        return resultModel;
    }

    public JSONObject InvalidHotelID(JSONObject ResponseStatus)
    {
        this.Initialization();
        ResponseStatus.put("Ack", "Warning");
        errorsObject.put("Message", "无效的酒店ID。");
        errorsObject.put("ErrorCode", "100002");
        errorsObject.put("ErrorFields", new ArrayList<>());
        errorsObject.put("ErrorClassification", "ValidationError");
        errors.add(errorsObject);
        extensionObject.put("Id", "100002");
        extensionObject.put("Value", "无效的酒店ID。");
        extension.add(extensionObject);

        ResponseStatus.put("Errors", errors);
        ResponseStatus.put("Extension", extension);
        resultModel.put("ResponseStatus", ResponseStatus);
        return resultModel;
    }

}
