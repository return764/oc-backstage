package com.oracleclub.server.controller;

import com.oracleclub.server.dao.UserDao;
import com.oracleclub.server.entity.Department;
import com.oracleclub.server.entity.User;
import com.oracleclub.server.entity.enums.RoleEnum;
import com.oracleclub.server.entity.enums.UserStatus;
import com.oracleclub.server.entity.vo.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author :RETURN
 * @date :2021/2/25 22:48
 */
@RestController
public class CommonController {

    @Resource
    UserDao userDao;

    @GetMapping("ping")
    public R ping(){

        User u = new User();
        u.setAvatar("asdasdasd.png");
        u.setBirthday(new Date());
        u.setEmail("5438514362qq.com");
        u.setInfo("asdasfxcvxcvxcv");
        u.setIpAddr(1344643131L);
        u.setName("小明");
        u.setNickname("asfsafasf");
        u.setRole(RoleEnum.NORMAL);
        u.setStatus(UserStatus.ACTIVE);
        u.setPassword("123456");
        u.setPhNum("12354548315");
        u.setStuNum("41804404");

        Department department = new Department();
        department.setId(1L);

        u.setDepartment(department);

        return R.success("pong",userDao.save(u));
    }
}
