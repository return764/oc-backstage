package com.oracleclub.server.entity.param;

import com.oracleclub.server.converter.InputConverter;
import com.oracleclub.server.entity.bbs.Comment;
import lombok.Data;

/**
 * @author :RETURN
 * @date :2021/11/13 11:22
 */
@Data
public class CommentParam implements InputConverter<Comment> {
    private Long postId;
    private String content;
    private Long issuerId;
    private Long parentId;
}
