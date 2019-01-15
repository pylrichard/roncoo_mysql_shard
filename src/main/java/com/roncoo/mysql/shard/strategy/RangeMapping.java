package com.roncoo.mysql.shard.strategy;

import lombok.Data;

@Data
public class RangeMapping {
    private Integer min;

    private Integer max;

    private Integer shardValue;
}
