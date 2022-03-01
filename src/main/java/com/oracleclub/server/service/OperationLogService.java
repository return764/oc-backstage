package com.oracleclub.server.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oracleclub.server.entity.OperationLog;
import com.oracleclub.server.entity.vo.OperationLogVO;
import com.oracleclub.server.service.base.ConverterService;
import com.oracleclub.server.service.base.CrudService;

import java.util.List;

/**
 * (OperationLogs)表服务接口
 *
 * @author makejava
 * @since 2021-02-21 17:08:08
 */
public interface OperationLogService extends CrudService<OperationLog,Long>, ConverterService<OperationLogVO, OperationLog> {


    List<OperationLog> listLatest(int top);

    IPage<OperationLog> pageBy(IPage<OperationLog> convertTo);
}
