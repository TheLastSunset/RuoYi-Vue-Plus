package com.ruoyi.system.service.impl;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.service.LoginLogService;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.ip.AddressUtils;
import com.ruoyi.system.domain.LoginLog;
import com.ruoyi.system.mapper.SysLoginLogMapper;
import com.ruoyi.system.service.ISysLoginLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统访问日志情况信息 服务层处理
 *
 * @author Lion Li
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class SysLoginLogServiceImpl implements ISysLoginLogService, LoginLogService {

    private final SysLoginLogMapper baseMapper;

    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息
     * @param args     列表
     */
    @Async
    @Override
    public void recordLoginLog(final String username, final String status, final String message,
                               HttpServletRequest request, final Object... args) {
        final UserAgent userAgent = UserAgentUtil.parse(request.getHeader("User-Agent"));
        final String ip = ServletUtils.getClientIP(request);

        String address = AddressUtils.getRealAddressByIP(ip);
        String s = getBlock(ip) + address + getBlock(username) + getBlock(status) + getBlock(message);
        // 打印信息到日志
        log.info(s, args);
        // 获取客户端操作系统
        String os = userAgent.getOs().getName();
        // 获取客户端浏览器
        String browser = userAgent.getBrowser().getName();
        // 封装对象
        LoginLog loginLog = new LoginLog();
        loginLog.setUserName(username);
        loginLog.setIpaddr(ip);
        loginLog.setLoginLocation(address);
        loginLog.setBrowser(browser);
        loginLog.setOs(os);
        loginLog.setMsg(message);
        // 日志状态
        if (StringUtils.equalsAny(status, Constants.LOGIN_SUCCESS, Constants.LOGOUT, Constants.REGISTER)) {
            loginLog.setStatus(Constants.SUCCESS);
        } else if (Constants.LOGIN_FAIL.equals(status)) {
            loginLog.setStatus(Constants.FAIL);
        }
        // 插入数据
        insertLoginLog(loginLog);
    }

    private String getBlock(Object msg) {
        if (msg == null) {
            msg = "";
        }
        return "[" + msg + "]";
    }

    @Override
    public TableDataInfo<LoginLog> selectPageLoginLogList(LoginLog entity, PageQuery pageQuery) {
        Map<String, Object> params = entity.getParams();
        LambdaQueryWrapper<LoginLog> lqw = new LambdaQueryWrapper<LoginLog>()
            .like(StringUtils.isNotBlank(entity.getIpaddr()), LoginLog::getIpaddr, entity.getIpaddr())
            .eq(StringUtils.isNotBlank(entity.getStatus()), LoginLog::getStatus, entity.getStatus())
            .like(StringUtils.isNotBlank(entity.getUserName()), LoginLog::getUserName, entity.getUserName())
            .between(params.get("beginTime") != null && params.get("endTime") != null,
                LoginLog::getLoginTime, params.get("beginTime"), params.get("endTime"));
        if (StringUtils.isBlank(pageQuery.getOrderByColumn())) {
            pageQuery.setOrderByColumn("info_id");
            pageQuery.setIsAsc("desc");
        }
        Page<LoginLog> page = baseMapper.selectPage(pageQuery.build(), lqw);
        return TableDataInfo.build(page);
    }

    /**
     * 新增系统登录日志
     *
     * @param entity 访问日志对象
     */
    @Override
    public void insertLoginLog(LoginLog entity) {
        entity.setLoginTime(new Date());
        baseMapper.insert(entity);
    }

    /**
     * 查询系统登录日志集合
     *
     * @param entity 访问日志对象
     * @return 登录记录集合
     */
    @Override
    public List<LoginLog> selectLoginLogList(LoginLog entity) {
        Map<String, Object> params = entity.getParams();
        return baseMapper.selectList(new LambdaQueryWrapper<LoginLog>()
            .like(StringUtils.isNotBlank(entity.getIpaddr()), LoginLog::getIpaddr, entity.getIpaddr())
            .eq(StringUtils.isNotBlank(entity.getStatus()), LoginLog::getStatus, entity.getStatus())
            .like(StringUtils.isNotBlank(entity.getUserName()), LoginLog::getUserName, entity.getUserName())
            .between(params.get("beginTime") != null && params.get("endTime") != null,
                LoginLog::getLoginTime, params.get("beginTime"), params.get("endTime"))
            .orderByDesc(LoginLog::getId));
    }

    /**
     * 批量删除系统登录日志
     *
     * @param ids 需要删除的登录日志ID
     * @return 结果
     */
    @Override
    public int deleteLoginLogByIds(Long[] ids) {
        return baseMapper.deleteBatchIds(Arrays.asList(ids));
    }

    /**
     * 清空系统登录日志
     */
    @Override
    public void cleanLoginLog() {
        baseMapper.delete(new LambdaQueryWrapper<>());
    }
}
