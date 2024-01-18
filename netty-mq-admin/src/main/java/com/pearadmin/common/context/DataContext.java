package com.pearadmin.common.context;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.pearadmin.modules.sys.domain.SysDataSource;
import com.pearadmin.modules.sys.service.SysDataSourceService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * Datasource 多数据源
 * <p>
 * @serial 2.0.0
 * @author 就眠儀式
 */
@Component
public class DataContext {

    @Resource
    protected DataSource dataSource;

    @Resource
    protected DefaultDataSourceCreator dataSourceCreator;

    @Resource
    private SysDataSourceService sysDataSourceService;

    /**
     * 新增数据源
     *
     * @param name 名称
     * @param username 账户
     * @param password 密码
     * @param url 连接
     * @param driver 驱动
     * */
    public void createDataSource(String name, String username, String password, String url, String driver){
        DynamicRoutingDataSource dynamicRoutingDataSource = (DynamicRoutingDataSource) dataSource;

        DataSourceProperty dsp = new DataSourceProperty();
        dsp.setPoolName(name);
        dsp.setUrl(url);
        dsp.setUsername(username);
        dsp.setPassword(password);
        dsp.setDriverClassName(driver);

        DataSource dataSource = dataSourceCreator.createDataSource(dsp);
        dynamicRoutingDataSource.addDataSource(name, dataSource);
    }

    /**
     * 切换数据源
     *
     * @param name 名称
     * */
    public void changeDataSource(String name){
        DynamicDataSourceContextHolder.push(name);
    }

    /**
     * 清空数据源
     *
     * @param name 名称
     * */
    public void cleanDataSource(String name) {
        DynamicDataSourceContextHolder.poll();
    }

    /**
     * 修改数据源
     *
     * @param name 名称
     * @param username 账户
     * @param password 密码
     * @param url 连接
     * @param driver 驱动
     * */
    public void updateDataSource(String name, String username, String password, String url, String driver) {
        removeDataSource(name);
        createDataSource(name, username, password, url, driver);
    }

    /**
     * 删除数据源
     *
     * @param name 名称
     * */
    public void removeDataSource(String name) {
        DynamicRoutingDataSource dynamicRoutingDataSource = (DynamicRoutingDataSource) dataSource;
        dynamicRoutingDataSource.removeDataSource(name);
    }

    /**
     * 数据源列表
     * */
    public Map<String, DataSource> getDataSources() {
        DynamicRoutingDataSource dynamicRoutingDataSource = (DynamicRoutingDataSource) dataSource;
        return dynamicRoutingDataSource.getDataSources();
    }

    /**
     * 获取数据源
     *
     * @param name 名称
     * */
    public DataSource getDataSource(String name) {
        DynamicRoutingDataSource dynamicRoutingDataSource = (DynamicRoutingDataSource) dataSource;
        return dynamicRoutingDataSource.getDataSource(name);
    }

    // init dataSource
    @PostConstruct
    public void loadDataSource() {
        List<SysDataSource> sysDataSources = sysDataSourceService.list();
        sysDataSources.forEach(sysDataSource -> {
            createDataSource(
                    sysDataSource.getName(),
                    sysDataSource.getUsername(),
                    sysDataSource.getPassword(),
                    sysDataSource.getUrl(),
                    sysDataSource.getDriver());
        });
    }
}
