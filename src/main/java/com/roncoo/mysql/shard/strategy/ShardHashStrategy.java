package com.roncoo.mysql.shard.strategy;

/**
 * 分片策略-按Hash值
 */
public class ShardHashStrategy extends ShardStrategy {
    private final static Integer SEGMENT_NUM = 1024;

    private static Long segmentArray[] = new Long[SEGMENT_NUM];

    static {
        /*
            按数据分布权重将数据路由到对应分片
            比如有4台A、B、C、D服务器(编号为0~3)，A和D的性能较好，各自有3/8的数据在上面，B和C各自有1/8的数据
            则SEGMENT_NUM中各自有3/8的元素对应到A和D，各自有1/8的数据对应到B和C
         */
        for (int i = 0; i < (SEGMENT_NUM / 8 * 3); i++) {
            segmentArray[i] = 0L;
        }
        for (int i = (SEGMENT_NUM / 8 * 3); i < (SEGMENT_NUM / 8 * 4); i++) {
            segmentArray[i] = 1L;
        }
        for (int i = (SEGMENT_NUM / 8 * 4); i < (SEGMENT_NUM / 8 * 5); i++) {
            segmentArray[i] = 2L;
        }
        for (int i = (SEGMENT_NUM / 8 * 5); i < SEGMENT_NUM; i++) {
            segmentArray[i] = 3L;
        }
    }

    @Override
    public Object getShardValue(Object shardKey) {
        Integer hashCode = new Integer(Integer.parseInt((String) shardKey)).hashCode();

        /*
            SEGMENT_NUM等于2的n次方，方便根据位与运算计算数据路由分片编号
         */
        return segmentArray[hashCode & SEGMENT_NUM];
    }
}
