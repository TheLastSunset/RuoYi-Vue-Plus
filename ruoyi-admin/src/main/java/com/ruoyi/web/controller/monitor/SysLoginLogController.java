package com.ruoyi.web.controller.monitor;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.LoginLog;
import com.ruoyi.system.service.ISysLoginLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 系统访问记录
 *
 * @author Lion Li
 */
@Validated
@Api(value = "系统访问记录", tags = {"系统访问记录管理"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/monitor/loginLog")
public class SysLoginLogController extends BaseController {

    private final ISysLoginLogService loginLogService;

    @ApiOperation("查询系统访问记录列表")
    @SaCheckPermission("monitor:loginLog:list")
    @GetMapping("/list")
    public TableDataInfo<LoginLog> list(LoginLog loginLog, PageQuery pageQuery) {
        return loginLogService.selectPageLoginLogList(loginLog, pageQuery);
    }

    @ApiOperation("导出系统访问记录列表")
    @Log(title = "登录日志", businessType = BusinessType.EXPORT)
    @SaCheckPermission("monitor:loginLog:export")
    @PostMapping("/export")
    public void export(LoginLog loginLog, HttpServletResponse response) {
        List<LoginLog> list = loginLogService.selectLoginLogList(loginLog);
        ExcelUtil.exportExcel(list, "登录日志", LoginLog.class, response);
    }

    @ApiOperation("删除系统访问记录")
    @SaCheckPermission("monitor:loginLog:remove")
    @Log(title = "登录日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{infoIds}")
    public R<Void> remove(@PathVariable Long[] infoIds) {
        return toAjax(loginLogService.deleteLoginLogByIds(infoIds));
    }

    @ApiOperation("清空系统访问记录")
    @SaCheckPermission("monitor:loginLog:remove")
    @Log(title = "登录日志", businessType = BusinessType.CLEAN)
    @DeleteMapping("/clean")
    public R<Void> clean() {
        loginLogService.cleanLoginLog();
        return R.ok();
    }
}
