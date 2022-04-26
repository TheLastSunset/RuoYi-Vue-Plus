package com.ruoyi.system.domain;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 操作日志记录表 sys_operation_log
 *
 * @author Lion Li
 */
@Data
@TableName("sys_operation_log")
@ExcelIgnoreUnannotated
@ApiModel("操作日志记录业务对象")
public class SysOperationLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日志主键
     */
    @ApiModelProperty(value = "日志主键")
    @ExcelProperty(value = "日志主键")
    @TableId(value = "id")
    private Long id;

    /**
     * 操作模块
     */
    @ApiModelProperty(value = "操作模块")
    @ExcelProperty(value = "操作模块")
    private String title;

    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     */
    @ApiModelProperty(value = "业务类型（0其它 1新增 2修改 3删除）")
    @ExcelProperty(value = "业务类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_operation_type")
    private Integer businessType;

    /**
     * 业务类型数组
     */
    @ApiModelProperty(value = "业务类型数组")
    @TableField(exist = false)
    private Integer[] businessTypes;

    /**
     * 请求方法
     */
    @ApiModelProperty(value = "请求方法")
    @ExcelProperty(value = "请求方法")
    private String method;

    /**
     * 请求方式
     */
    @ApiModelProperty(value = "请求方式")
    @ExcelProperty(value = "请求方式")
    private String requestMethod;

    /**
     * 操作类别（0其它 1后台用户 2手机端用户）
     */
    @ApiModelProperty(value = "操作类别（0其它 1后台用户 2手机端用户）")
    @ExcelProperty(value = "操作类别", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=其它,1=后台用户,2=手机端用户")
    private Integer operationType;

    /**
     * 操作人员
     */
    @ApiModelProperty(value = "操作人员")
    @ExcelProperty(value = "操作人员")
    private String operationName;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称")
    @ExcelProperty(value = "部门名称")
    private String deptName;

    /**
     * 请求url
     */
    @ApiModelProperty(value = "请求url")
    @ExcelProperty(value = "请求地址")
    private String operationUrl;

    /**
     * 操作地址
     */
    @ApiModelProperty(value = "操作地址")
    @ExcelProperty(value = "操作地址")
    private String operationIp;

    /**
     * 操作地点
     */
    @ApiModelProperty(value = "操作地点")
    @ExcelProperty(value = "操作地点")
    private String operationLocation;

    /**
     * 请求参数
     */
    @ApiModelProperty(value = "请求参数")
    @ExcelProperty(value = "请求参数")
    private String operationParam;

    /**
     * 返回参数
     */
    @ApiModelProperty(value = "返回参数")
    @ExcelProperty(value = "返回参数")
    private String jsonResult;

    /**
     * 操作状态（0正常 1异常）
     */
    @ApiModelProperty(value = "操作状态（0正常 1异常）")
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_common_status")
    private Integer status;

    /**
     * 错误消息
     */
    @ApiModelProperty(value = "错误消息")
    @ExcelProperty(value = "错误消息")
    private String errorMsg;

    /**
     * 操作时间
     */
    @ApiModelProperty(value = "操作时间")
    @ExcelProperty(value = "操作时间")
    private Date operationTime;

    /**
     * 请求参数
     */
    @ApiModelProperty(value = "请求参数")
    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();

}
