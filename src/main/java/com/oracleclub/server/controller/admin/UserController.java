package com.oracleclub.server.controller.admin;

import com.oracleclub.server.entity.User;
import com.oracleclub.server.entity.param.UserQueryParam;
import com.oracleclub.server.entity.param.UserUpdateParam;
import com.oracleclub.server.entity.vo.R;
import com.oracleclub.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
                       UserQueryParam userQueryParam){
        log.debug("params:{}",userQueryParam);
        return R.success("成功获取成员列表",userService.pageByParam(pageable,userQueryParam));
    }

    @DeleteMapping("{id:\\d+}")
    public R deleteArticle(@PathVariable("id")Long id,
                           @RequestParam(name = "soft",required = false,defaultValue = "true") boolean soft){
        User user = new User();
        if (soft){
            user = userService.removeLogicById(id);
        }else {
            user = userService.removeById(id);
        }
        return R.success("删除用户成功",userService.convertToVO(user));
    }

    @DeleteMapping
    public R deleteUsersInBatch(@RequestBody List<Long> ids,
                                @RequestParam(name = "soft",required = false,defaultValue = "true")boolean soft){
        if (soft){
            ids.forEach(userService::removeLogicById);
        }else {
            userService.removeInBatch(ids);
        }
        return R.success("成功删除用户列表");
    }

    @PutMapping("{id:\\d+}")
    public R updateUser(@PathVariable Long id,
            @RequestBody UserUpdateParam userUpdateParam){
        log.debug("params:{}",userUpdateParam);
        log.debug("department:{}",userUpdateParam.getDepartment());
        User user = userService.getByIdExist(id);

        userUpdateParam.update(user);

        User u = userService.update(user);
        return R.success("成功更新用户",userService.convertToVO(u));
    }
}
