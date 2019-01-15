package com.roncoo.mysql.shard.strategy;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 分片策略-按日期
 */
public class ShardDateStrategy extends ShardStrategy {
    private static final int SHARD_DAY = 3;

    private static final Integer ONE_DAY_MS = 24 * 60 * 60 * 1000;

    private static final String beginDate = "2018-01-01";

    private static final String format = "yyyy-MM-dd";

    private static SimpleDateFormat dateFormat;

    static {
        dateFormat = new SimpleDateFormat(format);
    }

    @Override
    public Object getShardValue(Object shardKey) {
        Long beginTime = null;
        Long endTime = null;
        try {
            beginTime = dateFormat.parse(beginDate).getTime();
            endTime = dateFormat.parse((String) shardKey).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long shardValue = (endTime - beginTime) / ONE_DAY_MS / SHARD_DAY;

        return shardValue;
    }
}
