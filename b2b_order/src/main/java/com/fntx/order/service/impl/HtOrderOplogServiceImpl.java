package com.fntx.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fntx.common.domain.BaseInfo;
import com.fntx.common.domain.BaseReq;
import com.fntx.order.domain.HtOrderOplog;
import com.fntx.order.dao.HtOrderOplogMapper;
import com.fntx.order.service.HtOrderOplogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 订单日志 服务实现类
 * </p>
 *
 * @author kang
 * @since 2019-07-17
 */
@Service
public class HtOrderOplogServiceImpl extends ServiceImpl<HtOrderOplogMapper, HtOrderOplog> implements HtOrderOplogService {

    @Autowired
    private HtOrderOplogMapper htOrderOplogMapper;

    @Override
    public void createHtLogger(BaseInfo baseReqVo, JSONObject req, String content, String orderId) {
        HtOrderOplog log = new HtOrderOplog();
        log.setOpType(1);
        log.setOrderId(orderId);
        log.setOpCode(baseReqVo.getICODE());
        log.setOpContent(content);
        log.setOpTime(new Date());
        log.setCreater(baseReqVo.getUUID());
        log.setMemo(req.toJSONString());
        htOrderOplogMapper.insert(log);
    }


    @Override
    public void insertOrderOpLog(BaseInfo baseInfo, int opType, String memo, String content, String orderId) {
        HtOrderOplog log = new HtOrderOplog();
        log.setOpType(opType);
        log.setOrderId(orderId);
        log.setOpCode(baseInfo.getICODE());
        log.setOpContent(content);
        log.setOpTime(new Date());
        log.setCreater(baseInfo.getUUID());
        log.setMemo(memo);
        htOrderOplogMapper.insert(log);
    }

}
