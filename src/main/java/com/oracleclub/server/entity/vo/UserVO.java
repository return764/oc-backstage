package com.oracleclub.server.entity.vo;

import com.oracleclub.server.converter.OutputConverter;
import com.oracleclub.server.entity.User;
import com.oracleclub.server.entity.enums.RoleEnum;
import com.oracleclub.server.entity.enums.UserStatus;
import lombok.Data;

import java.util.Date;

/**
 * @author RETURN
 * @date 2020/10/1 22:44
 */
@Data
public class UserVO implements OutputConverter<UserVO, User> {

    private Long id;
    private String name;
    private String email;
    private String nickname;
    private String stuNum;
    private String info;
    private String avatar;
    private UserStatus status;
    private RoleEnum role;
    private String ipAddr;
    private Date loginAt;
    private Date createdAt;
    private Date updatedAt;
}
