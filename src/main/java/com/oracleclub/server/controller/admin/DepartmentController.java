package com.oracleclub.server.controller.admin;

import com.oracleclub.server.entity.Department;
import com.oracleclub.server.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Departments)表控制层
 *
 * @author makejava
 * @since 2021-02-21 17:11:56
 */
@RestController
@RequestMapping("department")
public class DepartmentController {
    /**
     * 服务对象
     */
    @Resource
    private DepartmentService departmentService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Department selectOne(Long id) {
        return this.departmentService.queryById(id);
    }

}
