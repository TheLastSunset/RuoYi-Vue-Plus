package com.ruoyi.framework.config;

import com.mzt.logapi.starter.annotation.EnableLogRecord;
import org.springframework.context.annotation.Configuration;

/**
 * @author MaGuangZu
 * @since 2022-04-30
 */
@EnableLogRecord(tenant = "com.ruoyi")
@Configuration
public class BizLogConfig {
}
