package com.pearadmin.common.configure;

import com.fasterxml.jackson.core.json.PackageVersion;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;


public class JavaLongTypeModule extends SimpleModule {
    public JavaLongTypeModule() {
        super(PackageVersion.VERSION);
        // 将 Long 转 String
        this.addSerializer(Long.TYPE, ToStringSerializer.instance);
        this.addSerializer(Long.class, ToStringSerializer.instance);
    }
}
