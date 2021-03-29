package com.oracleclub.server.controller.admin;

import com.oracleclub.server.entity.Department;
import com.oracleclub.server.entity.param.DepartmentParam;
import com.oracleclub.server.entity.vo.R;
import com.oracleclub.server.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
        return R.success("获取部门成功",departmentService.convertToVO(departmentService.getById(id)));
    }

    @GetMapping
    public R listDepartment(){
        return R.success("获取部门列表成功",departmentService.convertToListVO(departmentService.listAllExist()));
    }

    @DeleteMapping("{id:\\d+}")
    public R deleteArticle(@PathVariable("id")Long id,
                           @RequestParam(name = "soft",required = false,defaultValue = "true") boolean soft){
        Department department = new Department();
        if (soft){
            department = departmentService.removeLogicById(id);
        }else {
            department = departmentService.removeById(id);
        }
        return R.success("删除部门成功",departmentService.convertToVO(department));
    }

    @PutMapping("{id:\\d+}")
    public R updateDepartment(@PathVariable Long id,
                             @RequestBody DepartmentParam updateParams){
        Department department = departmentService.getById(id);

        updateParams.update(department);

        Department updateDepartment = departmentService.update(department);

        return R.success("更新部门成功",departmentService.convertToVO(updateDepartment));
    }

    @PostMapping
    public R createDepartment(@RequestBody DepartmentParam departmentParam){
        log.debug("departmentParam:{}",departmentParam);
        Department department = departmentParam.convertTo();
        return R.success("创建部门成功",departmentService.createBy(department));
    }

}
