package com.oracleclub.server.entity.param;

import com.oracleclub.server.converter.InputConverter;
import com.oracleclub.server.entity.Department;
import com.oracleclub.server.entity.User;
import com.oracleclub.server.entity.enums.RoleEnum;
import com.oracleclub.server.entity.enums.UserStatus;
import lombok.Data;

/**
 * @author :RETURN
 * @date :2021/3/25 17:03
 */
@Data
public class UserUpdateParam implements InputConverter<User> {
    private String name;
    private String stuNum;
    private String nickname;
    private Department department;
    private String email;
    private String phNum;
    private UserStatus status;
    private RoleEnum role;
}
