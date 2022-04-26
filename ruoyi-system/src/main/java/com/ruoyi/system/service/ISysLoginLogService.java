package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.LoginLog;

import java.util.List;

/**
 * 系统访问日志情况信息 服务层
 *
 * @author Lion Li
 */
public interface ISysLoginLogService {

    TableDataInfo<LoginLog> selectPageLoginLogList(LoginLog entity, PageQuery pageQuery);

    /**
     * 新增系统登录日志
     *
     * @param entity 访问日志对象
     */
    void insertLoginLog(LoginLog entity);

    /**
     * 查询系统登录日志集合
     *
     * @param entity 访问日志对象
     * @return 登录记录集合
     */
    List<LoginLog> selectLoginLogList(LoginLog entity);

    /**
     * 批量删除系统登录日志
     *
     * @param ids 需要删除的登录日志ID
     * @return 结果
     */
    int deleteLoginLogByIds(Long[] ids);

    /**
     * 清空系统登录日志
     */
    void cleanLoginLog();
}
