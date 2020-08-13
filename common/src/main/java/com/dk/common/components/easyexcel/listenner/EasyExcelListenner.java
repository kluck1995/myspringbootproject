package com.dk.common.components.easyexcel.listenner;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.read.metadata.holder.ReadHolder;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.excel.read.metadata.property.ExcelReadHeadProperty;
import com.dk.common.components.easyexcel.entity.ExcelCellBo;
import com.dk.common.components.easyexcel.entity.ExcelErrorDTO;
import org.springframework.stereotype.Component;
import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validation;
import javax.validation.groups.Default;
import java.util.*;

/**
 * easyExcel 读取时的监听器
 * <p>
 * 需要继承 AnalysisEventListener类，并将要读取成的java对象传入泛行
 */
@Component
public abstract class EasyExcelListenner<T> extends AnalysisEventListener<T> {

    /**
     * 默认每5条存储到数据库中方，实际使用可以每3000条，然后清理list，方便内存回收
     */
    private static final int BATCH_COUNT = 2;
    // 校验失败的数据
    List<ExcelErrorDTO> errorList = new ArrayList();
    // 校验成功的数据
    List<T> dataList = new ArrayList<>();


    /**
     * 读取数据会执行该方法
     * @param data
     * @param analysisContext
     */
    @Override
    public void invoke(T data, AnalysisContext analysisContext) {
        Map<String, ExcelCellBo> propertyNameMap = getPropertyNameMap(true, analysisContext);
        if (validate(data, propertyNameMap)) {
            // 数据存储到list，供批量处理，或后续自己业务逻辑处理。
            dataList.add(data);
        }

        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (dataList.size() >= BATCH_COUNT ) {
            doService(dataList);
            dataList.clear();
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("=========== errorList ===============");
        System.out.println(errorList.toString());

        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        doService(dataList);
        System.out.println("finish");
    }


    /**
     * 校验数据
     * @param t
     * @param propertyNameMap
     * @return
     */
    boolean validate(T t, Map<String, ExcelCellBo> propertyNameMap) {
        boolean validateResult = true;

        //创建静态校验工厂 并 校验过程
        Set<ConstraintViolation<T>> validateSet = Validation.buildDefaultValidatorFactory().getValidator().validate(t, Default.class);
        if (validateSet != null && !validateSet.isEmpty()) {
            validateResult = false;
            ExcelErrorDTO errorDTO;
            for (ConstraintViolation<T> constraint : validateSet) {
                Path propertyPath = constraint.getPropertyPath();
                String propertyName = propertyPath.toString();
                ExcelCellBo bo = propertyNameMap.get(propertyName);
                errorDTO = new ExcelErrorDTO();
                errorDTO.setHeadName(bo.getHeadName());
                Object invalidValue = constraint.getInvalidValue();
                if (invalidValue != null) {
                    errorDTO.setValue(invalidValue.toString());
                } else {
                    errorDTO.setValue(null);
                }
                errorDTO.setColumnIndex(bo.getColumnIndex() + 1);
                errorDTO.setRowIndex(bo.getRowIndex() + 1);
                errorDTO.setErrMsg("第" + errorDTO.getRowIndex() + "第" + errorDTO.getColumnIndex() + "列," + constraint.getMessage());
                errorList.add(errorDTO);
            }
        }
        return validateResult;

    }

    /**
     * 获取当前行的信息
     * @param isSingleHeader
     * @param analysisContext
     * @return
     */
    Map<String, ExcelCellBo> getPropertyNameMap(boolean isSingleHeader, AnalysisContext analysisContext) {
        Map<String, ExcelCellBo> propertyNameMap = new HashMap<>(16);
        ReadRowHolder readRowHolder = analysisContext.readRowHolder();
        Integer rowIndex = readRowHolder.getRowIndex();
        ReadHolder readHolder = analysisContext.currentReadHolder();
        ExcelReadHeadProperty excelReadHeadProperty = readHolder.excelReadHeadProperty();
        Collection<ExcelContentProperty> values;
        if (isSingleHeader) {
            Map<Integer, ExcelContentProperty> contentPropertyMap = excelReadHeadProperty.getContentPropertyMap();
           // 获取到列的属性值
            values = contentPropertyMap.values();
        } else {
            //也适用于单行表头
            Map<String, ExcelContentProperty> fieldNameContentPropertyMap = excelReadHeadProperty.getFieldNameContentPropertyMap();
            values = fieldNameContentPropertyMap.values();
        }
        ExcelCellBo bo;
        for (ExcelContentProperty contentProperty : values) {
            bo = new ExcelCellBo();
            bo.setRowIndex(rowIndex);
            bo.setColumnIndex(contentProperty.getHead().getColumnIndex());
            bo.setFieldName(contentProperty.getHead().getFieldName());
            //多行表头
            bo.setHeadName(String.join(",", contentProperty.getHead().getHeadNameList()));
            bo.setField(contentProperty.getField());
            propertyNameMap.put(contentProperty.getHead().getFieldName(), bo);
        }
        return propertyNameMap;
    }

    /**
     * 对暂存数据的业务逻辑方法
     * 相关逻辑可以在该方法体内编写, 例如入库.
     */
    public abstract void doService(List<T> es);
    
}