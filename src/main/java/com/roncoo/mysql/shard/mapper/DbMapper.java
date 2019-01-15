package com.roncoo.mysql.shard.mapper;

import com.roncoo.mysql.shard.db.DataSource;
import com.roncoo.mysql.shard.db.DbContextHolder;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DbMapper {
    @DataSource(value = DbContextHolder.MASTER)
    List<Map<String, Object>> executeSQLAtMaster(@Param("sql") String sql);

    @DataSource(value = DbContextHolder.SLAVE)
    List<Map<String, Object>> executeSQLAtSlave(@Param("sql") String sql);
}
