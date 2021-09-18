package com.oracleclub.server.entity.param;

import com.oracleclub.server.converter.InputConverter;
import com.oracleclub.server.entity.User;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author :RETURN
 * @date :2021/8/25 22:21
 */
@Data
public class RegisterParam implements InputConverter<User> {
    @Email
    @NotBlank(message = "邮箱不能为空")
    private String email;
    @NotBlank(message = "密码不能为空")
    private String password;
    private String stuNum;
    private String nickname;
    private String phNum;
}
