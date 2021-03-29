package com.oracleclub.server.controller;

import com.oracleclub.server.annotation.PassToken;
import com.oracleclub.server.entity.param.LoginParam;
import com.oracleclub.server.entity.vo.AuthUserVO;
import com.oracleclub.server.entity.vo.R;
import com.oracleclub.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * (User)表控制层
 *
 * @author RETURN
 * @since 2020-08-13 22:01:00
 */
@RestController
@Slf4j
@RequestMapping("api/users")
public class UserController {

    @Resource
    private UserService userService;
    @Resource(name = "verifyCode")
    private Cache<String, String> verifyCodeCache;

    @PostMapping("verifyCode")
    @PassToken
    public R verifyCode(String email) {
        log.debug("发送验证码 -> 邮箱:{}", email);
        Integer v = (int) ((Math.random() * 9 + 1) * 100000);
        String verifyCode = String.valueOf(v);
        verifyCodeCache.put(email, verifyCode);
        log.debug("验证码 -> {}", verifyCode);
        userService.sendVerifyCode(email, "您的验证码", verifyCode);

        return R.builder().result("ok").msg("验证码发送成功").build();
    }

    @PostMapping("login/verify")
    @PassToken
    public R loginVerify(String email, String verifyCode) {
        log.debug("邮箱:{},验证码:{}", email, verifyCode);
        Assert.notNull(email, "用户邮箱不能为空");
        Assert.notNull(verifyCode, "验证码不能为空");

        AuthUserVO userDetail = userService.loginVerify(email, verifyCode);
        if (verifyCodeCache.containsKey(email)) {
            verifyCodeCache.remove(email);
        }
        return R.success("登录成功",userDetail);
    }

    @PassToken
    @PostMapping("login/email")
    public R loginEmail(@RequestBody @Valid LoginParam loginParam) {
        String email = loginParam.getUsername();
        String password = loginParam.getPassword();
        log.debug("邮箱:{},密码:{}", email, password);
        Assert.notNull(email, "用户邮箱不能为空");
        Assert.notNull(password, "密码不能为空");

        AuthUserVO userDetail = userService.loginEmail(email, password);
        return R.success("登录成功",userDetail);
    }

    @PassToken
    @PostMapping("login/stuNum")
    public R loginStuNum(@RequestBody @Valid LoginParam loginParam) {
        String stuNum = loginParam.getUsername();
        String password = loginParam.getPassword();
        log.debug("学号:{},密码:{}", stuNum, password);
        Assert.notNull(stuNum, "学号不能为空");
        Assert.notNull(password, "密码不能为空");

        AuthUserVO userDetail = userService.loginStuNum(stuNum, password);
        return R.success("登录成功",userDetail);
    }


}
