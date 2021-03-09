package com.oracleclub.server.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.oracleclub.server.dao.UserDao;
import com.oracleclub.server.entity.User;
import com.oracleclub.server.entity.vo.AuthUserVO;
import com.oracleclub.server.entity.vo.UserInfoVO;
import com.oracleclub.server.exception.LoginException;
import com.oracleclub.server.exception.UserException;
import com.oracleclub.server.exception.VerifyCodeException;
import com.oracleclub.server.service.UserService;
import com.oracleclub.server.service.base.AbstractCrudService;
import com.oracleclub.server.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * (User)表服务实现类
 *
 * @author RETURN
 * @since 2020-08-13 22:01:00
 */
@Service
@Slf4j
public class UserServiceImpl extends AbstractCrudService<User,Long> implements UserService {

    private final UserDao userDao;
    private final Cache<String, String> verifyCodeCache;
    private final Cache<String, String> tokenCache;
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    String username;

    private static final String NICKNAME_REG = "^[\u4E00-\u9FA5A-Za-z\\s+-]{2,64}$";

    protected UserServiceImpl(UserDao userDao,
                              JavaMailSender mailSender,
                              @Qualifier("verifyCode") Cache<String, String> verifyCodeCache,
                              Cache<String, String> tokenCache) {
        super(userDao);
        this.userDao = userDao;
        this.mailSender = mailSender;
        this.verifyCodeCache = verifyCodeCache;
        this.tokenCache = tokenCache;
    }


    @Override
    @Transactional(rollbackFor = {RuntimeException.class})
    public void uploadAvatar(MultipartFile picture, Long userId) {
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class})
    public void updateNickname(String nickname, Long userId) {
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class})
    public void updatePassword(Long userId, String password) {
    }

    @Override
    public void sendVerifyCode(String email, String subject, String verifyCode) {
        log.debug("开始发送验证码");

        MimeMessage message = mailSender.createMimeMessage();
        String content = "<html><body><h1>您的验证码是"+verifyCode+"</h1></body></html>";

        MimeMessageHelper messageHelper = null;
        try {
            messageHelper = new MimeMessageHelper(message,true);
            messageHelper.setFrom(username);
            messageHelper.setTo(email);
            messageHelper.setSubject("验证码");
            messageHelper.setText(content,true);
            mailSender.send(message);
            log.debug("验证码发送成功{}:",message);
        } catch (MessagingException e) {
            throw new VerifyCodeException("验证码发送失败");
        }

    }

    @Override
    public AuthUserVO loginVerify(String email, String verifyCode) {
        log.debug("邮箱验证码登录开始");
        String matchVerifyCode = verifyCodeCache.get(email);
        if (verifyCode.equals(matchVerifyCode)) {
            User u = userDao.findByEmail(email);
            if (u != null) {
                log.debug("邮箱验证码登录成功");
                return getAuthUser(u);
            }
            throw new UserException("用户不存在");
        }
        throw new VerifyCodeException("验证码错误");
    }

    @Override
    public AuthUserVO loginEmail(String email, String password) {
        User u = userDao.findByEmail(email);
        if(checkPassword(u,password)){
            return getAuthUser(u);
        }
        throw new LoginException("密码错误");
    }

    @Override
    public AuthUserVO loginStuNum(String stuNum, String password) {
        User u = userDao.findByStuNum(stuNum);
        if(checkPassword(u,password)){
            return getAuthUser(u);
        }
        throw new LoginException("密码错误");
    }


    @Override
    public boolean checkPassword(User user,String password) {
        Assert.notNull(user,"User must not be null");
        String pwd = user.getPassword();
        return DigestUtil.md5Hex(password).equals(pwd);
    }

    @Override
    public UserInfoVO convertToInfoVo(User user) {
        Assert.notNull(user,"User must not be null");
        return new UserInfoVO().convertFrom(user);
    }

    private AuthUserVO getAuthUser(User user) {
        AuthUserVO authUser = new AuthUserVO().convertFrom(user);
        String token = tokenCache.get(String.valueOf(user.getId()));
        if (null == token) {
            token = JwtUtil.signUser(user.getId());
        }
        authUser.setToken(token);
        tokenCache.put(String.valueOf(user.getId()), token);
        return authUser;
    }

}
