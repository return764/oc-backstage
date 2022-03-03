package com.oracleclub.server.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oracleclub.server.entity.base.BaseEntity;
import com.oracleclub.server.entity.enums.Role;
import com.oracleclub.server.entity.enums.UserStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (User)实体类
 *
 * @author RETURN
 * @since 2020-08-13 22:01:00
 */
@Data
@TableName("users")
public class User extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 779866878072284592L;
    /**
     * 用户昵称
     */
    private String nickname;
    private String name;
    private String stuNum;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 用户密码
     */
    @JsonIgnore
    private String password;
    private String phNum;
    private String info;
    /**
     * 用户头像地址
     */
    private String avatar;
    private LocalDateTime birthday;
    private UserStatus status;
    @TableField(exist = false)
    private Role role;
    private Long ipAddr;
    private LocalDateTime loginAt;
    @TableField(exist = false)
    private Department department;
}
