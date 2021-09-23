package com.oracleclub.server.dao.inject;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * @author :RETURN
 * @date :2021/9/22 0:12
 */
public class SelectAllExist extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String sql = "SELECT * FROM " + tableInfo.getTableName() + " WHERE deleted_at IS NULL";

        String sqlMethod = "selectAllExist";
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return this.addSelectMappedStatementForTable(mapperClass, sqlMethod, sqlSource, tableInfo);
    }
}
