package com.dk.common.components.easyexcel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 错误信息
 * @author dongkai
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelErrorDTO extends ExcelCellBo{
    private String value;
    private String errMsg;
}
