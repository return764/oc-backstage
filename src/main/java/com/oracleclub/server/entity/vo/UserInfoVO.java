package com.oracleclub.server.entity.vo;

import com.oracleclub.server.converter.OutputConverter;
import com.oracleclub.server.entity.User;
import lombok.Data;

/**
 * @author RETURN
 * @date 2020/10/1 22:44
 */
@Data
public class UserInfoVO implements OutputConverter<UserInfoVO, User> {

    /**
     * 用户id
     */
    private Long id;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 用户头像地址
     */
    private String avatar;
}
