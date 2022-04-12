package com.oracleclub.server.controller.content;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oracleclub.server.entity.User;
import com.oracleclub.server.entity.param.PageRequest;
import com.oracleclub.server.entity.param.UserUpdateParam;
import com.oracleclub.server.entity.support.LoginSign;
import com.oracleclub.server.entity.vo.R;
import com.oracleclub.server.entity.vo.ReplyMeReplyVO;
import com.oracleclub.server.entity.vo.SimplePostVO;
import com.oracleclub.server.handler.page.PageDefault;
import com.oracleclub.server.service.CommentService;
import com.oracleclub.server.service.PostService;
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
    @Resource
    PostService postService;
    @Resource
    CommentService commentService;

    @PutMapping("{id:\\d+}")
    public R update(@PathVariable("id") Long id,
                    @RequestBody UserUpdateParam userParam){
        Assert.notNull(userParam,"User参数不能为空");
        User beforeUpdateUser = userService.getUserAndDepartment(id);

        userParam.update(beforeUpdateUser);

        User afterUpdateUser = userService.update(beforeUpdateUser);
        return R.success("更新成功",afterUpdateUser);
    }

    @GetMapping("ownTopic")
    public IPage<SimplePostVO> ownTopic(@PageDefault PageRequest pageRequest, LoginSign loginSign) {
        Assert.notNull(loginSign, "重新登录");

        User user = loginSign.getUser();
        return postService.pageByUserId(user.getId(), pageRequest.convertTo());
    }

    @GetMapping("replayMe")
    public IPage<ReplyMeReplyVO> replayMe(@PageDefault PageRequest pageRequest, LoginSign loginSign) {
        Assert.notNull(loginSign, "重新登录");

        User user = loginSign.getUser();
        return commentService.pageReplyMeReplyByUserId(pageRequest.convertTo(), user.getId());
    }
}
