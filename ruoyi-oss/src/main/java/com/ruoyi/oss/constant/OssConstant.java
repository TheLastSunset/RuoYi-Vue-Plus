package com.ruoyi.oss.constant;

import java.util.Arrays;
import java.util.List;

/**
 * 对象存储常量
 *
 * @author Lion Li
 */
public interface OssConstant {

    /**
     * OSS模块KEY
     */
    String SYS_OSS_KEY = "sys_oss:";

    /**
     * 对象存储配置KEY
     */
    String OSS_CONFIG_KEY = "OssConfig";

    /**
     * 缓存配置KEY
     */
    String CACHE_CONFIG_KEY = SYS_OSS_KEY + OSS_CONFIG_KEY;

    /**
     * 预览列表资源开关Key
     */
    String PREVIEW_LIST_RESOURCE_KEY = "sys.oss.previewListResource";

    /**
     * 系统数据ids
     */
    List<Integer> SYSTEM_DATA_IDS = Arrays.asList(1, 2, 3, 4);

    /**
     * https 状态
     */
    String IS_HTTPS = "Y";

}
