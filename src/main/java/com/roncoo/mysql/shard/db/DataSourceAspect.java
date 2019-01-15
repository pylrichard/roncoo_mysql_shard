package com.roncoo.mysql.shard.db;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

public class DataSourceAspect {
    public void before(JoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        DataSource source = method.getAnnotation(DataSource.class);
        if (source == null) {
            return;
        }
        String value = source.value();
        //设置要使用的数据源
        DbContextHolder.set(value);
    }
}
