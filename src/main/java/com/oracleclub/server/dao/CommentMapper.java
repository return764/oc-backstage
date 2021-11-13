package com.oracleclub.server.dao;

import com.oracleclub.server.dao.base.BaseDao;
import com.oracleclub.server.entity.bbs.Comment;
import org.springframework.stereotype.Repository;

/**
 * @author :RETURN
 * @date :2021/11/8 0:34
 */
@Repository
public interface CommentMapper extends BaseDao<Comment, Long> {

    Long count(Long postId);
}
