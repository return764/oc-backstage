package com.oracleclub.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oracleclub.server.entity.base.BaseEntity;
import com.oracleclub.server.entity.enums.RoleEnum;
import com.oracleclub.server.entity.enums.UserStatus;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * (User)实体类
 *
 * @author RETURN
 * @since 2020-08-13 22:01:00
 */
@Data
@Entity
@Table(name = "users")
public class User extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 779866878072284592L;

    @Column(name = "dep_id")
    private Long depId;
    /**
     * 用户昵称
     */
    @Column
    private String nickname;
    @Column
    private String name;
    @Column(name = "stu_num")
    private String stuNum;
    /**
     * 用户邮箱
     */
    @Column
    private String email;
    /**
     * 用户密码
     */
    @JsonIgnore
    @Column
    private String password;
    @Column(name = "ph_num")
    private String phNum;
    @Column
    private String info;
    /**
     * 用户头像地址
     */
    @Column
    private String avatar;
    @Column
    private Date birthday;
    @Column
    @ColumnDefault("0")
    private UserStatus status;
    @Column
    @ColumnDefault("0")
    private RoleEnum role;
    @Column
    private Long ipAddr;
    @Column
    private Date loginAt;
}