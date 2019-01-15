package com.roncoo.mysql.shard.strategy;

import com.google.common.collect.Lists;
import com.roncoo.mysql.shard.mapper.DbMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * 分片策略基类
 */
public abstract class ShardStrategy {
    private static DbMapper dbMapper;

    private static List<String> tableNameList = Lists.newArrayList();

    static {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        dbMapper = context.getBean(DbMapper.class);
        String sql = "show tables";
        List<Map<String, Object>> result = dbMapper.executeSQLAtSlave(sql);
        if (!CollectionUtils.isEmpty(result)) {
            for (Map<String, Object> element : result) {
                String tableName = (String) element.values().iterator().next();
                tableNameList.add(tableName);
            }
        }
    }

    public abstract Object getShardValue(Object shardKey);

    public static String getShardKey() {
        Scanner sc = new Scanner(System.in);
        System.out.println("分片字段:");
        String shardKey = sc.next();
        sc.close();

        return shardKey;
    }

    public static String getTableName(Object shardValue) {
        return "table_" + shardValue;
    }

    public static void createTableIfNotExist(String tableName) {
        System.out.println("路由到的表名为:" + tableName);
        if (!tableNameList.contains(tableName)) {
            String sql = "CREATE TABLE " + tableName + "(id BIGINT PRIMARY KEY, name VARCHAR(10))";
            dbMapper.executeSQLAtMaster(sql);
            tableNameList.add(tableName);
        }
    }

    public static List<Map<String, Object>> executeSQL(String tableName, String sql) {
        if (StringUtils.isNotEmpty(tableName) && StringUtils.isEmpty(sql)) {
            sql = "SELECT * FROM " + tableName;
        }
        List<Map<String, Object>> result = null;
        String[] sqlElements = sql.split(" ");
        String type = sqlElements[0];
        if (type.equals("SELECT")) {
            result = dbMapper.executeSQLAtSlave(sql);
        } else {
            result = dbMapper.executeSQLAtMaster(sql);
        }

        return result;
    }
}
