package com.oracleclub.server.service.impl;

import com.oracleclub.server.entity.OperationLog;
import com.oracleclub.server.service.OperationLogService;

import java.util.List;

/**
 * @author :RETURN
 * @date :2021/2/21 18:27
 */
public class OperationLogServiceImpl implements OperationLogService {
    @Override
    public OperationLog queryById(Long id) {
        return null;
    }

    @Override
    public List<OperationLog> queryAllByLimit(int offset, int limit) {
        return null;
    }

    @Override
    public OperationLog insert(OperationLog operationLogs) {
        return null;
    }

    @Override
    public OperationLog update(OperationLog operationLogs) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }
}
