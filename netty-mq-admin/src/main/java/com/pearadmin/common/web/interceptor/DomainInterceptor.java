package com.pearadmin.common.web.interceptor;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.pearadmin.common.context.UserContext;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * mybatis-plus 字段填充
 * <p>
 * @serial 2.0.0
 * @author 就眠儀式
 */
@Component
public class DomainInterceptor implements MetaObjectHandler {

    @Resource
    private UserContext userContext;

    @Override
    public void insertFill(MetaObject metaObject) {
        createField(metaObject);
        updateField(metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        updateField(metaObject);
    }

    /**
     * @Field 创建人
     * @Field 创建时间
     * */
    public void createField(MetaObject metaObject){
        this.strictInsertFill(metaObject,"createBy", String.class, userContext.getUserId());
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
    }

    /**
     * @Field 修改人
     * @Field 修改时间
     * */
    public void updateField(MetaObject metaObject){
        this.strictInsertFill(metaObject,"updateBy", String.class, userContext.getUserId());
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }
}