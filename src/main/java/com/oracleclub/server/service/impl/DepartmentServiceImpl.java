package com.oracleclub.server.service.impl;

import com.oracleclub.server.dao.DepartmentMapper;
import com.oracleclub.server.entity.Department;
import com.oracleclub.server.entity.vo.DepartmentVO;
import com.oracleclub.server.service.DepartmentService;
import com.oracleclub.server.service.base.AbstractCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author :RETURN
 * @date :2021/2/21 18:27
 */
@Service
@Slf4j
public class DepartmentServiceImpl extends AbstractCrudService<Department,Long> implements DepartmentService {

    private DepartmentMapper departmentMapper;

    protected DepartmentServiceImpl(DepartmentMapper departmentMapper) {
        super(departmentMapper);
        this.departmentMapper = departmentMapper;
    }

    @Override
    public DepartmentVO convertToVO(Department department) {
        return new DepartmentVO().convertFrom(department);
    }

    @Override
    public DepartmentVO createOrUpdateBy(Department department) {
        return convertToVO(createOrUpdate(department));
    }

    @Override
    public DepartmentVO createBy(Department department) {
        return createOrUpdateBy(department);
    }

    @Override
    public List<Department> listAllExist() {
        return departmentMapper.getAllExist();
    }
}
