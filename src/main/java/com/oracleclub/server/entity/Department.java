package com.oracleclub.server.entity;

import com.oracleclub.server.entity.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * (Departments)实体类
 *
 * @author makejava
 * @since 2021-02-21 17:11:55
 */
@Data
@Entity
@Table(name = "departments")
public class Department extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -91746248750737053L;

    /**
     * 部门名称
     */
    @Column
    private String name;
    /**
     * 部门别名
     */
    @Column(name = "alias_name")
    private String aliasName;
    /**
     * 部门介绍
     */
    @Column
    private String content;

}
