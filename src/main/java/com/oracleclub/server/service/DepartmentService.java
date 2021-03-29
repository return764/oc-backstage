package com.oracleclub.server.service;

import com.oracleclub.server.entity.Department;
import com.oracleclub.server.entity.vo.DepartmentVO;
import com.oracleclub.server.service.base.ConverterService;
import com.oracleclub.server.service.base.CrudService;

import java.util.List;

/**
 * @author RETURN
 * @since 2021-02-21 17:11:55
 */
public interface DepartmentService extends CrudService<Department,Long>, ConverterService<DepartmentVO,Department> {

    DepartmentVO createOrUpdateBy(Department department);

    DepartmentVO createBy(Department department);

    List<Department> listAllExist();
}
