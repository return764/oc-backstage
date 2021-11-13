package com.oracleclub.server.entity.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author :RETURN
 * @date :2021/11/8 0:25
 */
@Data
public class CommentVO {
    private Long id;
    private Long parentId;
    private SimpleUserVO issuer;
    private String content;
    private Long postId;
    private LocalDateTime createdAt;

    private List<CommentVO> children;
}
