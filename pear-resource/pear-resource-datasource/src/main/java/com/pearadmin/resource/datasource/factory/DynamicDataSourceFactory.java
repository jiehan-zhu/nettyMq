package com.pearadmin.resource.datasource.factory;

import com.pearadmin.resource.datasource.entity.BaseDataSourceConfig;
import com.pearadmin.resource.datasource.entity.DruidDataSourceConfig;
import com.pearadmin.resource.datasource.entity.HikariDataSourceConfig;
import com.pearadmin.resource.datasource.support.BaseDataSourceSupport;
import com.pearadmin.resource.datasource.support.DruidDataSourceSupport;
import com.pearadmin.resource.datasource.support.HikariDataSourceSupport;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * DataSource Factory -- [就眠仪式]
 */
@Slf4j
public class DynamicDataSourceFactory {

    Map<String, BaseDataSourceConfig> configs;

    Map<String, BaseDataSourceSupport> dataSources = new HashMap<>();

    private String primaryPoolName;

    /**
     * Example chemical plant
     */
    public DynamicDataSourceFactory(Map<String, BaseDataSourceConfig> configs, String primaryPoolName) {
        this.configs = configs;
        this.primaryPoolName = primaryPoolName;
        configs.forEach((key, item) -> {
            log.info("加 载 数 据 源 : " + key );
            if (item instanceof DruidDataSourceConfig) {
                dataSources.put(key, new DruidDataSourceSupport((DruidDataSourceConfig) item).build());
            } else if (item instanceof HikariDataSourceConfig) {
                dataSources.put(key, new HikariDataSourceSupport((HikariDataSourceConfig) item).build());
            }
        });
        log.info("默 认 数 据 源 : " + primaryPoolName);
        log.info("Initialization datasource factory");
    }

    /**
     * Primary DataSource
     */
    public BaseDataSourceSupport primaryDataSource() {
        return dataSources.get(primaryPoolName);
    }

    /**
     * Other DataSource List
     */
    public Map<Object, Object> otherDataSource() {

        Map<Object, Object> map = new HashMap<>();

        dataSources.forEach((key, item) -> {

            if (key != primaryPoolName) map.put(key, item);

        });
        return map;
    }
}
