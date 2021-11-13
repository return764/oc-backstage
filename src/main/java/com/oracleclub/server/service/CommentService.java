package com.oracleclub.server.service;

import com.oracleclub.server.entity.bbs.Comment;
import com.oracleclub.server.entity.vo.CommentVO;
import com.oracleclub.server.service.base.CrudService;

import java.util.List;

/**
 * @author :RETURN
 * @date :2021/11/8 0:31
 */
public interface CommentService extends CrudService<Comment, Long> {
    List<CommentVO> getAllCommentOfPostBy(Long postId);
}
