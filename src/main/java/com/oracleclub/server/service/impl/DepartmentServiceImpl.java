package com.oracleclub.server.service.impl;

import com.oracleclub.server.entity.Department;
import com.oracleclub.server.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author :RETURN
 * @date :2021/2/21 18:27
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Override
    public Department queryById(Long id) {
        return null;
    }

    @Override
    public List<Department> queryAllByLimit(int offset, int limit) {
        return null;
    }

    @Override
    public Department insert(Department departments) {
        return null;
    }

    @Override
    public Department update(Department departments) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }
}
