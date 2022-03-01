package com.oracleclub.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oracleclub.server.dao.OperationLogMapper;
import com.oracleclub.server.dao.UserMapper;
import com.oracleclub.server.entity.OperationLog;
import com.oracleclub.server.entity.vo.OperationLogVO;
import com.oracleclub.server.entity.vo.SimpleUserVO;
import com.oracleclub.server.service.OperationLogService;
import com.oracleclub.server.service.base.AbstractCrudService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author :RETURN
 * @date :2021/2/21 18:27
 */
@Service
public class OperationLogServiceImpl extends AbstractCrudService<OperationLog,Long> implements OperationLogService {

    private final OperationLogMapper logMapper;
    private final UserMapper userMapper;

    protected OperationLogServiceImpl(OperationLogMapper logMapper, UserMapper userMapper) {
        super(logMapper);
        this.logMapper = logMapper;
        this.userMapper = userMapper;
    }

    @Override
    public List<OperationLog> listLatest(int top) {
        Assert.isTrue(top > 0,"Top number must not be less than 0");

        Page<OperationLog> pageRequest = new Page<>(0,top);
        return logMapper.selectPage(pageRequest,getLogDESCWrapper()).getRecords();
    }

    @Override
    public IPage<OperationLog> pageBy(IPage<OperationLog> pageRequest) {
        Assert.notNull(pageRequest, "分页参数不能为空");

        return logMapper.selectPage(pageRequest,getLogDESCWrapper());
    }

    private QueryWrapper<OperationLog> getLogDESCWrapper() {
        QueryWrapper<OperationLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("created_at");
        return queryWrapper;
    }

    @Override
    public OperationLogVO convertToVO(OperationLog operationLog) {
        Assert.notNull(operationLog, "日志不存在");

        OperationLogVO operationLogVO = new OperationLogVO().convertFrom(operationLog);
        operationLogVO.setUser(new SimpleUserVO().convertFrom(userMapper.findUserById(operationLog.getUserId())));
        return operationLogVO;
    }

    @Override
    public List<OperationLogVO> convertToListVO(List<OperationLog> operationLogs) {
        return operationLogs.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public IPage<OperationLogVO> convertToPageVO(IPage<OperationLog> domains) {
        return domains.convert(this::convertToVO);
    }
}
