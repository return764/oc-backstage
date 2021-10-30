package com.oracleclub.server.entity.bbs;

import com.baomidou.mybatisplus.annotation.TableName;
import com.oracleclub.server.entity.base.BaseEntity;
import lombok.Data;

/**
 * @author :RETURN
 * @date :2021/10/2 16:49
 */
@Data
@TableName("bbs_posts")
public class Post extends BaseEntity {
    private String name;
    private String content;
    private Long issuerId;
    private Long likeCount;
    private boolean canComment;
    private boolean isTop;
    private Long boardId;
}
