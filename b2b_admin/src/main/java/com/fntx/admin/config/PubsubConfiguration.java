package com.fntx.admin.config;

import com.fntx.admin.AdminApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Description: redis过期触发器配置类
 * @Author: 胡庆康
 * @Date: 2019/7/8 14:51
 */
@Configuration
@Import(value = AdminApplication.class)
public class PubsubConfiguration {

}

