package com.oracleclub.server.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oracleclub.server.entity.bbs.Comment;
import com.oracleclub.server.entity.param.CommentParam;
import com.oracleclub.server.entity.vo.CommentVO;
import com.oracleclub.server.entity.vo.ReplyMeReplyVO;
import com.oracleclub.server.service.base.ConverterService;
import com.oracleclub.server.service.base.CrudService;

import java.util.List;

/**
 * @author :RETURN
 * @date :2021/11/8 0:31
 */
public interface CommentService extends CrudService<Comment, Long>, ConverterService<CommentVO, Comment> {
    List<CommentVO> getAllCommentOfPostBy(Long postId);

    List<Comment> getAllByPostId(Long postId);

    void createByParams(CommentParam param);

    Long countByPost(Long postId);

    IPage<CommentVO> pageReply(IPage<Comment> pageable, Long commentId);

    IPage<ReplyMeReplyVO> pageReplyMeReplyByUserId(IPage<Comment> convertTo, Long id);

    void deleteByPostId(Long postId);
}
