package com.ruoyi.web.controller.monitor;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.SysOperationLog;
import com.ruoyi.system.service.ISysOperationLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 操作日志记录
 *
 * @author Lion Li
 */
@Validated
@Api(value = "操作日志记录", tags = {"操作日志记录管理"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/monitor/operationLog")
public class SysOperationLogController extends BaseController {

    private final ISysOperationLogService operationLogService;

    @ApiOperation("查询操作日志记录列表")
    @SaCheckPermission("monitor:operationLog:list")
    @GetMapping("/list")
    public TableDataInfo<SysOperationLog> list(SysOperationLog entity, PageQuery pageQuery) {
        return operationLogService.selectPageOperationLogList(entity, pageQuery);
    }

    @ApiOperation("导出操作日志记录列表")
    @Log(title = "操作日志", businessType = BusinessType.EXPORT)
    @SaCheckPermission("monitor:operationLog:export")
    @PostMapping("/export")
    public void export(SysOperationLog entity, HttpServletResponse response) {
        List<SysOperationLog> list = operationLogService.selectOperationLogList(entity);
        ExcelUtil.exportExcel(list, "操作日志", SysOperationLog.class, response);
    }

    @ApiOperation("删除操作日志记录")
    @Log(title = "操作日志", businessType = BusinessType.DELETE)
    @SaCheckPermission("monitor:operationLog:remove")
    @DeleteMapping("/{ids}")
    public R<Void> remove(@PathVariable Long[] ids) {
        return toAjax(operationLogService.deleteOperationLogByIds(ids));
    }

    @ApiOperation("清空操作日志记录")
    @Log(title = "操作日志", businessType = BusinessType.CLEAN)
    @SaCheckPermission("monitor:operationLog:remove")
    @DeleteMapping("/clean")
    public R<Void> clean() {
        operationLogService.cleanOperationLog();
        return R.ok();
    }
}
