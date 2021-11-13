package com.oracleclub.server.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author :RETURN
 * @date :2021/11/8 0:25
 */
@Data
public class CommentVO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;
    private SimpleUserVO issuer;
    private String content;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long postId;
    private LocalDateTime createdAt;
    private List<CommentVO> children;
}
