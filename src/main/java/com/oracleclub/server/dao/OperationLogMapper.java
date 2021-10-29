package com.oracleclub.server.dao;

import com.oracleclub.server.dao.base.BaseDao;
import com.oracleclub.server.entity.OperationLog;
import org.springframework.stereotype.Repository;

/**
 * (OperationLogs)表数据库访问层
 *
 * @author makejava
 * @since 2021-02-21 17:08:08
 */
@Repository
public interface OperationLogMapper extends BaseDao<OperationLog,Long> {

}

