package com.fntx.order.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fntx.common.constant.Enviroment;
import com.fntx.common.constant.HotelOrderState;
import com.fntx.common.constant.OplogOperationType;
import com.fntx.common.domain.BaseApiReq;
import com.fntx.common.utils.DateUtil;
import com.fntx.order.dao.HtOrderMapper;
import com.fntx.order.dao.HtOrderOplogMapper;
import com.fntx.order.domain.HtOrder;
import com.fntx.order.domain.HtOrderOplog;
import com.fntx.order.po.GetOrderDetailResponse;
import com.fntx.order.po.MonitorOrderStateRequest;
import com.fntx.order.po.MonitorOrderStateResponse;
import com.fntx.order.service.IOrderService;
import com.fntx.order.service.impl.OrderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @FileName:
 * @Author: 胡庆康
 * @Date: 2019/7/19 18:49
 */
@Component
public class OrderStatusSyncTask {

    @Autowired
    private StringRedisTemplate StringRedisTemplate;

    @Autowired
    private HtOrderMapper htOrderMapper;
    @Autowired
    private HtOrderOplogMapper htOrderOplogMapper;

    private final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    /**
     * 国内模块id
     */
    public static final String INLAND_MOUDLE_ID = "0";
    /**
     * 订单状态变化时间最大间隔
     */
    public static final int TIME_INTERVAL_MAX = 24;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Value("${order.status.sync.frequency}")
    private Integer minute;

    @Autowired
    private IOrderService iOrderService;

    //    @Scheduled(cron = "${order.status.sync.timer}")
    public void syncMain() {
        //同步国内账号数据
        syncProcess("0");
        //同步国外账号数据
        syncProcess("1");
    }

