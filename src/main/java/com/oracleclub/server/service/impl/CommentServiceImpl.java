package com.oracleclub.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oracleclub.server.dao.CommentMapper;
import com.oracleclub.server.dao.UserMapper;
import com.oracleclub.server.entity.base.BaseEntity;
import com.oracleclub.server.entity.bbs.Comment;
import com.oracleclub.server.entity.param.CommentParam;
import com.oracleclub.server.entity.vo.CommentVO;
import com.oracleclub.server.service.CommentService;
import com.oracleclub.server.service.UserService;
import com.oracleclub.server.service.base.AbstractCrudService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
        queryWrapper.orderByDesc("created_at");
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        List<CommentVO> outerComments = new ArrayList<>();
        Map<Long, CommentVO> map = new HashMap<>(16);

        comments.forEach(c-> {
            if (c.getRootId() == null) {
                //为不存在rootId的post设置上rootId(为comment的id)
                c.setRootId(c.getId());
            }
        });
        //取得该post的所有评论的去重rootId
        List<Long> rootIds = comments.stream().map(Comment::getRootId).distinct().collect(Collectors.toList());
        Map<Long, List<Comment>> collect = comments.stream().collect(Collectors.groupingBy(Comment::getRootId));
        Map<Long, Long> countMap = comments.stream().collect(Collectors.groupingBy(Comment::getRootId,Collectors.counting()));

        rootIds.stream().flatMap(id -> {
            List<Comment> commentGroupByRootId = collect.get(id);
            return commentGroupByRootId.stream().sorted(Comparator.comparing(BaseEntity::getCreatedAt)).limit(4);
        }).map(this::convertToVO).peek(c -> {
            if (c.getParentId() == null) {
                c.setCount(countMap.get(c.getRootId()) - 1);
                outerComments.add(c);
            }
            map.put(c.getId(), c);
        }).forEach(c -> {
            if (c.getParentId() != null){
                CommentVO parent = map.get(c.getParentId());
                if (parent.getChildren() == null) {
                    parent.setChildren(new ArrayList<>());
                }
                parent.getChildren().add(c);
            }
        });

        return checkAndSplit(outerComments);
    }

    private List<CommentVO> checkAndSplit(List<CommentVO> comments) {

        return comments;
    }

    @Override
    public void createByParams(CommentParam param) {
        Comment comment = param.convertTo();

        int i = commentMapper.insert(comment);
        if (i < 1) {
            throw new RuntimeException("评论失败");
        }
    }

    @Override
    public Long countByPost(Long postId) {
        return commentMapper.count(postId);
    }

    @Override
    public IPage<CommentVO> pageReply(IPage<Comment> pageable, Long commentId) {
        IPage<Comment> comments = commentMapper.pageReplyInComment(pageable, commentId);
        return this.convertToPageVO(comments);
    }

    @Override
    public CommentVO convertToVO(Comment c) {
        CommentVO commentVO = new CommentVO();
        commentVO.setId(c.getId());
        commentVO.setContent(c.getContent());
        commentVO.setParentId(c.getParentId());
        commentVO.setRootId(c.getRootId());
        commentVO.setPostId(c.getPostId());
        commentVO.setCreatedAt(c.getCreatedAt());
        commentVO.setIssuer(userService.convertToSimpleVO(userMapper.findUserById(c.getIssuerId())));
        return commentVO;
    }

}
