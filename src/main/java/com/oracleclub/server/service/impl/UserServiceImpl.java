package com.oracleclub.server.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.oracleclub.server.dao.UserDao;
import com.oracleclub.server.entity.Department;
import com.oracleclub.server.entity.User;
import com.oracleclub.server.entity.enums.UserStatus;
import com.oracleclub.server.entity.param.UserQueryParam;
import com.oracleclub.server.entity.vo.AuthUserVO;
import com.oracleclub.server.entity.vo.DepartmentVO;
import com.oracleclub.server.entity.vo.UserVO;
import com.oracleclub.server.exception.LoginException;
import com.oracleclub.server.exception.VerifyCodeException;
import com.oracleclub.server.service.UserService;
import com.oracleclub.server.service.base.AbstractCrudService;
import com.oracleclub.server.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.LinkedList;
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
            log.debug("当前登录用户:[{}]",u);
            return getAuthUser(u);
        }
        throw new VerifyCodeException("验证码错误");
    }

    @Override
    public AuthUserVO loginEmail(String email, String password) {
        User u = userDao.findByEmail(email);
        log.debug("当前登录用户:[{}]",u);
        if(checkPassword(u,password)){
            return getAuthUser(u);
        }
        throw new LoginException("密码错误");
    }

    @Override
    public AuthUserVO loginStuNum(String stuNum, String password) {
        User u = userDao.findByStuNum(stuNum);
        log.debug("当前登录用户:[{}]",u);
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
    public Page<UserVO> pageByParam(Pageable pageable, UserQueryParam userParam) {
        Assert.notNull(pageable,"分页参数不能为空");
        Page<User> all = userDao.findAllWithExist(buildParam(userParam), pageable);
        return convertToPageVO(all);
    }

    private static Specification<User> buildParam(UserQueryParam userParam){
        Assert.notNull(userParam,"用户列表查询数据不能为空");

        return (Specification<User>) (root, cq, cb) -> {
            List<Predicate> predicates = new LinkedList<>();

            if (userParam.getName() != null){
                predicates.add(cb.like(root.get("name").as(String.class),"%"+userParam.getName()+"%"));
            }

            if (userParam.getDepId() != null){
                predicates.add(cb.equal(root.join("department").get("id").as(Long.class),userParam.getDepId()));
            }

            if (userParam.getEmail() != null){
                predicates.add(cb.like(root.get("email").as(String.class),"%"+userParam.getEmail()+"%"));
            }

            if (userParam.getPhNum() != null){
                predicates.add(cb.like(root.get("phNum").as(String.class),"%"+userParam.getPhNum()+"%"));
            }

            if (userParam.getStuNum() != null){
                predicates.add(cb.like(root.get("stuNum").as(String.class),"%"+userParam.getStuNum()+"%"));
            }

            if (userParam.getStatus() != null){
                predicates.add(cb.equal(root.get("status").as(UserStatus.class),userParam.getStatus()));
            }

            if (userParam.getLoginStart() != null){
                predicates.add(cb.greaterThanOrEqualTo(root.get("loginAt").as(LocalDateTime.class),userParam.getLoginStart()));
            }

            if (userParam.getLoginEnd() != null){
                predicates.add(cb.lessThanOrEqualTo(root.get("loginAt").as(LocalDateTime.class),userParam.getLoginEnd()));
            }


            return cq.where(predicates.toArray(new Predicate[0])).getRestriction();
        };
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
    public Page<UserVO> convertToPageVO(Page<User> users) {
        return users.map(this::convertToVO);
    }

    private AuthUserVO getAuthUser(User user) {
        AuthUserVO authUser = new AuthUserVO().convertFrom(user);
        String token = tokenCache.get(String.valueOf(user.getId()));
        if (null == token) {
            token = JwtUtil.signUser(user.getId());
        }
        authUser.setDepartmentName(user.getDepartment().getName());
        authUser.setToken(token);
        tokenCache.put(String.valueOf(user.getId()), token);
        return authUser;
    }

}
