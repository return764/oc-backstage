package com.oracleclub.server.service;

import com.oracleclub.server.entity.User;
import com.oracleclub.server.entity.vo.AuthUserVO;
import com.oracleclub.server.entity.vo.UserVO;
import com.oracleclub.server.service.base.ConverterService;
import com.oracleclub.server.service.base.CrudService;
import org.springframework.web.multipart.MultipartFile;

/**
 * (User)表服务接口
 *
 * @author RETURN
 * @since 2020-08-13 22:01:00
 */
public interface UserService extends CrudService<User,Long>, ConverterService<UserVO,User> {

    /**
     * 用户修改头像
     * @param picture 图片
     * @param userId 用户id
     */
    void uploadAvatar(MultipartFile picture, Long userId);

    /**
     * 用户更新昵称
     * @param nickname 昵称
     * @param userId 用户id
     */
    void updateNickname(String nickname, Long userId);

    /**
     * 用户修改密码
     * @param userId 用户id
     * @param password 密码
     */
    void updatePassword(Long userId, String password);

    /**
     * 获取验证码
     * @param email 用户邮箱
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    void sendVerifyCode(String email, String subject, String content);

    /**
     * 邮箱验证码登录
     * @param email 用户邮箱
     * @return
     */
    AuthUserVO loginVerify(String email, String verifyCode);

    /**
     * 邮箱密码登录
     * @param email
     * @param password
     * @return
     */
    AuthUserVO loginEmail(String email, String password);

    /**
     * 学号密码登录
     * @param phNum
     * @param password
     * @return
     */
    AuthUserVO loginStuNum(String phNum,String password);

    boolean checkPassword(User user,String password);

    UserVO convertToVO(User user);

}
