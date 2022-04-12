package com.oracleclub.server.entity.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oracleclub.server.converter.OutputConverter;
import com.oracleclub.server.entity.OperationLog;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class OperationLogVO implements OutputConverter<OperationLogVO, OperationLog> {
    private Long id;
    /**
     * 用户id
     */
    private SimpleUserVO user;
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

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @JsonIgnore
    private LocalDateTime deletedAt;
}
