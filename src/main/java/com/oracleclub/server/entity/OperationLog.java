package com.oracleclub.server.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.oracleclub.server.entity.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * (OperationLogs)实体类
 *
 * @author makejava
 * @since 2021-02-21 17:08:07
 */
@Data
@TableName("operation_logs")
public class OperationLog extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 184793492685024048L;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 操作类型
     */
    private String type;
    /**
     * 操作内容
     */
    private String content;
    /**
     * 操作结果
     */
    private Integer result;
    /**
     * 备注
     */
    private String memo;

}
