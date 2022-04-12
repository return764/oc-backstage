package com.oracleclub.server.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oracleclub.server.dao.DepartmentMapper;
import com.oracleclub.server.dao.UserMapper;
import com.oracleclub.server.entity.Department;
import com.oracleclub.server.entity.User;
import com.oracleclub.server.entity.enums.Role;
import com.oracleclub.server.entity.enums.UserStatus;
import com.oracleclub.server.entity.param.RegisterParam;
import com.oracleclub.server.entity.param.UserQueryParam;
import com.oracleclub.server.entity.support.LoginToken;
import com.oracleclub.server.entity.vo.AuthUserVO;
import com.oracleclub.server.entity.vo.DepartmentVO;
import com.oracleclub.server.entity.vo.SimpleUserVO;
import com.oracleclub.server.entity.vo.UserVO;
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
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (User)表服务实现类
 *
 * @author RETURN
 * @since 2020-08-13 22:01:00
 */
@Service
@Slf4j
public class UserServiceImpl extends AbstractCrudService<User,Long> implements UserService {

    private final UserMapper userMapper;
    private final DepartmentMapper departmentMapper;
    private final Cache<String, String> verifyCodeCache;
    private final Cache<String, String> tokenCache;
    private Cache<String,String> refreshTokenCache;
    private final ApplicationEventPublisher eventPublisher;
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    String username;

    private static final String NICKNAME_REG = "^[\u4E00-\u9FA5A-Za-z\\s+-]{2,64}$";

    protected UserServiceImpl(UserMapper userMapper,
                              DepartmentMapper departmentMapper,
                              JavaMailSender mailSender,
                              @Qualifier("verifyCode") Cache<String, String> verifyCodeCache,
                              @Qualifier("refreshTokenCache") Cache<String, String> refreshTokenCache,
                              Cache<String, String> tokenCache,
                              ApplicationEventPublisher eventPublisher) {
        super(userMapper);
        this.userMapper = userMapper;
        this.departmentMapper = departmentMapper;
        this.mailSender = mailSender;
        this.verifyCodeCache = verifyCodeCache;
        this.tokenCache = tokenCache;
        this.refreshTokenCache = refreshTokenCache;
        this.eventPublisher = eventPublisher;
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
        log.debug("带匹配的验证码：{}", matchVerifyCode);
        if (verifyCode.equals(matchVerifyCode)) {
            User u = userMapper.findByEmail(email);
            log.debug("当前登录用户:[{}]",u);
            u.setLoginAt(LocalDateTime.now());
            userMapper.updateById(u);

            return getAuthUser(u);
        }
        throw new VerifyCodeException("验证码错误");
    }

    @Override
    public AuthUserVO loginEmail(String email, String password) {
        User u = userMapper.findByEmail(email);
        log.debug("当前登录用户:[{}]",u);
        if(u == null){
            throw new LoginException("账号或密码错误");
        }
        if(checkPassword(u,password)){
            u.setLoginAt(LocalDateTime.now());
            userMapper.updateLoginTimeBy(u);

            return getAuthUser(u);
        }
        throw new LoginException("账号或密码错误");
    }

    @Override
    public AuthUserVO loginStuNum(String stuNum, String password) {
        User u = userMapper.findByStuNum(stuNum);
        log.debug("当前登录用户:[{}]",u);
        if(checkPassword(u,password)){
            u.setLoginAt(LocalDateTime.now());
            userMapper.updateById(u);

            return getAuthUser(u);
        }
        throw new LoginException("密码错误");
    }


    @Override
    public boolean checkPassword(User user,String password) {
        String pwd = user.getPassword();
        return DigestUtil.md5Hex(password).equals(pwd);
    }

    @Override
    public IPage<UserVO> pageBy(IPage<User> pageable, UserQueryParam userParam) {
        Assert.notNull(pageable,"分页参数不能为空");
        IPage<User> all = userMapper.findAllExistWithParams(pageable, userParam);
        return convertToPageVO(all);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public AuthUserVO register(RegisterParam registerParam) {
        Assert.notNull(registerParam,"参数不能为空");
        User user = registerParam.convertTo();
        user.setRole(Role.VISITOR);
        user.setStatus(UserStatus.NOT_ACTIVE);
        user.setLoginAt(LocalDateTime.now());
        //检查是否存在学号
        if(user.getStuNum() != null){
            User test = userMapper.findByStuNum(user.getStuNum());
            if (test!=null){
                throw new UserException("学号已存在");
            }
        }
        //检查是否存在邮箱
        if(user.getEmail() != null){
            User test = userMapper.findByEmail(user.getEmail());
            if (test!=null){
                throw new UserException("邮箱已存在");
            }
        }
        //加密密码
        user.setPassword(DigestUtil.md5Hex(user.getPassword()));
        userMapper.insertUser(user);
        userMapper.insertUserRole(user.getId(), user.getRole());
        User u = userMapper.findUserById(user.getId());

        return getAuthUser(u);
    }

    @Override
    public User getUserAndDepartment(Long id) {
        return userMapper.findUserById(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public User updateUserInfo(User user) {

        userMapper.updateUserInfoBy(user);

        return userMapper.selectById(user.getId());
    }

    @Override
    public SimpleUserVO convertToSimpleVO(User user) {
        return new SimpleUserVO().convertFrom(user);
    }

    @Override
    public UserVO convertToVO(User user) {
        Assert.notNull(user,"用户不存在");

        UserVO userVO = new UserVO().convertFrom(user);
        Department department = user.getDepartment();
        if (department != null){
            userVO.setDepartment(new DepartmentVO().convertFrom(department));
        }
        return userVO;
    }

    @Override
    public List<UserVO> convertToListVO(List<User> users) {
        return users.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public IPage<UserVO> convertToPageVO(IPage<User> users) {
        return users.convert(this::convertToVO);
    }

    private AuthUserVO getAuthUser(User user) {
        AuthUserVO authUser = new AuthUserVO().convertFrom(user);
        LoginToken loginToken = JwtUtil.signUser(user.getId());

        if (user.getDepartment() != null){
            authUser.setDepartmentName(user.getDepartment().getName());
        }
        authUser.setToken(loginToken);

        tokenCache.put(String.valueOf(user.getId()), loginToken.getToken());
        refreshTokenCache.put(String.valueOf(user.getId()), loginToken.getRefreshToken());
        return authUser;
    }

}
