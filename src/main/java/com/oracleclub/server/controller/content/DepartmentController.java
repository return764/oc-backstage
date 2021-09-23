package com.oracleclub.server.controller.content;

import com.oracleclub.server.entity.Department;
import com.oracleclub.server.entity.vo.DepartmentVO;
import com.oracleclub.server.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author :RETURN
 * @date :2021/6/17 0:11
 */
@Slf4j
@RestController("content_department_controller")
@RequestMapping("api/content/departments")
public class DepartmentController {

    @Resource
    private DepartmentService departmentService;

    @GetMapping
    public List<DepartmentVO> list(){
        List<Department> departments = departmentService.listAllExist();
        log.debug(departments.toString());
        return departmentService.convertToListVO(departments);
    }
}
