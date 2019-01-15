package com.roncoo.mysql.shard.strategy;

import org.junit.Test;

public class ShardStrategyTest {
    private static Class<?>[] strategies = new Class<?>[]{
            ShardHashStrategy.class,
            ShardDateStrategy.class,
            ShardEnumStrategy.class,
            ShardRangeStrategy.class,
            ShardModStrategy.class
    };

    @Test
    public void testShardStrategy() {
        for (Class clazz : strategies) {
            ShardStrategy strategy = null;
            try {
                strategy = (ShardStrategy) clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (strategy != null) {
                String shardKey = ShardStrategy.getShardKey();
                String tableName = ShardStrategy.getTableName(strategy.getShardValue(shardKey));
                ShardStrategy.createTableIfNotExist(tableName);
                ShardStrategy.executeSQL(tableName, "SELECT * FROM " + tableName);
            }
        }
    }
}