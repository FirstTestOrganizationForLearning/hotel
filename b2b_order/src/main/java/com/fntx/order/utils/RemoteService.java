package com.fntx.order.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fntx.common.constant.BusiModuleType;
import com.fntx.common.constant.Enviroment;
import com.fntx.common.constant.OperateTypeNo;
import com.fntx.common.domain.BaseInfo;
import com.fntx.common.domain.FinanceResponse;
import com.fntx.common.utils.SignCheckUtil;
import com.fntx.order.feign.AdminFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 远程服务调用
 *
 * @Copyright (C), 2019, 弗恩天下
 * @FileName
 * @Author 胡庆康
 * @Date 2019/7/24 17:30
 */
@Component
public class RemoteService {
    @Autowired
    private static AdminFeign adminFeign;

    /**
     * 调用财务接口查询余额
     *
     * @param baseInfo  分销商信息
     * @param jsonParam 获取参数中的moduleId判断是否国际酒店
     * @author 胡庆康
     * @date 2019/7/25 16:53
     * @returns
     */
    public FinanceResponse getBalance(BaseInfo baseInfo, JSONObject jsonParam) {
        RestTemplate restTemplate = new RestTemplate();
        FinanceResponse financeResponse = new FinanceResponse();
        String timestamp = String.valueOf(System.currentTimeMillis());
        //酒店的模块id
        String moduleId = "";
        //securityKey-123
        String securiyKey = "123";
        //模块id-酒店
        String mode = "";
        if (jsonParam.containsKey("moduleId")) {
            mode = jsonParam.get("moduleId").toString();
            if ("0".equals(mode)) {
                moduleId = BusiModuleType.HOTEL.getCode();
            } else {
                moduleId = BusiModuleType.INTERNATIONAL_TICKET.getCode();
            }
        } else {
            moduleId = BusiModuleType.HOTEL.getCode();
        }


        //支付方式-预存款
        String payType = "0000700001";
        //sign 查询余额接口名称：searchBalance
        String sign = SignCheckUtil.signGenerate(baseInfo.getAID(), moduleId, timestamp, "searchBalance", securiyKey);
        HttpHeaders headers = new HttpHeaders();
        headers.set("timestamp", timestamp);
        headers.set("securykey", securiyKey);
        headers.set("sign", sign);
        JSONObject param = new JSONObject();
        param.put("cid", baseInfo.getAID());
        param.put("moduleId", moduleId);
        param.put("paytype", payType);
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<JSONObject> formEntity = new HttpEntity<JSONObject>(param, headers);
        try {
            JSONObject response = restTemplate.exchange(Enviroment.FINANCE_SEARCH_BALANCE.getValue(), HttpMethod.POST, formEntity, JSONObject.class).getBody();
            financeResponse = JSON.toJavaObject(response, FinanceResponse.class);
        } catch (Exception e) {
            return null;
        }
        return financeResponse;
    }

