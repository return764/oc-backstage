package com.oracleclub.server.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.oracleclub.server.converter.OutputConverter;
import com.oracleclub.server.entity.base.BaseEntity;
import com.oracleclub.server.entity.bbs.Post;
import lombok.Data;

/**
 * @author :RETURN
 * @date :2021/10/29 10:58
 */
@Data
public class PostVO extends BaseEntity implements OutputConverter<PostVO, Post> {
    private String name;
    private String content;
    private SimpleUserVO issuer;
    private Long likeCount;
    private boolean canComment;
    private boolean isTop;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long boardId;
}