    public void syncProcess(String moduleId) {
        // 定义订单状态发生变化的时间区间的起始时间，起始时间必须在距离当前时间24小时以内。
        Date dtLastModifyDateTime = null;
        Date curTime = new Date();
        String strLastModifyDateTime = null;
        if (INLAND_MOUDLE_ID.equals(moduleId)) {
            // 获取最后同步时间 国内账号
            StringRedisTemplate.opsForValue().get("LastModifyDateTime");
        } else {
            // 获取最后同步时间 海外账号
            StringRedisTemplate.opsForValue().get("overseas:LastModifyDateTime");
        }

        Calendar calendar = Calendar.getInstance();
        if (null != strLastModifyDateTime && !"".equals(strLastModifyDateTime)) {
            try {
                dtLastModifyDateTime = format.parse(strLastModifyDateTime.replace("T", " "));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            calendar.setTime(curTime);
            calendar.add(Calendar.HOUR, -24);
            dtLastModifyDateTime = calendar.getTime();
            calendar.setTime(dtLastModifyDateTime);
            calendar.add(Calendar.SECOND, 60);
            dtLastModifyDateTime = calendar.getTime();
        }
        long hour = DateUtil.getHourSub(curTime, dtLastModifyDateTime);
        // 起始时间必须在距离当前时间24小时以内
        if (hour > TIME_INTERVAL_MAX) {
            calendar.setTime(curTime);
            calendar.add(Calendar.HOUR, -24);
            dtLastModifyDateTime = calendar.getTime();
            calendar.setTime(dtLastModifyDateTime);
            calendar.add(Calendar.SECOND, 60);
            dtLastModifyDateTime = calendar.getTime();
        }
        Date dtEndTime;
        while (dtLastModifyDateTime.compareTo(curTime) < 0) {
            // 起始时间必须与上一次的截止时间有重合部分
            calendar.setTime(dtLastModifyDateTime);
            calendar.add(Calendar.SECOND, -10);
            dtLastModifyDateTime = calendar.getTime();
            calendar.add(Calendar.MINUTE, minute);
            dtEndTime = calendar.getTime();
            if (dtEndTime.compareTo(curTime) > 0) {
                dtEndTime = curTime;
            }
            String strBeginTime = DateUtil.dateToStr(dtLastModifyDateTime, "yyyy-MM-dd HH:mm:ss");
            String strEndTime = DateUtil.dateToStr(dtEndTime, "yyyy-MM-dd HH:mm:ss");
            //@TODO 时间问题待修改
            boolean flag = SyncOrderState(strBeginTime, strEndTime, moduleId);
            if (flag) {
                if ("0".equals(moduleId)) {
                    // 更新最后同步时间
                    StringRedisTemplate.opsForValue().set("LastModifyDateTime", strEndTime);
                } else {
                    // 更新最后同步时间
                    StringRedisTemplate.opsForValue().set("overseas:LastModifyDateTime", strEndTime);
                }


            }

            dtLastModifyDateTime = dtEndTime;
        }
        logger.info("OrderStatusSyncTask:订单状态同步结束");
    }

    // 同步订单状态国内外账号

    /**
     * @param strBeginTime 开始时间
     * @param strEndTime   结束时间
     * @param moduleId     账号模块
     * @author 胡庆康
     * @date 2019/9/23 11:46
     * @returns
     */
    public boolean SyncOrderState(String strBeginTime, String strEndTime, String moduleId) {
        BaseApiReq baseApiReq = Enviroment.getBaseApiReq(moduleId);
        MonitorOrderStateRequest requestEntity = new MonitorOrderStateRequest();
        MonitorOrderStateRequest.HotelReservation hotelReservation = new MonitorOrderStateRequest.HotelReservation();
        hotelReservation.setUniqueID(new ArrayList<>(16));
        // 构建 UniqueID
        MonitorOrderStateRequest.UniqueID uniqueID = new MonitorOrderStateRequest.UniqueID();
        uniqueID.setType("28");
        uniqueID.setID(baseApiReq.getAID());
        hotelReservation.getUniqueID().add(uniqueID);
        uniqueID = new MonitorOrderStateRequest.UniqueID();
        uniqueID.setType("503");
        uniqueID.setID(baseApiReq.getSID());
        hotelReservation.getUniqueID().add(uniqueID);
        hotelReservation.setLastModifyDateTime(strBeginTime);
        hotelReservation.setEndModifyDateTime(strEndTime);
        requestEntity.setHotelReservations(new ArrayList<>(16));
        requestEntity.getHotelReservations().add(hotelReservation);
        // 公共参数
        requestEntity.setTimeStamp(new Timestamp(System.currentTimeMillis()).toString());
        requestEntity.setVersion("1");
        requestEntity.setPrimaryLangID("zh-cn");

        JSONObject param = JSONObject.parseObject(JSON.toJSONString(requestEntity));
        JSONObject response = iOrderService.getResponse(param, Enviroment.HOTEL_ORDER_STATECHANGE.getValue(), Enviroment.TEST_HOTEL_ORDER_STATECHANGE.getValue());
        if (null == response) {
            String strMsg = "MonitorOrderState " + strBeginTime + " ~ " + strEndTime + " 失败：携程接口调用失败";
            logger.error("OrderStatusSyncTask:" + strMsg);
            return false;
        }
        // 解析应答json
        MonitorOrderStateResponse responseEntity = JSONObject.toJavaObject(response, MonitorOrderStateResponse.class);
        if (responseEntity != null
                && responseEntity.getResponseStatus() != null
                && !"Success".equals(responseEntity.getResponseStatus().getAck())) {
            String strMsg = responseEntity.getResponseStatus().getErrors().toString();
            logger.error(strMsg);
            return false;
        }

        if (responseEntity != null
                && null != responseEntity.getResponseStatus()
                && "Success".equals(responseEntity.getResponseStatus().getAck())) {
            if (null == responseEntity.getErrors() || responseEntity.getErrors().size() == 0) {
                if ((responseEntity.getHotelReservations() == null || responseEntity.getHotelReservations().size() == 0)) {
                    // 没有订单状态发生变化
                    logger.info("OrderStatusSyncTask:没有订单状态发生变化");
                    return true;
                }
            } else {
                String strMsg = responseEntity.getErrors().toString();
                logger.error("OrderStatusSyncTask:" + strMsg);
                return false;
            }
        }
        if (responseEntity != null
                && null != responseEntity.getResponseStatus()
                && "Success".equals(responseEntity.getResponseStatus().getAck())
                && responseEntity.getHotelReservations().size() > 0) {
            for (MonitorOrderStateResponse.HotelReservation orderItem : responseEntity.getHotelReservations()) {
                String str501Id = "";
                String str502Id = "";
                String str507Id = "";
                String str509Id = "";
                for (MonitorOrderStateResponse.HotelReservationID idItem : orderItem.getResGlobalInfo().getHotelReservationIDs()) {
                    //ResID_Type为501，则该节点的值表示“新订单号”；
                    //ResID_Type为502，则该节点的值表示“酒店确认号”；
                    //ResID_Type为507，则该节点的值表示被修改的“原始订单号”，对应修改后订单号为“新订单号”。
                    //ResID_Type为509，则该节点的值表示被补单的“原始订单号”，与补单后生成的“新订单号”相对应。
                    //备注：存在多个订单号的情况下，建议分销商将关联的订单号也落到本地，每个订单号有独立的订单处理流程。
                    switch (idItem.getResIDType()) {
                        case "501": {
                            str501Id = idItem.getResIDValue();
                            break;
                        }
                        case "502": {
                            str502Id = idItem.getResIDValue();
                            break;
                        }
                        case "507": {
                            str507Id = idItem.getResIDValue();
                            break;
                        }
                        case "509": {
                            str509Id = idItem.getResIDValue();
                            break;
                        }
                        default: {
                            break;
                        }
                    }
                }
                String strOrderId = str501Id;
                // 更新订单关联ID
                QueryWrapper<HtOrder> orderQueryWrapper = new QueryWrapper<>();
                HtOrder orderTemp = new HtOrder();
                orderQueryWrapper.eq("ORDER_ID", strOrderId);
                orderTemp.setOrderId501(str501Id);
                orderTemp.setOrderId502(str502Id);
                orderTemp.setOrderId507(str507Id);
                orderTemp.setOrderId509(str509Id);
                htOrderMapper.update(orderTemp, orderQueryWrapper);
                // 订单状态变更时间
                Date dtOrderStateChangeTime = null;
                try {
                    dtOrderStateChangeTime = format.parse(orderItem.getLastModifyDateTime().replace("T", " "));
                } catch (ParseException e) {
                    //日期转换失败
                    e.printStackTrace();
                }
//                String strOrderStateChangeTime = transform.format(dtOrderStateChangeTime);
                // 找回订单
                HtOrder order = htOrderMapper.selectById(strOrderId);
                if (order == null) {
                    // 写日志
                    logger.error("OrderStatusSyncTask:获取订单失败,未找到订单号为" + strOrderId + "的订单");
                    return false;
                }
                // 获取订单详情，同步订单状态
                GetOrderDetailResponse detailResponse = null;
                detailResponse = iOrderService.getOrderDetailResponse(strOrderId, moduleId);
                if (null != detailResponse
                        && "Success".equals(detailResponse.getResponseStatus().getAck())
                        && null != detailResponse.getReservationsList()
                        && detailResponse.getReservationsList().size() > 0) {
                    // 解析出订单状态
                    int nOrderSate = -1;
                    String strOrderSate = detailResponse.getReservationsList().get(0).getOrderStatus();
                    nOrderSate = HotelOrderState.getByCode(strOrderSate).getCode();
                    // 更新订单状态
                    if (nOrderSate != -1) {
                        orderQueryWrapper = new QueryWrapper<>();
                        orderTemp = new HtOrder();
                        orderQueryWrapper.eq("ORDER_ID", strOrderId);
                        orderTemp.setOrderState(nOrderSate);
                        orderTemp.setLastUpdateStateTime(new Date());
                        int result = htOrderMapper.update(orderTemp, orderQueryWrapper);
                        if (result == 0) {
                            String strMsg = "更新订单(" + strOrderId + ":" + strOrderSate + ")状态失败：" + "数据库更新错误";
                            logger.error("OrderStatusSyncTask:" + strMsg);
                        }
                        // 记录日志
                        switch (HotelOrderState.getByCode(strOrderSate)) {
                            case Process: {
                                HtOrderOplog orderOplog = new HtOrderOplog();
                                orderOplog.setOpType(OplogOperationType.Confirm.getCode());
                                orderOplog.setOrderId(strOrderId);
                                orderOplog.setOpCode("SyncOrderState");
                                orderOplog.setOpContent("订单处理中");
                                orderOplog.setOpTime(dtOrderStateChangeTime);
                                orderOplog.setCreater("API");
                                orderOplog.setMemo("");
                                htOrderOplogMapper.insert(orderOplog);
                                break;
                            }
                            case Confirm: {
                                HtOrderOplog orderOplog = new HtOrderOplog();
                                orderOplog.setOpType(OplogOperationType.Confirm.getCode());
                                orderOplog.setOrderId(strOrderId);
                                orderOplog.setOpCode("SyncOrderState");
                                orderOplog.setOpContent("订单已确认");
                                orderOplog.setOpTime(dtOrderStateChangeTime);
                                orderOplog.setCreater("API");
                                orderOplog.setMemo("");
                                htOrderOplogMapper.insert(orderOplog);
                                break;
                            }
                            case Success: {
                                HtOrderOplog orderOplog = new HtOrderOplog();
                                orderOplog.setOpType(OplogOperationType.Success.getCode());
                                orderOplog.setOrderId(strOrderId);
                                orderOplog.setOpCode("SyncOrderState");
                                orderOplog.setOpContent("订单已完成");
                                orderOplog.setOpTime(dtOrderStateChangeTime);
                                orderOplog.setCreater("API");
                                orderOplog.setMemo("");
                                htOrderOplogMapper.insert(orderOplog);
                                break;
                            }
                            case Cancel: {
                                // 更新订单取消理由 OrderCancelReason
                                String strCancelReason = "";
                                try {
                                    strCancelReason = detailResponse.getReservationsList().get(0).getResGlobalInfo().getHotelReservationIDs().get(0).getCancelReason();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                orderQueryWrapper = new QueryWrapper<>();
                                orderTemp = new HtOrder();
                                orderTemp.setCancelReason(strCancelReason);
                                result = htOrderMapper.update(orderTemp, orderQueryWrapper);
                                if (result == 0) {
                                    logger.error("OrderStatusSyncTask:订单取消理由更新失败");
                                }
                                // 写日志
                                {
                                    HtOrderOplog orderOplog = new HtOrderOplog();
                                    orderOplog.setOpType(OplogOperationType.Cancel.getCode());
                                    orderOplog.setOrderId(strOrderId);
                                    orderOplog.setOpCode("SyncOrderState");
                                    orderOplog.setOpContent("订单已取消");
                                    orderOplog.setOpTime(dtOrderStateChangeTime);
                                    orderOplog.setCreater("API");
                                    orderOplog.setMemo("");
                                    htOrderOplogMapper.insert(orderOplog);
                                }
                                //TODO: 对于由于客房满等原因，OTA取消订单的问题，要及时通知客服，电话联系客人
                                if (!order.getOrderState().equals(HotelOrderState.Cancel.getName())) {
                                    // 如果数据库中的订单状态不是Cancel，说明不是用户主动取消订单，
                                    // 是OTA由于客房满等原因取消了订单，这是要电话通知客户，并人工退款
                                    orderQueryWrapper = new QueryWrapper<>();
                                    orderTemp = new HtOrder();
                                    orderTemp.setCancelReason("订单被平台取消");
                                    orderTemp.setManualFlag(1);
                                    orderTemp.setOrderId(strOrderId);
                                    result = htOrderMapper.update(orderTemp, orderQueryWrapper);
                                    if (result == 0) {
                                        logger.error("OrderStatusSyncTask:订单人工取消理由更新失败");
                                    }

                                }
                                break;
                            }
                            default: {
                                break;
                            }
                        }
                    }

                    // 更新酒店名称，房间名称
                    String strHotelName = "";
                    String strRoomName = "";
                    try {
                        strHotelName = detailResponse.getReservationsList().get(0).getRoomStays().get(0).getBasicPropertyInfo().getHotelName();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        strRoomName = detailResponse.getReservationsList().get(0).getRoomStays().get(0).getRoomTypes().get(0).getName();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    orderTemp = new HtOrder();
                    orderQueryWrapper = new QueryWrapper<>();
                    orderTemp.setHotelName(strHotelName);
                    orderTemp.setRoomName(strRoomName);
                    orderQueryWrapper.eq("ORDER_ID", strOrderId);
                    // 更新关联订单ID及酒店名称，房间名称
                    int result = htOrderMapper.update(orderTemp, orderQueryWrapper);
                    if (result == 0) {
//                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        logger.error("OrderStatusSyncTask:更新关联订单ID及酒店名称，房间名称失败");
                    }
                }
            }
            return true;
        }
        return false;
    }

}
