package com.oracleclub.server.controller.admin;

import com.oracleclub.server.entity.vo.R;
import com.oracleclub.server.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author RETURN
 * @since 2021-02-21 17:11:56
 */
@Slf4j
@RestController
@RequestMapping("api/admin/departments")
public class DepartmentController {


    @Resource
    private DepartmentService departmentService;


    @GetMapping("{id:\\d+}")
    public R getOne(@PathVariable Long id) {
        return R.success("获取部门成功",this.departmentService.getById(id));
    }

    @GetMapping
    public R listDepartment(){
        return R.success("获取部门列表成功",this.departmentService.listAll());
    }

}
