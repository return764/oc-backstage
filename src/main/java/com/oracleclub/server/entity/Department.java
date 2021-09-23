package com.oracleclub.server.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName(value = "departments",resultMap = "departmentMap")
public class Department extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -91746248750737053L;

    /**
     * 部门名称
     */
    @TableField("dep_name")
    private String name;
    /**
     * 部门别名
     */
    @TableField("dep_alias_name")
    private String aliasName;
    /**
     * 部门介绍
     */
    @TableField("dep_content")
    private String content;

}
