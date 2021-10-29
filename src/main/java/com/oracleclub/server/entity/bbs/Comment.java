package com.oracleclub.server.entity.bbs;

import com.baomidou.mybatisplus.annotation.TableName;
import com.oracleclub.server.entity.base.BaseEntity;
import lombok.Data;

/**
 * @author :RETURN
 * @date :2021/10/2 16:53
 */
@Data
@TableName("bbs_comments")
public class Comment extends BaseEntity {
    private Long parentId;
    private String issuerName;
    private String content;
    private Long postId;
}
