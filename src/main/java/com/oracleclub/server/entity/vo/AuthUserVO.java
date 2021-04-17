package com.oracleclub.server.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.oracleclub.server.converter.OutputConverter;
import com.oracleclub.server.entity.User;
import com.oracleclub.server.entity.enums.RoleEnum;
import com.oracleclub.server.entity.enums.UserStatus;
import com.oracleclub.server.entity.support.LoginToken;
import lombok.Data;

import java.util.Date;

/**
 * @author RETURN
 * @date 2020/9/6 13:45
 */
@Data
public class AuthUserVO implements OutputConverter<AuthUserVO, User> {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String departmentName;
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
    /**
     * token
     */
    private LoginToken token;
}