    /**
     * 调用财务支付接口
     *
     * @param baseInfo 分销商信息
     * @param orderNo  订单号
     * @param payMoney 支付金额
     * @param jsonParam 获取参数中的moduleId判断是否国际酒店
     * @author 胡庆康
     * @date 2019/7/25 17:08
     * @returns
     */
    public FinanceResponse pay(BaseInfo baseInfo, String orderNo, String payMoney, JSONObject jsonParam) {
        FinanceResponse financeResponse;
        RestTemplate restTemplate = new RestTemplate();
        int balance = -1;
        String timestamp = String.valueOf(System.currentTimeMillis());
        //securityKey-123
        String securiyKey = "123";
        //模块id-酒店
        String moduleId = BusiModuleType.HOTEL.getCode();
        //模块id-国际国内酒店
        String mode = "";
        if (jsonParam.containsKey("moduleId")) {
            mode = jsonParam.get("moduleId").toString();
            if ("0".equals(mode)) {
                moduleId = BusiModuleType.HOTEL.getCode();
            } else {
                moduleId = BusiModuleType.INTERNATIONAL_TICKET.getCode();
            }
        } else {
            moduleId = BusiModuleType.HOTEL.getCode();
        }
        //支付方式-预存款
        String payType = "0000700001";
        //操作行为
        String operateType = OperateTypeNo.PAYMENT_OF_ORDERS.getCode();
        //sign 查询余额接口名称：pay
        String sign = SignCheckUtil.signGenerate(baseInfo.getAID(), moduleId, timestamp, "pay", securiyKey);
        HttpHeaders headers = new HttpHeaders();
        headers.set("timestamp", timestamp);
        headers.set("securykey", securiyKey);
        headers.set("sign", sign);
        JSONObject param = new JSONObject();
        param.put("cid", baseInfo.getAID());
        param.put("moduleId", moduleId);
        param.put("paytype", payType);
        param.put("operatetype", operateType);
        param.put("orderNo", orderNo);
        param.put("payMoney", payMoney);
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<JSONObject> formEntity = new HttpEntity<JSONObject>(param, headers);
        try {
            JSONObject response = restTemplate.exchange(Enviroment.FINANCE_PAY.getValue(), HttpMethod.POST, formEntity, JSONObject.class).getBody();
            financeResponse = JSON.toJavaObject(response, FinanceResponse.class);
        } catch (Exception e) {
            return null;
        }
        return financeResponse;
    }

    /**
     * 调用财务取消支付接口
     *
     * @param baseInfo    分销商信息
     * @param orderNo     订单号
     * @param payMoney    支付金额
     * @param tradedetail 支付流水单号
     * @param jsonParam 获取参数中的moduleId判断是否国际酒店
     * @author 胡庆康
     * @date 2019/7/25 17:08
     * @returns
     */
    public FinanceResponse canclePay(BaseInfo baseInfo, String orderNo, String payMoney, String tradedetail, JSONObject jsonParam) {
        FinanceResponse financeResponse;
        RestTemplate restTemplate = new RestTemplate();
        int balance = -1;
        String timestamp = String.valueOf(System.currentTimeMillis());
        //securityKey-123
        String securiyKey = "123";
        //模块id-酒店
        String moduleId = BusiModuleType.HOTEL.getCode();
        //模块id-国际国内酒店
        String mode = "";
        if (jsonParam.containsKey("moduleId")) {
            mode = jsonParam.get("moduleId").toString();
            if ("0".equals(mode)) {
                moduleId = BusiModuleType.HOTEL.getCode();
            } else {
                moduleId = BusiModuleType.INTERNATIONAL_TICKET.getCode();
            }
        } else {
            moduleId = BusiModuleType.HOTEL.getCode();
        }
        //支付方式-预存款
        String payType = "0000700001";
        //操作行为
        String operateType = OperateTypeNo.PAYMENT_OF_ORDERS.getCode();
        //sign 取消支付接口名：cancelPay
        String sign = SignCheckUtil.signGenerate(baseInfo.getAID(), moduleId, timestamp, "cancelPay", securiyKey);
        HttpHeaders headers = new HttpHeaders();
        headers.set("timestamp", timestamp);
        headers.set("securykey", securiyKey);
        headers.set("sign", sign);
        JSONObject param = new JSONObject();
        param.put("cid", baseInfo.getAID());
        param.put("moduleId", moduleId);
        param.put("paytype", payType);
        param.put("operatetype", operateType);
        param.put("orderNo", orderNo);
        param.put("payMoney", payMoney);
        param.put("tradedetail", tradedetail);
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<JSONObject> formEntity = new HttpEntity<JSONObject>(param, headers);
        try {
            JSONObject response = restTemplate.exchange(Enviroment.FINANCE_CANCLE_PAY.getValue(), HttpMethod.POST, formEntity, JSONObject.class).getBody();
            financeResponse = JSON.toJavaObject(response, FinanceResponse.class);
        } catch (Exception e) {
            return null;
        }
        return financeResponse;
    }
}
