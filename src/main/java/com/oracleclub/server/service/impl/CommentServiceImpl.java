package com.oracleclub.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oracleclub.server.dao.CommentMapper;
import com.oracleclub.server.dao.UserMapper;
import com.oracleclub.server.entity.bbs.Comment;
import com.oracleclub.server.entity.param.CommentParam;
import com.oracleclub.server.entity.vo.CommentVO;
import com.oracleclub.server.service.CommentService;
import com.oracleclub.server.service.UserService;
import com.oracleclub.server.service.base.AbstractCrudService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author :RETURN
 * @date :2021/11/8 0:32
 */
@Service
public class CommentServiceImpl extends AbstractCrudService<Comment, Long> implements CommentService {

    private final CommentMapper commentMapper;
    private final UserMapper userMapper;
    private final UserService userService;

    protected CommentServiceImpl(CommentMapper commentMapper, UserMapper userMapper, UserService userService) {
        super(commentMapper);
        this.commentMapper = commentMapper;
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @Override
    public List<CommentVO> getAllCommentOfPostBy(Long postId) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("post_id",postId);
        queryWrapper.orderByAsc("created_at");
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        List<CommentVO> outerComments = new ArrayList<>();
        Map<Long, CommentVO> map = new HashMap<>(16);

        comments.stream().map(c -> {
            CommentVO commentVO = new CommentVO();
            commentVO.setId(c.getId());
            commentVO.setContent(c.getContent());
            commentVO.setParentId(c.getParentId());
            commentVO.setPostId(c.getPostId());
            commentVO.setCreatedAt(c.getCreatedAt());
            commentVO.setIssuer(userService.convertToSimpleVO(userMapper.findUserById(c.getIssuerId())));
            if (c.getParentId() == null) {
                outerComments.add(commentVO);
            }
            map.put(commentVO.getId(), commentVO);
            return commentVO;
        }).forEach(c -> {
            if (c.getParentId() != null){
                CommentVO parent = map.get(c.getParentId());
                if (parent.getChildren() == null) {
                    parent.setChildren(new ArrayList<>());
                }
                parent.getChildren().add(c);
            }
        });

        return outerComments;
    }

    @Override
    public void createByParams(CommentParam param) {
        Comment comment = param.convertTo();

        int i = commentMapper.insert(comment);
        if (i < 1) {
            throw new RuntimeException("评论失败");
        }
    }

}
