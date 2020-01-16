package com.fntx.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fntx.common.constant.*;
import com.fntx.common.domain.*;
import com.fntx.common.utils.DateUtil;
import com.fntx.common.utils.DistributorCheck;
import com.fntx.common.utils.ValaditionUtils;
import com.fntx.order.dao.HtOrderDetailMapper;
import com.fntx.order.dao.HtOrderMapper;
import com.fntx.order.domain.HtOrder;
import com.fntx.order.domain.HtOrderDetail;
import com.fntx.order.po.*;
import com.fntx.order.service.HtOrderOplogService;
import com.fntx.order.service.IOrderService;
import com.fntx.order.utils.RemoteService;
import com.fntx.order.utils.TestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {

    private final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    /**
     * 订单日志mapper
     */
    @Autowired
    private HtOrderOplogService orderOplogService;
    /**
     * 订单表mapper
     */
    @Autowired
    private HtOrderMapper htOrderMapper;
    /**
     * 订单详情表mapper
     */
    @Autowired
    private HtOrderDetailMapper htOrderDetailMapper;
    @Autowired
    private RemoteService remoteService;
    @Autowired
    private HtOrderOplogService htOrderOplogService;


    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public JSONObject checkBookable(JSONObject param) {
        return getResponse(param, Enviroment.HOTEL_BOOKABLE_CHECK.getValue(), Enviroment.TEST_HOTEL_BOOKABLE_CHECK.getValue());
    }

    @Override
    public JSONObject getOrderNightCheckStatus(JSONObject param) {
        return getResponseNew(param, Enviroment.FN_ORDER_NIGHTCHECK_POSTBACK.getValue(), Enviroment.TEST_HOTEL_ORDER_NIGHTCHECK_POSTBACK.getValue());
//        return getResponseNew(param, Enviroment.HOTEL_ORDER_NIGHTCHECK_POSTBACK.getValue(), Enviroment.TEST_HOTEL_ORDER_NIGHTCHECK_POSTBACK.getValue());
    }

    @Override
    public JSONObject getOrderRefund(JSONObject param) {
        return getResponseNew(param, Enviroment.FN_ORDER_REFUND.getValue(), Enviroment.TEST_HOTEL_ORDER_REFUND.getValue());
//        return getResponseNew(param, Enviroment.HOTEL_ORDER_REFUND.getValue(), Enviroment.TEST_HOTEL_ORDER_REFUND.getValue());
    }

    @Override
    public JSONObject getOrderNightCheckResult(JSONObject param) {
        return getResponseNew(param, Enviroment.FN_ORDER_NIGHTCHECK.getValue(), Enviroment.TEST_HOTEL_ORDER_NIGHTCHECK.getValue());
//        return getResponseNew(param, Enviroment.HOTEL_ORDER_NIGHTCHECK.getValue(), Enviroment.TEST_HOTEL_ORDER_NIGHTCHECK.getValue());
    }


    private JSONObject ctripCancelOrder(JSONObject param) {
        return getResponseNew(param, Enviroment.FN_ORDER_CANCEL.getValue(), Enviroment.TEST_HOTEL_ORDER_CANCEL.getValue());
//        return getResponseNew(param, Enviroment.HOTEL_ORDER_CANCEL.getValue(), Enviroment.TEST_HOTEL_ORDER_CANCEL.getValue());
    }

    @Override
    public JSONObject orderCreate(BaseInfo baseInfo, JSONObject param) {
        JSONObject response = null;
        try {
            //写日志
            orderOplogService.insertOrderOpLog(baseInfo, OplogOperationType.Create.getCode(), param.toJSONString(), "准备创建订单", "");
            // 将分销商uuid替换为弗恩uuid @TODO 上线修改为我方对携程UUID
            JSONObject jsonParam = JSONObject.parseObject(param.toJSONString().replace(baseInfo.getUUID(), Enviroment.FN_UUID.getValue()));
            //调用携程接口
            response = getResponse(jsonParam, Enviroment.HOTEL_ORDER_CREATE.getValue(), Enviroment.TEST_HOTEL_ORDER_CREATE.getValue());
            if (null == response) {
                //写日志
                orderOplogService.insertOrderOpLog(baseInfo, OplogOperationType.Create.getCode(), "调用携程接口失败", "创建订单失败", "");
                return ValaditionUtils.getApiError();
            }
            CreateOrderResponse orderResp = null;
            try {
                orderResp = JSON.toJavaObject(response, CreateOrderResponse.class);
            } catch (Exception e) {
                orderOplogService.insertOrderOpLog(baseInfo, OplogOperationType.Create.getCode(), "根据返回json转换实体失败，返回体为" + response.toJSONString(), "创建订单失败", "");
                return ValaditionUtils.getApiError();
            }
            if (orderResp == null) {
                orderOplogService.insertOrderOpLog(baseInfo, OplogOperationType.Create.getCode(), "根据返回json转换实体为null或携程接口返回创建失败应答，返回体为" + response.toJSONString(), "创建订单失败", "");
                return ValaditionUtils.getApiError();
            }
            if (null == orderResp.ResponseStatus
                    || !"Success".equals(orderResp.ResponseStatus.Ack)) {
                // 写日志
                orderOplogService.insertOrderOpLog(baseInfo, OplogOperationType.Create.getCode(), "根据返回json转换实体为null或携程接口返回创建失败应答，返回体为" + response.toJSONString(), "创建订单失败", "");
                return response;
            }
            // 这里仅仅表示调用远程方法成功，需要进一步判断创建订单是否成功
            if (orderResp.Errors.size() == 0) {
                // 保存订单
                CreateOrderRequest orderReq = JSON.toJavaObject(param, CreateOrderRequest.class);
                String strDistOrderId = "";
                for (GeneralEntity.ResponseStatus.UniqueIDItem item : orderReq.UniqueID
                ) {
                    if ("504".equals(item.Type)) {
                        strDistOrderId = item.ID;
                    }
                }
                HtOrder order = new HtOrder();
                GeneralEntity.ResponseStatus.HotelReservationIDs ids =
                        orderResp.HotelReservations.HotelReservation.ResGlobalInfo.HotelReservationIDs.get(0);
                String orderId = ids.getResID_Value();
                order.setOrderId(orderId);
                order.setFenOrderId("Fen_" + orderId);
                order.setOrderJson(response.toJSONString());
                order.setOrderState(HotelOrderState.Uncommitted.getCode());
                order.setCreateTime(new Date());
                order.setSubmitTime(null);
                order.setCancelTime(null);
                order.setDistOrderId(strDistOrderId);
                order.setDistUuid(baseInfo.getUUID());
                order.setCreater(baseInfo.getUUID());
                order.setCurrencyCode(orderResp.HotelReservations
                        .HotelReservation
                        .ResGlobalInfo
                        .Total
                        .CurrencyCode);
                // 注意该金额作为携程请求支付回调的金额数
                order.setAmount(orderResp.HotelReservations
                        .HotelReservation.ResGlobalInfo
                        .Total.InclusiveAmount);
                order.setPayAmount(0.0);
                order.setPayTime(null);
                order.setOrderType(0);
                order.setMemo("");

                // 酒店ID
                String strHotelId = orderReq.HotelReservations.HotelReservation.RoomStays.RoomStay
                        .BasicPropertyInfo.HotelCode;
                //酒店id
                order.setHotelId(strHotelId);
                // 房间ID
                String strRoomId = orderReq.HotelReservations.HotelReservation.RoomStays.RoomStay.RatePlans
                        .RatePlan.RoomID;
                order.setRoomId(strRoomId);
                //房间名称
                // 价格计划ID
                String strRatePlanId = orderReq.HotelReservations.HotelReservation.RoomStays.RoomStay.RatePlans
                        .RatePlan.RatePlanID;
                order.setRatePlanId(strRatePlanId);
                // BookingCode
                String strBookingCode = orderReq.HotelReservations.HotelReservation.RoomStays.RoomStay.RatePlans
                        .RatePlan.BookingCode;
                order.setBookingCode(strBookingCode);
                // 是否预付房型
                String strPrepaidIndicator = orderReq.HotelReservations.HotelReservation.RoomStays.RoomStay
                        .RatePlans.RatePlan.PrepaidIndicator;
                order.setPrepaidIndicator(("true".equals(strPrepaidIndicator) ? 1 : 0));
                // 预订房间数量
                int nNumberOfUnits = orderReq.HotelReservations.HotelReservation.RoomStays.RoomStay.RoomTypes
                        .RoomType.NumberOfUnits;
                order.setNumberOfUnits(nNumberOfUnits);
                // 入离时间
                String strStartDate = orderReq.HotelReservations.HotelReservation.ResGlobalInfo.TimeSpan.Start;
                order.setStartDate(format.parse(strStartDate.replace("T", " ")));
                String strEndDate = orderReq.HotelReservations.HotelReservation.ResGlobalInfo.TimeSpan.End;
                order.setEndDate(format.parse(strEndDate.replace("T", " ")));
                // 客人数量及名字
                int nGuestCount = orderReq.HotelReservations.HotelReservation.ResGlobalInfo.GuestCounts
                        .GuestCount.Count;
                order.setGuestCount(nGuestCount);
                List<GeneralEntity.ResponseStatus.PersonNameItem> nameList = orderReq.HotelReservations.HotelReservation.ResGuests.ResGuest
                        .Profiles.ProfileInfo.Profile.Customer.PersonName;
                String strCustomers = "";
                for (GeneralEntity.ResponseStatus.PersonNameItem cust : nameList) {
                    strCustomers += cust.Name;
                    strCustomers += ",";
                }
                strCustomers = strCustomers.substring(0, strCustomers.length() - 1);
                order.setCustomers(strCustomers);
                // 联系人
                String strContactName = orderReq.HotelReservations.HotelReservation.ResGuests.ResGuest.Profiles
                        .ProfileInfo.Profile.Customer.ContactPerson.PersonName.Name;
                order.setContactName(strContactName);
                String strTelphone = "";
                List<GeneralEntity.ResponseStatus.TelephoneItem> telList = orderReq.HotelReservations.HotelReservation.ResGuests.ResGuest
                        .Profiles.ProfileInfo.Profile.Customer.ContactPerson.Telephone;
                for (GeneralEntity.ResponseStatus.TelephoneItem tel : telList) {
                    strTelphone += tel.PhoneNumber;
                    strTelphone += ",";
                }
                strTelphone = strTelphone.substring(0, strTelphone.length() - 1);
                order.setContactTelphone(strTelphone);
                String strEmail = orderReq.HotelReservations.HotelReservation.ResGuests.ResGuest.Profiles
                        .ProfileInfo.Profile.Customer.ContactPerson.Email;
                order.setContactEmail(strEmail);
                // 到达时间
                String strArrivalTime =
                        orderReq.HotelReservations.HotelReservation.ResGuests.ResGuest.ArrivalTime;
                order.setArrivalTime(format.parse(strArrivalTime.replace("T", " ")));
                //人工操作标志 人工操作：1，未曾人工操作：0
                order.setManualFlag(0);
                order.setManualDone(0);
                // 将订单写入数据库
                if (htOrderMapper.insert(order) == 0) {
                    // 写日志
                    orderOplogService.insertOrderOpLog(baseInfo, OplogOperationType.Create.getCode(), "订单信息入库错误，返回体为：" + response.toJSONString(), "订单已创建，但写入数据库时发生错误", ids.getResID_Value());
                } else {
                    //此处同步订单详情到本地库
                    boolean flag = syncOrderDetail(baseInfo, orderId, param.get("moduleId").toString());
                    if (!flag) {
                        orderOplogService.insertOrderOpLog(baseInfo, OplogOperationType.Create.getCode(), response.toJSONString(), "创建订单成功,但是同步携程数据到订单详情失败了", ids.getResID_Value());
                    }
                    orderOplogService.insertOrderOpLog(baseInfo, OplogOperationType.Create.getCode(), response.toJSONString(), "创建订单成功", ids.getResID_Value());
                }
            } else // if (orderResp.Errors.Count == 0)
            {
                orderOplogService.insertOrderOpLog(baseInfo, OplogOperationType.Create.getCode(), "返回json中包含错误，返回体为：" + response.toJSONString(), "创建订单失败", "");
                return response;
            }
        } catch (Exception e) {
            String memo = "";
            if (null != response && response.size() != 0) {
                memo = response.toJSONString();
            } else {
                memo = param.toJSONString();
            }
            orderOplogService.insertOrderOpLog(baseInfo, OplogOperationType.Create.getCode(), "订单创建过程中发生异常：" + memo, "订单创建过程中发生异常:" + e.toString(), "");
            return ValaditionUtils.getApiError();
        }
        return response;
    }

    @Override
    public JSONObject orderSubmit(BaseInfo baseInfo, JSONObject param) {
        JSONObject response = null;
        try {
            SubmitOrderRequest submitOrder = null;
            try {
                submitOrder = JSON.toJavaObject(param, SubmitOrderRequest.class);
            } catch (Exception e) {
                submitOrder = null;
            }
            String orderId = null;
            if (submitOrder != null
                    && "501".equals(submitOrder.ReservationPayment.ReservationID.Type)) {
                orderId = submitOrder.ReservationPayment.ReservationID.ID;
            }
            if (orderId == null) {
                // 写日志
                orderOplogService.insertOrderOpLog(baseInfo, OplogOperationType.Submit.getCode(), param.toJSONString(), "获取订单ID失败", "");
                logger.error("orderSubmit:获取订单ID失败");
                return ValaditionUtils.getApiError("ReservationID.ID不能为空");
            }
//            HtOrder htOrder = htOrderMapper.selectById(orderId);
//            if (null == htOrder) {
//                orderOplogService.insertOrderOpLog(baseInfo, OplogOperationType.Submit.getCode(), param.toJSONString(), "该订单未创建", orderId);
//                logger.error("orderSubmit:该订单未创建：" + orderId);
//                return ValaditionUtils.getParamError("该订单未创建");
//            }
            // 写日志
            orderOplogService.insertOrderOpLog(baseInfo, OplogOperationType.Submit.getCode(), param.toJSONString(), "准备提交订单", orderId);
            // 检查支付结果
            BaseReq baseReqTemp = new BaseReq();
            baseReqTemp.setAID(baseInfo.getAID());
            baseReqTemp.setSID(baseInfo.getSID());
            baseReqTemp.setUUID(baseInfo.getUUID());
            baseReqTemp.setKEY("123");
            baseReqTemp.setModuleId(BusiModuleType.HOTEL.getCode());
            BaseReq baseReq = DistributorCheck.distributorCheck(baseReqTemp);
            if (baseReq == null) {
                orderOplogService.insertOrderOpLog(baseInfo, OplogOperationType.Submit.getCode(), param.toJSONString(), "没有找到分销商（" + baseInfo.getUUID() + "）", orderId);
                logger.error("orderSubmit:获取分销商信息失败");
                return ValaditionUtils.getApiError();
            }

            //根据分销商对应回调地址获取支付结果
            String strUrl = baseReq.getUrl() + "?order_type=1&order_id=" + orderId;
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            HttpEntity<JSONObject> formEntity = new HttpEntity<JSONObject>(param, headers);
            //此处调用分销商支付回调接口
//            JSONObject body = null;
//            try {
//                body = restTemplate.exchange(strUrl, HttpMethod.POST, formEntity, JSONObject.class).getBody();
//            } catch (Exception e) {
//                body = null;
//            }
            PaymentCallbackResponse payResult = null;
            try {
//                payResult = JSON.toJavaObject(body, PaymentCallbackResponse.class);
                //@TODO 此处测试提交订单
                payResult = TestUtils.getCallback(submitOrder.ReservationPayment.PaymentDetail.get(0).PaymentAmount.Amount);
            } catch (Exception ex) {
                payResult = null;
            }
            if (payResult == null || !payResult.is_success) {
                orderOplogService.insertOrderOpLog(baseInfo, OplogOperationType.Submit.getCode(), param.toJSONString(), "获取回调支付结果失败", orderId);
                logger.error("orderSubmit:获取回调支付结果失败");
                return ValaditionUtils.getApiError("获取回调支付结果失败");
            }
            Double amount = submitOrder.ReservationPayment.PaymentDetail.get(0).PaymentAmount.Amount;
            String currencyCode = submitOrder.ReservationPayment.PaymentDetail.get(0).PaymentAmount.CurrencyCode;
            if (!payResult.pay_currency.toUpperCase().equals(currencyCode.toUpperCase())) {
                orderOplogService.insertOrderOpLog(baseInfo, OplogOperationType.Submit.getCode(), param.toJSONString(), "提交币种与支付币种不一致", orderId);
                logger.error("orderSubmit:提交币种与支付币种不一致");
                return ValaditionUtils.getApiError("提交币种与支付币种不一致");
            }
            if (amount != Double.parseDouble(payResult.pay_amount)) {
                orderOplogService.insertOrderOpLog(baseInfo, OplogOperationType.Submit.getCode(), param.toJSONString(), "提交金额与支付金额不一致", orderId);
                logger.error("orderSubmit:提交金额与支付金额不一致");
                return ValaditionUtils.getApiError("提交金额与支付金额不一致");
            }
            if (Double.parseDouble(payResult.pay_amount) == 0) {
                orderOplogService.insertOrderOpLog(baseInfo, OplogOperationType.Submit.getCode(), param.toJSONString(), "该账户预存款余额不足", orderId);
                logger.error("orderSubmit:该账户预存款余额不足");
                return ValaditionUtils.getApiError("该账户预存款余额不足");
            }
            HtOrder order = new HtOrder();
            // 更新订单支付金额
            double fAmount = Double.parseDouble(payResult.pay_amount);
            order.setPayAmount(fAmount);
            order.setPayTime(new Date());
            order.setOrderId(orderId);
            int result = htOrderMapper.updateById(order);
            if (result == 0) {
                orderOplogService.insertOrderOpLog(baseInfo, OplogOperationType.Submit.getCode(), param.toJSONString(), "更新订单支付金额失败", orderId);
                logger.error("orderSubmit:更新订单支付金额失败");
                return ValaditionUtils.getApiError();
            }
            // 检查预付款是否足够支付当前订单

            FinanceResponse financeResponse = remoteService.getBalance(baseInfo,param);
            //@TODO 测试获取余额
//            financeResponse = TestUtils.getBalance();
            if (null == financeResponse) {
                orderOplogService.insertOrderOpLog(baseInfo, OplogOperationType.Submit.getCode(), param.toJSONString(), "获取分销商账户余额失败", orderId);
                logger.error("orderSubmit:财务查询余额接口调用失败");
                return ValaditionUtils.getApiError();
            }
            double fPredeposit = financeResponse.getData().getAccountbalance();
            if (fPredeposit < fAmount) {
                orderOplogService.insertOrderOpLog(baseInfo, OplogOperationType.Submit.getCode(), param.toJSONString(), "预付款不足", orderId);
                return ValaditionUtils.getApiError();
            }
            //支付
            //@TODO 测试进行支付
//            financeResponse = TestUtils.pay();
            financeResponse = remoteService.pay(baseInfo, orderId, "-" + payResult.pay_amount,param);
            if (financeResponse.getStatus() != 100) {
                orderOplogService.insertOrderOpLog(baseInfo, OplogOperationType.Submit.getCode(), param.toJSONString(), "扣款失败", orderId);
                logger.error("orderSubmit:财务支付接口调用失败");
                return ValaditionUtils.getApiError();
            }
            //提交订单
            response = getResponse(param, Enviroment.HOTEL_ORDER_SUBMIT.getValue(), Enviroment.TEST_HOTEL_ORDER_SUBMIT.getValue());
            if (null == response) {
                FinanceResponse pay = remoteService.canclePay(baseInfo, orderId, payResult.pay_amount, financeResponse.getData().getTradedetail(),param);
                if (null == pay) {
                    logger.error("orderSubmit:携程接口调用失败，调用财务接口退款失败");
                }
                orderOplogService.insertOrderOpLog(baseInfo, OplogOperationType.Submit.getCode(), param.toJSONString(), "提交订单失败", orderId);
                logger.error("orderSubmit:携程接口调用失败");
                return ValaditionUtils.getApiError();
            }
            SubmitOrderResponse orderResp = null;
            try {
                orderResp = JSON.toJavaObject(response, SubmitOrderResponse.class);
            } catch (Exception ex) {
                orderResp = null;
            }
            if (null == orderResp) {
                logger.error("orderSubmit:携程返回失败，根据返回json转换实体对象错误");
                orderOplogService.insertOrderOpLog(baseInfo, OplogOperationType.Submit.getCode(), "根据返回json转换实体对象错误，或者携程提交订单应答错误，返回体为：" + response.toJSONString(), "提交订单失败", orderId);
                //取消支付
                FinanceResponse pay = remoteService.canclePay(baseInfo, orderId, payResult.pay_amount, financeResponse.getData().getTradedetail(),param);
                if (null == pay) {
                    logger.error("orderSubmit:携程返回失败，调用财务接口退款失败");
                }
                return ValaditionUtils.getApiError();
            }
            if (null == orderResp.ResponseStatus
                    || !"Success".equals(orderResp.ResponseStatus.Ack)) {
                logger.error("orderSubmit:携程提交订单应答错误");
                orderOplogService.insertOrderOpLog(baseInfo, OplogOperationType.Submit.getCode(), "根据返回json转换实体对象错误，或者携程提交订单应答错误，返回体为：" + response.toJSONString(), "提交订单失败", orderId);
                //取消支付
                FinanceResponse pay = remoteService.canclePay(baseInfo, orderId, payResult.pay_amount, financeResponse.getData().getTradedetail(),param);
                if (null == pay) {
                    logger.error("orderSubmit:携程返回失败，调用财务接口退款失败");
                }
                return response;
            }
            // 这里仅仅表示调用远程方法成功，需要进一步判断创建订单是否成功
            if (orderResp.Errors.size() == 0) {
                // 更新submit时间,订单状态
                order = new HtOrder();
                order.setSubmitTime(new Date());
                order.setOrderState(HotelOrderState.Process.getCode());
                QueryWrapper<HtOrder> orderWrapper = new QueryWrapper<HtOrder>();
                orderWrapper.eq("ORDER_ID", orderId);
                htOrderMapper.update(order, orderWrapper);
                orderOplogService.insertOrderOpLog(baseInfo, OplogOperationType.Submit.getCode(), response.toJSONString(), "提交订单成功", orderId);
            } else {
                //取消支付
                logger.error("orderSubmit:订单提交失败，取消支付");
                FinanceResponse pay = remoteService.canclePay(baseInfo, orderId, payResult.pay_amount, financeResponse.getData().getTradedetail(),param);
                if (null == pay) {
                    logger.error("orderSubmit:携程返回失败，调用财务接口退款失败");
                }
                orderOplogService.insertOrderOpLog(baseInfo, OplogOperationType.Submit.getCode(), "返回中包含错误信息，返回体为：" + response.toJSONString(), "提交订单失败", orderId);
            }
        } catch (Exception e) {
            String memo = "";
            if (null != response && response.size() != 0) {
                memo = response.toJSONString();
            } else {
                memo = param.toJSONString();
            }
            logger.error("orderSubmit:订单提交过程中发生异常，异常信息为：" + e.toString());
            orderOplogService.insertOrderOpLog(baseInfo, OplogOperationType.Submit.getCode(), "提交订单过程中发生异常：" + memo, "提交订单失败：" + e.toString(), "");
            return ValaditionUtils.getApiError();
        }
        return response;
    }

    @Override
    public JSONObject orderStateChange(BaseInfo baseinfo, JSONObject param) {
        JSONObject response = null;
        try {
            //从数据中查询订单状态发生变化的订单
            MonitorOrderStateRequest requestEntity = null;
            try {
                requestEntity = JSON.toJavaObject(param, MonitorOrderStateRequest.class);
            } catch (Exception e) {
                requestEntity = null;
            }
            if (requestEntity == null) {
                return ValaditionUtils.getParamError();
            }
            Date startTime = null;
            Date endTime = null;

            //解析出请求参数
            startTime = format.parse(requestEntity.HotelReservations.get(0).LastModifyDateTime.replace("T", " "));
            endTime = new Date();
            String strTmp = requestEntity.HotelReservations.get(0).EndModifyDateTime;
            if (strTmp != null && !"".equals(strTmp)) {
                endTime = format.parse(strTmp.replace("T", " "));
            }

            long hour = DateUtil.getHourSub(new Date(), startTime);
            if (hour >= 24) {
                ErrorResp errorResp = new ErrorResp();
                errorResp.setErrCode(CtripApiErrorCode.ApiInternalError.getCode());
                errorResp.setErrMsg("Invalid value:LastModifyDateTime must be in 24 hours");
                response = JSONObject.parseObject(JSON.toJSONString(errorResp));
                logger.error("orderStateChange:无效值:最后一次修改时间必须在24小时内");
                return response;
            }
            long minute = DateUtil.getMinuteSub(startTime, endTime);
            if (minute < 0) {
                ErrorResp errorResp = new ErrorResp();
                errorResp.setErrCode(CtripApiErrorCode.ApiInternalError.getCode());
                errorResp.setErrMsg("Invalid value:截止时间不能小于开始时间");
                response = JSONObject.parseObject(JSON.toJSONString(errorResp));
                logger.error("orderStateChange:无效值：截止时间不能小于开始时间");
                return response;
            }

            if (minute > 10) {
                ErrorResp errorResp = new ErrorResp();
                errorResp.setErrCode(CtripApiErrorCode.ApiInternalError.getCode());
                errorResp.setErrMsg("Invalid value:时间内间隔不能大于10分钟");
                response = JSONObject.parseObject(JSON.toJSONString(errorResp));
                logger.error("orderStateChange:无效值：时间内间隔不能大于10分钟");
                return response;
            }
            response = getMonitorOrderStateResponse(baseinfo.getUUID(), startTime, endTime);
        } catch (Exception e) {
            logger.error("orderStateChange:获取状态变化订单过程中发生异常" + e.toString());
            return ValaditionUtils.getApiError();
        }
        return response;
    }

    /**
     * 创建订单时落库时同步订单详情到本地库
     *
     * @param orderId  订单号
     * @param baseInfo 分销商信息
     * @author 胡庆康
     * @date 2019/7/27 15:33
     * @returns
     */
    @Async
    public boolean syncOrderDetail(BaseInfo baseInfo, String orderId, String moduleId) {
        GetOrderDetailRequest request = new GetOrderDetailRequest();
        request.UniqueID = new ArrayList<>();
        GetOrderDetailRequest.Unique unique = new GetOrderDetailRequest.Unique();
        //AllienceID
        unique.setType("28");
        unique.setID(baseInfo.getAID());
        request.UniqueID.add(unique);
        //SiteID
        unique.setType("503");
        unique.setID(baseInfo.getSID());
        request.UniqueID.add(unique);
        //订单号
        unique.setType("501");
        unique.setID(orderId);
        request.UniqueID.add(unique);
        request.setPrimaryLangID("zh-cn");
        request.setTimeStamp(format.format(new Date()));
        request.setVersion("1");
        JSONObject param = JSONObject.parseObject(JSON.toJSONString(request));
        param.put("moduleId", moduleId);
        JSONObject response = getResponse(param, Enviroment.HOTEL_ORDER_DETAIL.getValue(), Enviroment.TEST_HOTEL_ORDER_DETAIL.getValue());
        if (null == response) {
            logger.error("携程订单详情接口调用失败，方法：syncOrderDetail(),订单号为：" + orderId);
            return false;

        }
        GetOrderDetailResponse detailResponse = null;
        try {
            detailResponse = JSON.toJavaObject(response, GetOrderDetailResponse.class);
            if (null == detailResponse) {
                logger.error("订单详情返回json转换实体类为null，方法：syncOrderDetail(),订单号为：" + orderId);
                return false;
            }
        } catch (Exception e) {
            logger.error("订单详情返回json转换实体类时发生异常，方法：syncOrderDetail(),订单号为：" + orderId + ",错误信息为:" + e.toString());
            return false;
        }
        try {
            HtOrderDetail orderDetail = getOrderDetail(orderId, response, detailResponse);
            int result = htOrderDetailMapper.insert(orderDetail);
            if (result == 0) {
                orderOplogService.insertOrderOpLog(baseInfo, OplogOperationType.Create.getCode(), "订单详情入库失败，返回体为：" + response.toJSONString(), "获取订单详情成功，但写入数据库失败了", "");
                return false;
            }
            //订单详情入库成功，同步订单对应酒店名称，房型名称到订单表
            HtOrder order = new HtOrder();
            order.setHotelName(orderDetail.getHotelname());
            order.setRoomName(orderDetail.getRoomtypename());
            QueryWrapper<HtOrder> orderWrapper = new QueryWrapper<HtOrder>();
            orderWrapper.eq("ORDER_ID", orderId);
            htOrderMapper.update(order, orderWrapper);
        } catch (Exception e) {
            orderOplogService.insertOrderOpLog(baseInfo, OplogOperationType.Create.getCode(), "订单详情入库失败，返回体为：" + response.toJSONString(), "获取订单详情成功，但写入数据库失败了", "");
            logger.error("订单详情获取数据过程中发生异常，方法为syncOrderDetail()，订单号为：" + orderId + ",错误信息为:" + e.toString());
        }
        return true;
    }

    @Override
    public JSONObject orderDetail(BaseInfo baseInfo, JSONObject param) {
        JSONObject response = null;
        try {
            String orderId = "";
            response = getResponse(param, Enviroment.HOTEL_ORDER_DETAIL.getValue(), Enviroment.TEST_HOTEL_ORDER_DETAIL.getValue());
            if (null == response) {
                logger.error("orderDetail:获取订单详情失败，调用携程接口失败");
                return ValaditionUtils.getApiError();
            }
            GetOrderDetailResponse detailResponse = null;
            try {
                detailResponse = JSON.toJavaObject(response, GetOrderDetailResponse.class);
            } catch (Exception e) {
                detailResponse = null;
            }
            if (null == detailResponse) {
                logger.error("orderDetail:获取订单详情失败，携程返回失败状态或返回结果转换实体类失败");
                return ValaditionUtils.getApiError();
            }
            if (null == detailResponse.ResponseStatus
                    || !"Success".equals(detailResponse.ResponseStatus.Ack)) {
                logger.error("orderDetail:获取订单详情失败，携程返回失败状态或返回结果转换实体类失败");
                return response;
            }
            if (detailResponse.Errors.size() == 0) {
                //调用订单详情接口成功
                //从请求json中获取订单号
                GetOrderDetailRequest detailRequest = JSON.toJavaObject(param, GetOrderDetailRequest.class);
                for (GetOrderDetailRequest.Unique unique : detailRequest.getUniqueID()) {
                    if ("501".equals(unique.getType())) {
                        orderId = unique.getID();
                    }
                }
                //检查是否为第一次调用，是则保存订单详情，不是则不保存
                HtOrderDetail tempOrderDetail = htOrderDetailMapper.selectById(orderId);
                if (null != tempOrderDetail) {
                    return response;
                }
                HtOrderDetail orderDetail = getOrderDetail(orderId, response, detailResponse);
                int result = htOrderDetailMapper.insert(orderDetail);
                if (result == 0) {
                    logger.error("orderDetail:获取订单详情成功，但写入数据库失败了");
                    return ValaditionUtils.getApiError();
                }
            }
        } catch (Exception e) {
            String memo = "";
            if (null != response && response.size() != 0) {
                memo = response.toJSONString();
            } else {
                memo = param.toJSONString();
            }
            logger.error("orderDetail:获取订单详情过程中发生异常");
            return ValaditionUtils.getApiError();
        }
        return response;
    }

    /**
     * 根据订单号和订单详情返回实体类获取订单详情实体类
     *
     * @param orderId        订单号
     * @param detailResponse 订单详情返回实体类
     * @param response       订单返回json
     * @author 胡庆康
     * @date 2019/7/27 15:28
     * @returns
     */
    public HtOrderDetail getOrderDetail(String orderId, JSONObject response, GetOrderDetailResponse detailResponse) throws Exception {
        HtOrderDetail orderDetail = new HtOrderDetail();
        //订单号
        orderDetail.setOrderId(orderId);
        //详情返回json
        orderDetail.setOrderJson(response.toJSONString());
        for (GetOrderDetailResponse.ReservationsList reservationsList : detailResponse.getReservationsList()) {
            for (GetOrderDetailResponse.RoomStay roomStay : reservationsList.getRoomStays()) {
                //-----------roomStay start-------------
                for (GetOrderDetailResponse.RoomType roomType : roomStay.getRoomTypes()) {
                    //房间类型名称
                    orderDetail.setRoomtypename(roomType.getName());
                    //房间类型代码
                    orderDetail.setRoomtypecode(roomType.getRoomTypeCode());
                    //父床型ID
                    orderDetail.setBedtypecode(roomType.getBedTypeCode().get(0));
                    //售卖房型的预定间数
                    orderDetail.setNumberofunits(roomType.getNumberOfUnits());
                }
                for (GetOrderDetailResponse.RatePlan ratePlan : roomStay.getRatePlans()) {
                    //售卖房型ID/子房型ID
                    orderDetail.setRoomid(ratePlan.getRoomID());
                    //格式化的描述信息
                    if (ratePlan.AdditionalDetails.size() != 0) {
                        orderDetail.setDetaildescription(ratePlan.getAdditionalDetails().get(0).getDetailDescription());
                    }
                }
                //酒店代码
                orderDetail.setHotelcode(roomStay.getBasicPropertyInfo().getHotelCode());
                //酒店名称
                orderDetail.setHotelname(roomStay.getBasicPropertyInfo().getHotelName());
                for (GetOrderDetailResponse.RoomRate roomRate : roomStay.getRoomRates()) {
                    for (GetOrderDetailResponse.Rate rate : roomRate.getRates()) {
                        //基础货币代码
                        orderDetail.setBasecurrencycode(rate.getBase().getCurrencyCode());
                        //基础税前价格
                        orderDetail.setBaseexclusiveamount((float) rate.getBase().getExclusiveAmount());
                        //基础税后价格
                        orderDetail.setBaseinclusiveamount((float) rate.getBase().getInclusiveAmount());
                        //是否含早餐
                        if (rate.getMealsIncluded() != null) {
                            orderDetail.setBreakfast((rate.getMealsIncluded().Breakfast == true) ? 1 : 0);
                            //早餐份数
                            orderDetail.setNumberofbreakfast(rate.getMealsIncluded().getNumberOfBreakfast());
                        }
                        //有效日期
                        orderDetail.setEffectivedate(format.parse(rate.getEffectiveDate().replace("T", " ")));
                    }
                }
                //-----------roomStay end-------------
            }
            //PP-该订单为预付订单；FG-该订单为到付订单
            orderDetail.setBillingcode(reservationsList.getBillingInstructionCode().get(0).BillingCode);
            //宾客人数
            orderDetail.setGuestcount(reservationsList.getResGlobalInfo().getGuestCounts().getGuestCount().getCount());
            //开始时间
            orderDetail.setStartdate(format.parse(reservationsList.getResGlobalInfo().getTimeSpan().getStart()));
            //结束时间
            orderDetail.setEnddate(format.parse(reservationsList.getResGlobalInfo().getTimeSpan().getEnd().replace("T", " ")));
            if (reservationsList.getResGlobalInfo().getCancelPenalties().getCancelPenalty() != null) {
                //取消处罚基础货币代码
                orderDetail.setCpCurrencycode(reservationsList.getResGlobalInfo().getCancelPenalties().getCancelPenalty().getAmountPercent().getDisplayCurrency().get(0).getCurrencyCode());
                //取消处罚基础税前价格
                orderDetail.setCpExclusiveamount((float) reservationsList.getResGlobalInfo().getCancelPenalties().getCancelPenalty().getAmountPercent().getDisplayCurrency().get(0).getExclusiveAmount());
                //取消处罚基础税后价格
                orderDetail.setCpInclusiveamount((float) reservationsList.getResGlobalInfo().getCancelPenalties().getCancelPenalty().getAmountPercent().getDisplayCurrency().get(0).getInclusiveAmount());
                //取消处罚金额
                orderDetail.setCpAmount((float) reservationsList.getResGlobalInfo().getCancelPenalties().getCancelPenalty().getAmountPercent().getAmount());
                //取消处罚开始时间
                orderDetail.setCpStartdate(format.parse(reservationsList.getResGlobalInfo().getCancelPenalties().getCancelPenalty().getStart().replace("T", " ")));
                //取消处罚结束时间
                orderDetail.setCpStartdate(format.parse(reservationsList.getResGlobalInfo().getCancelPenalties().getCancelPenalty().getEnd().replace("T", " ")));
            }
            //放弃预定
            orderDetail.setNoshowpaid((float) reservationsList.getResGlobalInfo().getTotal().getTPAExtensions().getNoShowPaid().getAmount());
            //总计货币代码
            orderDetail.setTotalcurrencycode(reservationsList.getResGlobalInfo().getTotal().getCurrencyCode());
            //税前价格总计
            orderDetail.setTotalexclusiveamount((float) reservationsList.getResGlobalInfo().getTotal().getExclusiveAmount());
            //税后价格总计
            orderDetail.setTotalinclusiveamount((float) reservationsList.getResGlobalInfo().getTotal().getInclusiveAmount());
            //宾客名称
            String customerNames = "";
            for (GetOrderDetailResponse.PersonName personName : reservationsList.ResGuests.ResGuest.Profiles.ProfileInfo.Profile.Customer.PersonName) {
                customerNames += customerNames + personName.getName() + ",";
            }
            orderDetail.setCustomername(customerNames.substring(0, customerNames.length() - 1));
            if (reservationsList.ResGuests.ResGuest.Profiles.ProfileInfo.Profile.Customer.ContactPerson.size() != 0) {
                //联系人名称
                orderDetail.setContactperson(reservationsList.ResGuests.ResGuest.Profiles.ProfileInfo.Profile.Customer.ContactPerson.get(0).PersonName.getName());
                //联系人手机号
                orderDetail.setContactphone(reservationsList.ResGuests.ResGuest.Profiles.ProfileInfo.Profile.Customer.ContactPerson.get(0).Telephone.get(0).PhoneNumber);
                //联系人邮箱
                orderDetail.setContactemail(reservationsList.ResGuests.ResGuest.Profiles.ProfileInfo.Profile.Customer.ContactPerson.get(0).Email);
            }
            //最晚到店时间
            String lateArrivalTime = reservationsList.ResGuests.ResGuest.TPAExtensions.LateArrivalTime;
            if (org.apache.commons.lang.StringUtils.isNotBlank(lateArrivalTime)) {
                orderDetail.setLatearrivaltime(format.parse(lateArrivalTime.replace("T", " ")));
            }
            //最早到店时间
            String arrivalTime = reservationsList.ResGuests.ResGuest.ArrivalTime;
            if (org.apache.commons.lang.StringUtils.isNotBlank(arrivalTime)) {
                orderDetail.setArrivaltime(format.parse(arrivalTime.replace("T", " ")));
            }
            //订单创建时间
            orderDetail.setCreatedate(format.parse(reservationsList.getCreateDateTime().replace("T", " ")));
            //最后一次修改时间
            orderDetail.setLastmodifydate(format.parse(reservationsList.getLastModifyDateTime().replace("T", " ")));
            //订单状态
            orderDetail.setOrderstatus(reservationsList.getOrderStatus());
        }
        orderDetail.setContacttype(ContactType.Non.getCode());
        return orderDetail;
    }

    /**
     * 获取订单详情实体类
     *
     * @param uuid      分销商身份标识
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @author 胡庆康
     * @date: 2019/7/19 16:10
     * @returns
     */
    public JSONObject getMonitorOrderStateResponse(String uuid, Date startTime, Date endTime) {
        List<HtOrder> orderList = null;
        QueryWrapper<HtOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("DIST_UUID", uuid);
        queryWrapper.isNull("ORDER_STATE_SYNC_FLAG");
        queryWrapper.ne("ORDER_STATE", 0);
        queryWrapper.between("LAST_UPDATE_STATE_TIME", startTime, endTime);
        orderList = htOrderMapper.selectList(queryWrapper);
        HtOrder htOrder = new HtOrder();
        //修改同步状态    0->1
        htOrder.setOrderStateSyncFlag(1);
        MonitorOrderStateResponse response = new MonitorOrderStateResponse();
        htOrderMapper.update(htOrder, queryWrapper);
        GeneralEntity.ResponseStatus responseStatus = new GeneralEntity.ResponseStatus();
        responseStatus.setAck("1232");
        response.setResponseStatus(responseStatus);
        System.out.println(response);
        response.setResponseStatus(responseStatus);
        response.ResponseStatus.setAck("Success");
        response.ResponseStatus.setTimestamp(new Timestamp(System.currentTimeMillis()).toString());
        //从订单列表中拿出需要的数据
        if (orderList != null && orderList.size() > 0) {
            for (HtOrder order : orderList) {
                MonitorOrderStateResponse.HotelReservation hotelResv = new MonitorOrderStateResponse.HotelReservation();
                hotelResv.setResGlobalInfo(new MonitorOrderStateResponse.ResGlobalInfo());
                hotelResv.ResGlobalInfo.setHotelReservationIDs(new ArrayList<>(16));
                hotelResv.setResStatus("S");
                hotelResv.setLastModifyDateTime(format.format(order.getLastUpdateStateTime()));
                MonitorOrderStateResponse.HotelReservationID hotelResvId = new MonitorOrderStateResponse.HotelReservationID();
                if (!"".equals(order.getOrderId501())) {
                    hotelResvId.setResIDType("501");
                    hotelResvId.setResIDValue(order.getOrderId501());
                    hotelResv.ResGlobalInfo.HotelReservationIDs.add(hotelResvId);
                }
                if (!"".equals(order.getOrderId502())) {
                    hotelResvId.setResIDType("502");
                    hotelResvId.setResIDValue(order.getOrderId502());
                    hotelResv.ResGlobalInfo.HotelReservationIDs.add(hotelResvId);
                }
                if (!"".equals(order.getOrderId507())) {
                    hotelResvId.setResIDType("507");
                    hotelResvId.setResIDValue(order.getOrderId507());
                    hotelResv.ResGlobalInfo.HotelReservationIDs.add(hotelResvId);
                }
                if (!"".equals(order.getOrderId509())) {
                    hotelResvId.setResIDType("509");
                    hotelResvId.setResIDValue(order.getOrderId509());
                    hotelResv.ResGlobalInfo.HotelReservationIDs.add(hotelResvId);
                }
                response.HotelReservations.add(hotelResv);
            }
        }
        response.setTimeStamp(new Timestamp(System.currentTimeMillis()).toString());
        response.setVersion("1");
        response.setPrimaryLangID("zh-cn");
        //实体类转JsonObject
        JSONObject result = JSONObject.parseObject(JSON.toJSONString(response));

        return result;
    }

    /**
     * 获取订单详情实体类
     *
     * @param strOrderId 订单号
     * @author: 胡庆康
     * @date: 2019/7/22 16:56
     * @returns:
     */
    @Override
    public GetOrderDetailResponse getOrderDetailResponse(String strOrderId, String moduleId) {
        BaseApiReq apiReq = Enviroment.getBaseApiReq(moduleId);
        GetOrderDetailRequest detailRequest = new GetOrderDetailRequest();
        detailRequest.setUniqueID(new ArrayList<>(16));
        GetOrderDetailRequest.Unique unique = new GetOrderDetailRequest.Unique();
        unique.setType("28");
        unique.setID(apiReq.getAID());
        detailRequest.getUniqueID().add(unique);
        unique = new GetOrderDetailRequest.Unique();
        unique.setType("503");
        unique.setID(apiReq.getSID());
        detailRequest.getUniqueID().add(unique);
        unique.setType("501");
        unique.setID(strOrderId);
        detailRequest.getUniqueID().add(unique);
        detailRequest.setTimeStamp(new Timestamp(System.currentTimeMillis()).toString());
        detailRequest.setVersion("1");
        detailRequest.setPrimaryLangID("zh-cn");
        JSONObject param = JSONObject.parseObject(JSON.toJSONString(detailRequest));
        param.put("moduleId", moduleId);
        JSONObject response = getResponse(param, Enviroment.HOTEL_ORDER_DETAIL.getValue(), Enviroment.TEST_HOTEL_ORDER_DETAIL.getValue());
        if (null == response) {
            logger.error("GetOrderDetailResponse():获取订单详情实体类方法调用携程接口失败");
            return null;
        }
        // 订单详情接口，新增了是否可以取消的字段，可以直接依赖
        // TPA_Extensions.OrderTags.Code   Code为"AllowCancel"，代表该字段含义为“是否可取消”
        // TPA_Extensions.OrderTags.Value  为T时，该订单可取消；为F时，该订单不可取消。
        GetOrderDetailResponse detailResponse = null;
        try {
            detailResponse = JSON.toJavaObject(response, GetOrderDetailResponse.class);
        } catch (Exception e) {
            logger.error("GetOrderDetailResponse():获取订单详情实体类方法json转实体类发生异常：" + e.toString());
            detailResponse = null;
        }
        return detailResponse;
    }

    /**
     * 远程调用公共方法
     *
     * @param param  json参数
     * @param master 正式icode
     * @param test   测试icode
     * @author 胡庆康
     * @date: 2019/7/10 11:55
     * @returns com.alibaba.fastjson.JSONObject
     */
    @Override
    public JSONObject getResponse(JSONObject param, String master, String test) {
        RestTemplate restTemplate = new RestTemplate();
        String token = "";
        JSONObject body;
        String url = Enviroment.API_COMMON_URL.getValue();
        String moduleId = null;
        if (param.containsKey("moduleId")) {
            moduleId = param.get("moduleId").toString();
        } else {
            logger.info("moduleId不能为空");
            return null;
        }
        if ("0".equals(moduleId)) {
            token = stringRedisTemplate.opsForValue().get("token");
            url = url.replace("{app_path}", Enviroment.URL.getValue())
                    .replace("{aid}", Enviroment.AID.getValue())
                    .replace("{sid}", Enviroment.SID.getValue())
                    .replace("{key}", Enviroment.KEY.getValue())
                    .replace("{format}", Enviroment.FORMAT.getValue())
                    .replace("{GUID}", Enviroment.UUID.getValue())
                    .replace("{Access_Token}", token)
                    .replace("{ICODE}", master);
        } else if ("1".equals(moduleId)) {
            token = stringRedisTemplate.opsForValue().get("overseasToken");
            url = url.replace("{app_path}", Enviroment.URL.getValue())
                    .replace("{aid}", Enviroment.OVERSEAS_AID.getValue())
                    .replace("{sid}", Enviroment.OVERSEAS_SID.getValue())
                    .replace("{key}", Enviroment.OVERSEAS_KEY.getValue())
                    .replace("{format}", Enviroment.FORMAT.getValue())
                    .replace("{GUID}", Enviroment.OVERSEAS_UUID.getValue())
                    .replace("{Access_Token}", token)
                    .replace("{ICODE}", master);
        } else {
            logger.info("非法moduleId值");
            return null;
        }
        /* 没有测试环境
        if (Enviroment.IS_RELEASE_ENV) {
            token = stringRedisTemplate.opsForValue().get("token");
        } else {
            token = stringRedisTemplate.opsForValue().get("testToken");
        }
        if (Enviroment.IS_RELEASE_ENV) {
            url = url.replace("{app_path}", Enviroment.URL.getValue())
                    .replace("{aid}", Enviroment.AID.getValue())
                    .replace("{sid}", Enviroment.SID.getValue())
                    .replace("{key}", Enviroment.KEY.getValue())
                    .replace("{format}", Enviroment.FORMAT.getValue())
                    .replace("{GUID}", Enviroment.UUID.getValue())
                    .replace("{Access_Token}", token)
                    .replace("{ICODE}", master);
        } else {
            url = url.replace("{app_path}", Enviroment.TEST_URL.getValue())
                    .replace("{aid}", Enviroment.TEST_AID.getValue())
                    .replace("{sid}", Enviroment.TEST_SID.getValue())
                    .replace("{key}", Enviroment.TEST_KEY.getValue())
                    .replace("{format}", Enviroment.TEST_FORMAT.getValue())
                    .replace("{GUID}", Enviroment.TEST_UUID.getValue())
                    .replace("{Access_Token}", token)
                    .replace("{ICODE}", test);
        }
        */
        if (Enviroment.HOTEL_BOOKABLE_CHECK.getValue().equals(master)) {
            url = "http://hotel.tianxiafen.com:82/OpenService/FenHotelService.ashx?AID=1&SID=101&ICODE=hotel.bookable.check&UUID=A63FA038-32B2-46C7-A363-42E651378752&Token={{Token}}&mode=1&format=JSON".replace("{{Token}}", token);
        }
        if (Enviroment.HOTEL_ORDER_CREATE.getValue().equals(master)) {
            url = "http://hotel.tianxiafen.com:82/OpenService/FenHotelService.ashx?AID=1&SID=101&ICODE=hotel.order.create&UUID=A63FA038-32B2-46C7-A363-42E651378752&Token={{Token}}&mode=1&format=JSON".replace("{{Token}}", token);
        }
        if (Enviroment.HOTEL_ORDER_SUBMIT.getValue().equals(master)) {
            url = "http://hotel.tianxiafen.com:82/OpenService/FenHotelService.ashx?AID=1&SID=101&ICODE=hotel.order.submit&UUID=A63FA038-32B2-46C7-A363-42E651378752&Token={{Token}}&mode=1&format=JSON".replace("{{Token}}", token);
        }
        if (Enviroment.HOTEL_ORDER_STATECHANGE.getValue().equals(master)) {
            url = "http://hotel.tianxiafen.com:82/OpenService/FenHotelService.ashx?AID=1&SID=101&ICODE=hotel.order.statechange&UUID=A63FA038-32B2-46C7-A363-42E651378752&Token={{Token}}&mode=1&format=JSON".replace("{{Token}}", token);
        }
        if (Enviroment.HOTEL_ORDER_DETAIL.getValue().equals(master)) {
            url = "http://hotel.tianxiafen.com:82/OpenService/FenHotelService.ashx?AID=1&SID=101&ICODE=hotel.order.detail&UUID=A63FA038-32B2-46C7-A363-42E651378752&Token={{Token}}&mode=1&format=JSON".replace("{{Token}}", token);
        }
        if ("1".equals(moduleId)) {
            url = url.replace("hotel.tianxiafen.com:82", "hotel.tianxiafen.com:83");
        }
//        url="http://hotel.tianxiafen.com:82/OpenService/FenHotelService.ashx?AID=1&SID=101&ICODE=hotel.order.detail&UUID=A63FA038-32B2-46C7-A363-42E651378752&Token=c574641dde6d4113bdff8b44fbb8d7f9&mode=1&format=JSON";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<JSONObject> formEntity = new HttpEntity<JSONObject>(param, headers);
        try {
            body = restTemplate.exchange(url, HttpMethod.POST, formEntity, JSONObject.class).getBody();
        } catch (Exception e) {
            return null;
        }
        return body;
    }

    @Override
    public JSONObject getResponseNew(JSONObject param, String master, String test) {
        RestTemplate restTemplate = new RestTemplate();
        String token = "";
        String overseasToken = "";
        JSONObject body;
        String url = Enviroment.API_FN_COMMON_URL.getValue();
        if (Enviroment.IS_RELEASE_ENV) {
            token = stringRedisTemplate.opsForValue().get("token");
            overseasToken = stringRedisTemplate.opsForValue().get("overseasToken");
        } else {
            token = stringRedisTemplate.opsForValue().get("testToken");
            overseasToken = stringRedisTemplate.opsForValue().get("testOverseasToken");
        }
        String moduleId = param.getString("moduleId");
        if (!StringUtils.hasText(moduleId)) {
            return null;
        }
        //0 国内, 1 国际
        if ("0".equals(moduleId)) {
            if (Enviroment.IS_RELEASE_ENV) {
                url = url.replace("{app_path}", Enviroment.FN_URL.getValue())
                        .replace("{aid}", Enviroment.AID.getValue())
                        .replace("{sid}", Enviroment.FN_SID.getValue())
                        .replace("{key}", Enviroment.KEY.getValue())
                        .replace("{format}", Enviroment.FORMAT.getValue())
                        .replace("{GUID}", Enviroment.UUID.getValue())
                        .replace("{ICODE}", master);
            } else {
                url = url.replace("{app_path}", Enviroment.TEST_URL.getValue())
                        .replace("{aid}", Enviroment.TEST_AID.getValue())
                        .replace("{sid}", Enviroment.TEST_SID.getValue())
                        .replace("{key}", Enviroment.TEST_KEY.getValue())
                        .replace("{format}", Enviroment.TEST_FORMAT.getValue())
                        .replace("{GUID}", Enviroment.TEST_UUID.getValue())
                        .replace("{ICODE}", test);
            }
            url = url.replace("{Access_Token}", token);
        } else if ("1".equals(moduleId)) {
            if (Enviroment.IS_RELEASE_ENV) {
                url = url.replace("{app_path}", Enviroment.FN_URL.getValue())
                        .replace("{aid}", Enviroment.OVERSEAS_AID.getValue())
                        .replace("{sid}", Enviroment.OVERSEAS_SID.getValue())
                        .replace("{key}", Enviroment.OVERSEAS_KEY.getValue())
                        .replace("{format}", Enviroment.FORMAT.getValue())
                        .replace("{GUID}", Enviroment.OVERSEAS_UUID.getValue())
                        .replace("{ICODE}", master);
            } else {
                url = url.replace("{app_path}", Enviroment.TEST_URL.getValue())
                        .replace("{aid}", Enviroment.TEST_AID.getValue())
                        .replace("{sid}", Enviroment.TEST_SID.getValue())
                        .replace("{key}", Enviroment.TEST_KEY.getValue())
                        .replace("{format}", Enviroment.TEST_FORMAT.getValue())
                        .replace("{GUID}", Enviroment.TEST_UUID.getValue())
                        .replace("{ICODE}", test);
            }
            url = url.replace("{Access_Token}", overseasToken);
        }


        param.remove("moduleId");
        //url = "http://hotel.tianxiafen.com:82/OpenService/FenHotelService.ashx?AID=1&SID=101&ICODE=hotel.order.detail&UUID=A63FA038-32B2-46C7-A363-42E651378752&Token=03af678a95954d448845e0dc858d5dec&mode=1&format=JSON";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<JSONObject> formEntity = new HttpEntity<JSONObject>(param, headers);
        try {
            body = restTemplate.exchange(url, HttpMethod.POST, formEntity, JSONObject.class).getBody();
        } catch (Exception e) {
            return null;
        }
        return body;
    }

    /**
     * 订单同步订单详情数据
     *
     * @param
     * @author 胡庆康
     * @date 2019/7/25 17:29
     * @returns
     */
    @Override
    public JSONObject orderSync() {
        GetOrderDetailRequest request = new GetOrderDetailRequest();
        request.setUniqueID(new ArrayList<>());
        GetOrderDetailRequest.Unique unique = new GetOrderDetailRequest.Unique();
        unique.setType("501");
        unique.setID("");
        request.UniqueID.add(unique);
        JSONObject response = new JSONObject();
        QueryWrapper<HtOrder> queryWrapper = new QueryWrapper<>();
        List<HtOrder> listOrder = htOrderMapper.selectList(queryWrapper);
        int success = 0;
        int error = 0;
        BaseInfo baseInfo = new BaseInfo();
        baseInfo.setUUID("Api");
        try {
            for (HtOrder order : listOrder) {
                String orderId = order.getOrderId();
                request.UniqueID.get(0).setID(orderId);
                JSONObject result = orderDetail(baseInfo, JSONObject.parseObject(JSON.toJSONString(request)));
                logger.info("orderSync:订单号：" + orderId + ",result:" + result);
                if (result.containsKey("errCode")) {
                    error += 1;
                    System.out.println("errorOrder" + orderId + "-----------" + result.toJSONString() + "---------------");
                } else {
                    success += 1;
                }
            }
        } catch (Exception e) {
            logger.error(e.toString());
        }
        response.put("error", error);
        response.put("success", success);
        response.put("total", listOrder.size());
        return response;
    }


    @Override
    @Transactional
    public Object cancelOrder(BaseInfo baseReqVo, JSONObject orderRefundReqVo) {
        //1.转化为具体的实体
        if (orderRefundReqVo == null) {
            return new ErrorResp().paramsError();
        }
        String moduleId = (String) orderRefundReqVo.get("moduleId");
        if (!StringUtils.hasText(moduleId)) {
            logger.error("orderRefundReqVo:" + orderRefundReqVo);
            return new ErrorResp().paramsError();
        }
        OrderCancelReq orderCancelReq;
        try {
            orderCancelReq = JSON.toJavaObject(orderRefundReqVo, OrderCancelReq.class);
        } catch (Exception e) {
            logger.error("取消订单Json转换异常: 入参: orderRefundReqVo:" + orderRefundReqVo.toString());
            logger.error("取消订单Json转换异常: 异常信息e :" + e);
            return new ErrorResp().paramsError();
        }
        //TODO 2.替换授权信息为弗恩在携程的账号UUID
//        baseReqVo.setUUID(Enviroment.TEST_UUID.getValue());
        String orderID = null;
        if (null != orderCancelReq && !CollectionUtils.isEmpty(orderCancelReq.getUniqueID())) {
            for (UniqueID uniqueID : orderCancelReq.getUniqueID()) {
                if (uniqueID.getType().equals(HotelUniqueIDType.ORDER_NO.getCode())) {
                    orderID = uniqueID.getID();
                    break;
                }
            }
        }

        if (!StringUtils.hasText(orderID)) {
            //获取订单ID失败 日志
            htOrderOplogService.createHtLogger(baseReqVo, orderRefundReqVo, "获取订单ID失败", orderID);
            return new ErrorResp(HtErrorType.GET_ORDERID_FAILURE.getCode(), HtErrorType.GET_ORDERID_FAILURE.getName());
        }

        //3.准备取消订单 日志
        htOrderOplogService.createHtLogger(baseReqVo, orderRefundReqVo, "准备取消订单", orderID);

        //4.找回订单
        HtOrder htOrder = htOrderMapper.selectById(orderID);
        if (null == htOrder) {
            //获取订单失败 日志
            htOrderOplogService.createHtLogger(baseReqVo, orderRefundReqVo, "获取订单失败", orderID);
            return new ErrorResp(HtErrorType.CANCEL_ORDER_FAILURE.getCode(), "获取订单失败：序列不包含任何元素");
        }

        //5.检查订单是否可以取消
        //Q：确认中的订单可以取消吗？
        //A：“确认中”的订单不受取消政策限制；
        //“已确认”只能取消符合取消政策的；“已成交”不可取消。
        //凡是通过接口取消的，都是免费取消。
        if (htOrder.getOrderState() == HotelOrderStateTypeDesc.CANCEL.getCode()
                || htOrder.getOrderState() == HotelOrderStateTypeDesc.SUCCESS.getCode()) {
            String errorStr = "不能取消订单，订单状态为" + HotelOrderStateTypeDesc.getByCode(htOrder.getOrderState()).getName();
            //订单不可取消 日志
            htOrderOplogService.createHtLogger(baseReqVo, orderRefundReqVo, errorStr, orderID);
            //TODO 未知错误码
            return new ErrorResp(HtErrorType.CANCEL_ORDER_FAILURE.getCode(), errorStr);
        }
        if (!checkOrderCancelable(orderID, moduleId)) {
            //订单不可取消 日志
            String errorStr = "订单不可取消";
            htOrderOplogService.createHtLogger(baseReqVo, orderRefundReqVo, errorStr, orderID);
            return new ErrorResp(HtErrorType.CANCEL_ORDER_FAILURE.getCode(), errorStr);
        }
        double fRefundAmount = htOrder.getPayAmount();
        if (fRefundAmount < 0.0) {
            String errorStr = "获取订单部分退款金额失败";
            htOrderOplogService.createHtLogger(baseReqVo, orderRefundReqVo, errorStr, orderID);
            return new ErrorResp(HtErrorType.CANCEL_ORDER_FAILURE.getCode(), errorStr);
        }
        // 6.调整分销商预存款

        //手动开启事务
//        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
        //分销商添加预付款余额(订单退款)
//        HtDistributor distributor = new HtDistributor();
//        UpdateWrapper<HtDistributor> distWrapper = new UpdateWrapper<HtDistributor>();
//        distWrapper.eq("UUID", baseReqVo.getUUID());
//        distWrapper.setSql("PREDEPOSIT=PREDEPOSIT+" + fRefundAmount);

        String errorStr = null;
        //不在使用分销商表的预付款,改用智科
        BaseInfo baseInfo = new BaseInfo();
        BeanUtils.copyProperties(baseReqVo, baseInfo);
        FinanceResponse pay = null;
        try {
            pay = remoteService.pay(baseInfo, orderID, String.valueOf(fRefundAmount),orderRefundReqVo);
        } catch (Exception e) {
            logger.error("调用支付接口失败,参数:baseInfo:" + baseInfo.toString() + ";orderID:" + orderID + ";退款金额:" + String.valueOf(fRefundAmount));
            htOrderOplogService.createHtLogger(baseReqVo, orderRefundReqVo, "退款失败:", orderID);
            return new ErrorResp(HtErrorType.CANCEL_ORDER_FAILURE.getCode(), errorStr);
        }
        if (null != pay && pay.getStatus() != 100) {
            logger.error("取消订单,智科账户退款失败");
            htOrderOplogService.createHtLogger(baseReqVo, orderRefundReqVo, "退款失败:", orderID);
            return new ErrorResp(HtErrorType.CANCEL_ORDER_FAILURE.getCode(), errorStr);
        }

//        try {
//            htDistributorMapper.update(distributor, distWrapper);
//            result = 1;
//        } catch (Exception e) {
//            errorStr = e.getMessage();
//        }
        //7. 调用携程 取消订单
        boolean resp = false;
        JSONObject orderCommon = null;
        try {
            //UniqueID  TODO 改为携程接口后  需删除  start
            UniqueID uniqueID = new UniqueID();
            uniqueID.setID("A63FA038-32B2-46C7-A363-42E651378752");
            uniqueID.setType("1");
            List<UniqueID> list = new ArrayList<>();
            list.add(uniqueID);
            UniqueID uniqueID2 = new UniqueID();
            uniqueID2.setType("28");
            uniqueID2.setID("1");
            list.add(uniqueID2);
            UniqueID uniqueID3 = new UniqueID();
            uniqueID3.setType("503");
            uniqueID3.setID("101");
            list.add(uniqueID3);
            UniqueID uniqueID4 = new UniqueID();
            uniqueID4.setType("501");
            uniqueID4.setID(orderID);
            list.add(uniqueID4);
            orderRefundReqVo.put("UniqueID", list);
            //end
            orderCommon = ctripCancelOrder(orderRefundReqVo);
            if (null != orderCommon && null != orderCommon.get("Success")) {
                resp = true;
            }
        } catch (Exception e) {
            logger.error("调用携程取消订单失败:" + e);
        }
        //如果返回 空 Success 对象则表示调用成功
        if (!resp) {
            // 订单取消失败 日志
            //取消支付
            remoteService.canclePay(baseInfo, orderID, "-" + String.valueOf(fRefundAmount), pay.getData().getTradedetail(),orderRefundReqVo);
            logger.info("取消退款成功");
            // 写日志
            {
                errorStr = "取消订单失败";
                htOrderOplogService.createHtLogger(baseReqVo, orderRefundReqVo, errorStr, orderID);
                if (orderCommon != null) {
                    return orderCommon;
                }
                return new ErrorResp(HtErrorType.CANCEL_ORDER_FAILURE.getCode(), errorStr);
            }
        }
        if (null != orderCommon) {
            List errors = (List) orderCommon.get("Errors");
            if (CollectionUtils.isEmpty(errors)) {
                //提交退款事务
                //更新取消时间与订单状态
                HtOrder order = new HtOrder();
                order.setCancelTime(new Date());
                order.setOrderId(orderID);
                order.setOrderState(HotelOrderState.Cancel.getCode());
                //  源码中最后更新时间是:"1970-01-01 00:00:00"
                String lastUpdateStateTime = "1970-01-01 00:00:00";
                Date lastUpdateDate = DateUtil.strToDate(lastUpdateStateTime, "yyyy-mm-dd HH:mm:ss");
                order.setLastUpdateStateTime(lastUpdateDate);
                htOrderMapper.updateById(order);
                //订单取消成功日志
                htOrderOplogService.createHtLogger(baseReqVo, orderRefundReqVo, "取消订单成功", orderID);
                return orderCommon;
            } else {
                //取消订单失败,回滚事务
                //取消支付
                remoteService.canclePay(baseInfo, orderID, "-" + String.valueOf(fRefundAmount), pay.getData().getTradedetail(),orderRefundReqVo);
                logger.info("取消退款成功");
                // 写日志
                {
                    errorStr = "取消订单失败";
                    htOrderOplogService.createHtLogger(baseReqVo, orderRefundReqVo, errorStr, orderID);
                    return new ErrorResp(HtErrorType.CANCEL_ORDER_FAILURE.getCode(), errorStr);
                }
            }
        } else {
            //取消订单失败,回滚事务
            //取消支付
            remoteService.canclePay(baseInfo, orderID, "-" + String.valueOf(fRefundAmount), pay.getData().getTradedetail(),orderRefundReqVo);
            logger.info("取消退款成功");
            // 写日志
            {
                errorStr = "取消订单失败";
                htOrderOplogService.createHtLogger(baseReqVo, orderRefundReqVo, errorStr, orderID);
                return new ErrorResp(HtErrorType.CANCEL_ORDER_FAILURE.getCode(), errorStr);
            }
        }
    }

    /**
     * @Copyright (C), 2019, 弗恩天下
     * @Author: 渠猛
     * @Date: 2019/7/23 0023 上午 11:41
     * @Description: 检查订单是否可以取消
     */
    private Boolean checkOrderCancelable(String strOrderId, String moduleId) {

        boolean bCanCancel = false;
        // TPA_Extensions.OrderTags.Code   Code为"AllowCancel"，代表该字段含义为“是否可取消”
        // TPA_Extensions.OrderTags.Value  为T时，该订单可取消；为F时，该订单不可取消。
        //string strOrderTag = "{\"Code\":\"AllowCancel\",\"Value\":\"T\"}";
        //if (strResponse4Detail.IndexOf(strOrderTag) == -1)
        //Model.OrderDetail.GetOrderDetailResponse detailResponse = null;
        GetOrderDetailResponse orderDetailResponse = getOrderDetailResponse(strOrderId, moduleId);
        if (null != orderDetailResponse && null != orderDetailResponse.ResponseStatus
                && orderDetailResponse.ResponseStatus.Ack.equals("Success")
                && !CollectionUtils.isEmpty(orderDetailResponse.ReservationsList)
                && !CollectionUtils.isEmpty(orderDetailResponse.ReservationsList.get(0).TPAExtensions.OrderTags)) {
            for (GetOrderDetailResponse.OrderTag orderTag : orderDetailResponse.ReservationsList.get(0).TPAExtensions.OrderTags) {
                if (orderTag.Code.equals("AllowCancel")
                        && orderTag.Value.equals(AllowCancelType.CAN.getCode())) {
                    bCanCancel = true;
                    break;
                }
            }
        }
        return bCanCancel;
    }
}
