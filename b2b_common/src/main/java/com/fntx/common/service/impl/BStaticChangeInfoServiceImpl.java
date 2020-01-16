package com.fntx.common.service.impl;

import com.fntx.common.domain.BStaticChangeInfo;
import com.fntx.common.dao.BStaticChangeInfoMapper;
import com.fntx.common.service.IBStaticChangeInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 静态信息增量更新表 服务实现类
 * </p>
 *
 * @author wang
 * @since 2019-08-06
 */
@Service
public class BStaticChangeInfoServiceImpl extends ServiceImpl<BStaticChangeInfoMapper, BStaticChangeInfo> implements IBStaticChangeInfoService {

}
