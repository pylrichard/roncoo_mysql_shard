package com.roncoo.mysql.shard.strategy;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

/**
 * 分片策略-按枚举
 */
public class ShardEnumStrategy extends ShardStrategy {
    /**
     * Properties继承自HashTable，其中元素是无序的
     */
    private static Properties prop = new Properties();

    static {
        try {
            prop.load(ShardEnumStrategy.class.getResourceAsStream("enumRule.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Enumeration<Object> keys = prop.keys();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            String value = prop.getProperty(key);
            System.out.println("key: " + key
                    + " value: " + value
                    + " table: " + getTableName(Integer.parseInt(value)));
        }
    }

    @Override
    public Object getShardValue(Object shardKey) {
        String shardValue = prop.getProperty((String) shardKey);
        if (StringUtils.isEmpty(shardValue)) {
            throw new RuntimeException("未知的枚举规则");
        }

        return shardValue;
    }
}
