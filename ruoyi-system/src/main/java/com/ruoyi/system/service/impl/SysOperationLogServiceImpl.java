package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.dto.OperationLogDTO;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.service.OperationLogService;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.ip.AddressUtils;
import com.ruoyi.system.domain.SysOperationLog;
import com.ruoyi.system.mapper.SysOperationLogMapper;
import com.ruoyi.system.service.ISysOperationLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 操作日志 服务层处理
 *
 * @author Lion Li
 */
@RequiredArgsConstructor
@Service
public class SysOperationLogServiceImpl implements ISysOperationLogService, OperationLogService {

    private final SysOperationLogMapper baseMapper;

    /**
     * 操作日志记录
     *
     * @param operationLogDTO 操作日志信息
     */
    @Async
    @Override
    public void recordOperationLog(final OperationLogDTO operationLogDTO) {
        SysOperationLog entity = BeanUtil.toBean(operationLogDTO, SysOperationLog.class);
        // 远程查询操作地点
        entity.setOperationLocation(AddressUtils.getRealAddressByIP(entity.getOperationIp()));
        insertOperationLog(entity);
    }

    @Override
    public TableDataInfo<SysOperationLog> selectPageOperationLogList(SysOperationLog entity, PageQuery pageQuery) {
        Map<String, Object> params = entity.getParams();
        LambdaQueryWrapper<SysOperationLog> lqw = new LambdaQueryWrapper<SysOperationLog>()
            .like(StringUtils.isNotBlank(entity.getTitle()), SysOperationLog::getTitle, entity.getTitle())
            .eq(entity.getBusinessType() != null && entity.getBusinessType() > 0,
                SysOperationLog::getBusinessType, entity.getBusinessType())
            .func(f -> {
                if (ArrayUtil.isNotEmpty(entity.getBusinessTypes())) {
                    f.in(SysOperationLog::getBusinessType, Arrays.asList(entity.getBusinessTypes()));
                }
            })
            .eq(entity.getStatus() != null,
                SysOperationLog::getStatus, entity.getStatus())
            .like(StringUtils.isNotBlank(entity.getOperationName()), SysOperationLog::getOperationName, entity.getOperationName())
            .between(params.get("beginTime") != null && params.get("endTime") != null,
                SysOperationLog::getOperationTime, params.get("beginTime"), params.get("endTime"));
        if (StringUtils.isBlank(pageQuery.getOrderByColumn())) {
            pageQuery.setOrderByColumn("id");
            pageQuery.setIsAsc("desc");
        }
        Page<SysOperationLog> page = baseMapper.selectPage(pageQuery.build(), lqw);
        return TableDataInfo.build(page);
    }

    /**
     * 新增操作日志
     *
     * @param entity 操作日志对象
     */
    @Override
    public void insertOperationLog(SysOperationLog entity) {
        entity.setOperationTime(new Date());
        baseMapper.insert(entity);
    }

    /**
     * 查询系统操作日志集合
     *
     * @param entity 操作日志对象
     * @return 操作日志集合
     */
    @Override
    public List<SysOperationLog> selectOperationLogList(SysOperationLog entity) {
        Map<String, Object> params = entity.getParams();
        return baseMapper.selectList(new LambdaQueryWrapper<SysOperationLog>()
            .like(StringUtils.isNotBlank(entity.getTitle()), SysOperationLog::getTitle, entity.getTitle())
            .eq(entity.getBusinessType() != null && entity.getBusinessType() > 0,
                SysOperationLog::getBusinessType, entity.getBusinessType())
            .func(f -> {
                if (ArrayUtil.isNotEmpty(entity.getBusinessTypes())) {
                    f.in(SysOperationLog::getBusinessType, Arrays.asList(entity.getBusinessTypes()));
                }
            })
            .eq(entity.getStatus() != null && entity.getStatus() > 0,
                SysOperationLog::getStatus, entity.getStatus())
            .like(StringUtils.isNotBlank(entity.getOperationName()), SysOperationLog::getOperationName, entity.getOperationName())
            .between(params.get("beginTime") != null && params.get("endTime") != null,
                SysOperationLog::getOperationTime, params.get("beginTime"), params.get("endTime"))
            .orderByDesc(SysOperationLog::getId));
    }

    @Override
    public int deleteOperationLogByIds(Long[] ids) {
        return baseMapper.deleteBatchIds(Arrays.asList(ids));
    }

    @Override
    public SysOperationLog selectOperationLogById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public void cleanOperationLog() {
        baseMapper.delete(new LambdaQueryWrapper<>());
    }
}
