package com.roncoo.mysql.shard.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 见applicationContext.xml
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DbContextHolder.get();
    }
}
