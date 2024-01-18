package com.pearadmin.common.aop.annotation;

import com.pearadmin.common.aop.enums.ExcelModel;

import java.lang.annotation.*;

/**
 * Excel 导入导出
 * <p>
 * @serial 2.0.0
 * @author 就眠儀式
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Excel {

    Class clazz();

    ExcelModel model() default ExcelModel.WRITE;
}