package com.dk.common.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ExcelIgnoreUnannotated // 不加ExcelProperty的注解的，不会参与读写
@ContentRowHeight(20)   // 设置行高，不包含表头 （标记在类上）
@HeadRowHeight(30)      // 设置表头高度 （标记在类上）
public class User {
    // index 是从0开始的
    // 如果不加index默认是按照实体属性的顺序排序
    @ExcelProperty(value = "名字",index = 1)
    @ColumnWidth(20)  // 单元格 宽度
    private String name;

    @ExcelProperty(value ={ "联系方式","手机号"},index = 3)
    @ColumnWidth(30)  // 单元格 宽度
    private String phone;

    @ExcelProperty(value ={ "联系方式", "邮箱" },index = 2)
    @ColumnWidth(30)  // 单元格 宽度
    private String email;

    @ExcelProperty(value = "地址",index = 0)
    @ColumnWidth(40)  // 单元格 宽度
    private String address;

    @ExcelIgnore   // 这个注解会忽略该字段，不解析到excel
    private String gender;

    private Integer age;

}
