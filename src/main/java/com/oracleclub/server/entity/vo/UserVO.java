package com.oracleclub.server.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.oracleclub.server.converter.InputConverter;
import com.oracleclub.server.converter.OutputConverter;
import com.oracleclub.server.entity.User;
import com.oracleclub.server.entity.enums.RoleEnum;
import com.oracleclub.server.entity.enums.UserStatus;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author RETURN
 * @date 2020/10/1 22:44
 */
@Data
public class UserVO implements OutputConverter<UserVO, User>, InputConverter<User> {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String name;
    private String email;
    private String nickname;
    private String stuNum;
    private String phNum;
    private String info;
    private String avatar;
    private UserStatus status;
    private RoleEnum role;
    private String ipAddr;
    private LocalDateTime loginAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private DepartmentVO department;
}
