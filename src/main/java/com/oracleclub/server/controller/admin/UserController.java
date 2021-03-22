package com.oracleclub.server.controller.admin;

import com.oracleclub.server.entity.param.UserParam;
import com.oracleclub.server.entity.vo.R;
import com.oracleclub.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author :RETURN
 * @date :2021/3/22 0:24
 */
@Slf4j
@RestController("ApiAdminUserController")
@RequestMapping("/api/admin/users")
public class UserController {

    @Resource
    UserService userService;

    @GetMapping
    public R listUsers(@PageableDefault(sort = "id",direction = Sort.Direction.DESC) Pageable pageable,
                       @RequestBody(required = false) UserParam userParam){
        log.debug("{}",pageable);
        log.debug("userParam:{}",userParam);
        return R.success("成功获取成员列表",userService.pageByParam(pageable,userParam));
    }
}
