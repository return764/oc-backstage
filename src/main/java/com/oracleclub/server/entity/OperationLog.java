package com.oracleclub.server.entity;

import com.oracleclub.server.entity.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * (OperationLogs)实体类
 *
 * @author makejava
 * @since 2021-02-21 17:08:07
 */
@Data
@Entity
@Table(name = "operation_logs")
public class OperationLog extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 184793492685024048L;
    /**
     * 操作者
     */
    @Column
    private String name;
    /**
     * 操作类型
     */
    @Column
    private String type;
    /**
     * 操作内容
     */
    @Column
    private String content;
    /**
     * 操作结果
     */
    @Column
    private Integer result;
    /**
     * 备注
     */
    @Column
    private String memo;

}
