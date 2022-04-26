package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.SysOperationLog;

import java.util.List;

/**
 * 操作日志 服务层
 *
 * @author Lion Li
 */
public interface ISysOperationLogService {

    TableDataInfo<SysOperationLog> selectPageOperationLogList(SysOperationLog entity, PageQuery pageQuery);

    /**
     * 新增操作日志
     *
     * @param entity 操作日志对象
     */
    void insertOperationLog(SysOperationLog entity);

    /**
     * 查询系统操作日志集合
     *
     * @param entity 操作日志对象
     * @return 操作日志集合
     */
    List<SysOperationLog> selectOperationLogList(SysOperationLog entity);

    /**
     * 批量删除系统操作日志
     *
     * @param ids 需要删除的操作日志ID
     * @return 结果
     */
    int deleteOperationLogByIds(Long[] ids);

    /**
     * 查询操作日志详细
     *
     * @param id 操作ID
     * @return 操作日志对象
     */
    SysOperationLog selectOperationLogById(Long id);

    /**
     * 清空操作日志
     */
    void cleanOperationLog();
}
