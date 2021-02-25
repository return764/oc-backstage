package com.oracleclub.server.service;

import com.oracleclub.server.entity.OperationLog;

import java.util.List;

/**
 * (OperationLogs)表服务接口
 *
 * @author makejava
 * @since 2021-02-21 17:08:08
 */
public interface OperationLogService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OperationLog queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<OperationLog> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param operationLogs 实例对象
     * @return 实例对象
     */
    OperationLog insert(OperationLog operationLogs);

    /**
     * 修改数据
     *
     * @param operationLogs 实例对象
     * @return 实例对象
     */
    OperationLog update(OperationLog operationLogs);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
