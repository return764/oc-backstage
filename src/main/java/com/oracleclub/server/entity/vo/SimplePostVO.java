package com.oracleclub.server.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.oracleclub.server.converter.OutputConverter;
import com.oracleclub.server.entity.bbs.Post;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author :RETURN
 * @date :2021/10/29 10:59
 */
@Data
public class SimplePostVO implements OutputConverter<SimplePostVO, Post> {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String name;
    private SimpleUserVO issuer;
    private boolean canComment;
    private boolean isTop;
    private Long boardId;
    private LocalDateTime createdAt;
}
