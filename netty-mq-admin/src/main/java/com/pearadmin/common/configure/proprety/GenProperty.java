package com.pearadmin.common.configure.proprety;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Describe: 默 认 配 置
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
@Component
@ConfigurationProperties(prefix = "gen")
@PropertySource(value = {"classpath:generate.yml"}, encoding = "UTF-8")
public class GenProperty {
    /**
     * 作者
     **/
    public static String author;

    /**
     * 生成包路径
     **/
    public static String packageName;

    /**
     * 自动去除表前缀，默认是false
     **/
    public static boolean autoRemovePre;

    /**
     * 表前缀(类名不会包含表前缀)
     **/
    public static String tablePrefix;

    public static String getAuthor() {
        return author;
    }

    @Value("${author}")
    public void setAuthor(String author) {
        GenProperty.author = author;
    }

    public static String getPackageName() {
        return packageName;
    }

    @Value("${packageName}")
    public void setPackageName(String packageName) {
        GenProperty.packageName = packageName;
    }

    public static boolean getAutoRemovePre() {
        return autoRemovePre;
    }

    @Value("${autoRemovePre}")
    public void setAutoRemovePre(boolean autoRemovePre) {
        GenProperty.autoRemovePre = autoRemovePre;
    }

    public static String getTablePrefix() {
        return tablePrefix;
    }

    @Value("${tablePrefix}")
    public void setTablePrefix(String tablePrefix) {
        GenProperty.tablePrefix = tablePrefix;
    }
}
