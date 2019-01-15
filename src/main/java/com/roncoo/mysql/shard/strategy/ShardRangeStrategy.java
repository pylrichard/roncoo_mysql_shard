package com.roncoo.mysql.shard.strategy;

import com.google.common.collect.Maps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Map;

/**
 * 分片策略-按范围
 */
public class ShardRangeStrategy extends ShardStrategy {
    private static Map<Integer, RangeMapping> rangeMap = Maps.newHashMap();

    static {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                ShardRangeStrategy.class.getResourceAsStream("rangeRule.properties")));
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                String[] split = line.split("=");
                String[] range = split[0].split("-");
                RangeMapping rangeMapping = new RangeMapping();
                rangeMapping.setMin(Integer.parseInt(range[0]));
                rangeMapping.setMax(Integer.parseInt(range[1]));
                rangeMapping.setShardValue(Integer.parseInt(split[1]));
                rangeMap.put(rangeMapping.getShardValue(), rangeMapping);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getShardValue(Object shardKey) {
        Integer key = (Integer) shardKey;
        Integer shardValue = null;
        Collection<RangeMapping> values = rangeMap.values();
        for (RangeMapping rangeMapping : values) {
            if (rangeMapping.getMin() <= key && key < rangeMapping.getMax()) {
                shardValue = rangeMapping.getShardValue();
                break;
            }
        }

        return shardValue;
    }
}
