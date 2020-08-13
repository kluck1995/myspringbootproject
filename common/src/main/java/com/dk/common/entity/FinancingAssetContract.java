package com.dk.common.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.dk.common.components.easyexcel.constant.ExcelPatternMsg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.dk.common.components.easyexcel.annotation.SensitiveCharactersValidate;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 融资资产-合同信息
 *
 * @since 2020-07-01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ExcelIgnoreUnannotated // 不加ExcelProperty的注解的，不会参与读写
@ContentRowHeight(20)   // 设置行高，不包含表头 （标记在类上）
@HeadRowHeight(30)      // 设置表头高度 （标记在类上）
public class FinancingAssetContract {
    @ExcelIgnore
    private Long id;

    /**
     * 创建人ID
     */
    @ExcelIgnore   // 这个注解会忽略该字段，不解析到excel
    private String createUserId;

    /**
     * 创建时间
     *
     * excel 用年月日的格式
     */
    @DateTimeFormat("yyyy年MM月dd日 HH时mm分ss秒")
    private Date createTime;

    /**
     *  债务人（项目公司）企业全称【原 权人/债务人（对手企业全称）】
     *  这里不建议 index 和 name 同时使用，要么一个对象只用index，要么一个对象只用name去匹配
     *  用名字去匹配，这里需要注意，如果名字重复，会导致只有一个字段读取到数据
     */
    @ExcelProperty("债务人企业全称")
    @ColumnWidth(40)  // 单元格 宽度
    @NotNull(message = "债务人企业全称，不能为空" ) // 不能检查长度为0的字符串
    //@Length(min = 1, max = 3,message = "债务人企业全称，超长")
    private String companyName;

    /**
     * 债权人（供应商）企业全称
     */
    @ExcelProperty("债权人企业全称")
    @ColumnWidth(40)
    private String supplierCompanyName;

    /**
     * 基础交易合同编号与名称
     */
    @ExcelProperty("基础交易合同名称")
    @ColumnWidth(30)
    private String contractName;

    /**
     * 应收账款金额（元）
     */
    @ExcelProperty("应收账款转让金额（元）")
    @ColumnWidth(30)
    private BigDecimal contractReceivables;

    /**
     * 应收账款到期日
     */
    @ExcelProperty("应收账款到期日")
    @DateTimeFormat("yyyy-MM-dd") // 日期格式转换
    @Pattern(regexp = ExcelPatternMsg.DATE2,message = ExcelPatternMsg.DATE2_MSG)
    @ColumnWidth(20)
    private String contractDueDate;

    /**
     * 备注（发票或凭证号码）
     */
    @ExcelProperty("备注")
    @SensitiveCharactersValidate
    private String contractRemark;

    /**
     * 合同附件
     */
    private String contractFileId;

    /**
     * 审核状态0未通过1.通过
     */
    private String auditStatus;

    /**
     * 审核结论
     */
    private String auditRemark;

    /**
     * 合同是否审核过0未审核过1.审核过
     */
    private String commitStaus;

    /**
     * 更新人ID
     */
    private String updateUserId;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 逻辑删除 0未删除 1已删除
     */
    private Integer deleted;

    /**
     * 版本信息
     */
    private Integer version;

    /**
     * 资产CODE
     */
    private String assetCode;

    /**
     * 合同序号
     */
    private Integer contractOrder;

    /**
     * 合同企业属性CODE
     */
    private String companyAttributeCode;

    /**
     * 合同企业属性描述
     */
    private String companyAttributeName;

    /**
     * 项目名称
     */
    private String contractProjectName;
}
