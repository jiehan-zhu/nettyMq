package com.pearadmin.common.configure;

import com.fasterxml.jackson.core.json.PackageVersion;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * Long 精度处理
 * */
public class LongModule extends SimpleModule {

    public LongModule() {
        super(PackageVersion.VERSION);
        this.addSerializer(Long.TYPE, ToStringSerializer.instance);
        this.addSerializer(Long.class, ToStringSerializer.instance);
    }
}
