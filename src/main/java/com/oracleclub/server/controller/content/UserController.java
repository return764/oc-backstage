package com.oracleclub.server.controller.content;

import com.oracleclub.server.entity.User;
import com.oracleclub.server.entity.param.UserUpdateParam;
import com.oracleclub.server.entity.vo.R;
import com.oracleclub.server.service.UserService;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author :RETURN
 * @date :2021/9/18 11:00
 */
@RestController("content_user_controller")
@RequestMapping("api/content/users")
public class UserController {

    @Resource
    UserService userService;

    @PutMapping("{id:\\d+}")
    public R update(@PathVariable("id") Long id,
                    @RequestBody UserUpdateParam userParam){
        Assert.notNull(userParam,"User参数不能为空");
        User beforeUpdateUser = userService.getById(id);

        userParam.update(beforeUpdateUser);

        User afterUpdateUser = userService.update(beforeUpdateUser);
        return R.success("更新成功",afterUpdateUser);
    }
}
