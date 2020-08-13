package com.dk.common.components.easyexcel.entity;

import lombok.Data;
import java.lang.reflect.Field;

/**
 * 单元格信息
 * @author dongkai
 */
@Data
public class ExcelCellBo {
    private Field field;
    private String fieldName;
    private String headName;
    private Integer columnIndex;
    private Integer rowIndex;

}
