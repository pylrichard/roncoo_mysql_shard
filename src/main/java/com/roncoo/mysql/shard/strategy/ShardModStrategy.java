package com.roncoo.mysql.shard.strategy;

/**
 * 分片策略-按取模值
 */
public class ShardModStrategy extends ShardStrategy {
    public static final int SHARD_COUNT = 4;

    @Override
    public Object getShardValue(Object shardKey) {
        return Long.parseLong((String) shardKey) % SHARD_COUNT;
    }
}
