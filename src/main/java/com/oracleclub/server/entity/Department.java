package com.oracleclub.server.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.oracleclub.server.entity.base.BaseEntity;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * (Departments)实体类
 *
 * @author makejava
 * @since 2021-02-21 17:11:55
 */
@Data
@ToString(callSuper = true)
@TableName(value = "departments")
public class Department extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -91746248750737053L;

    /**
     * 部门名称
     */
    private String name;
    /**
     * 部门别名
     */
    private String aliasName;
    /**
     * 部门介绍
     */
    private String content;

}
