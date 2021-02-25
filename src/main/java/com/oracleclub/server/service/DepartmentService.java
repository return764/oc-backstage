package com.oracleclub.server.service;

import com.oracleclub.server.entity.Department;

import java.util.List;

/**
 * (Departments)表服务接口
 *
 * @author makejava
 * @since 2021-02-21 17:11:55
 */
public interface DepartmentService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Department queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Department> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param departments 实例对象
     * @return 实例对象
     */
    Department insert(Department departments);

    /**
     * 修改数据
     *
     * @param departments 实例对象
     * @return 实例对象
     */
    Department update(Department departments);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
