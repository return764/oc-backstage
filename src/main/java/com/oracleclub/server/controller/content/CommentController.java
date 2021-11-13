package com.oracleclub.server.controller.content;

import com.oracleclub.server.entity.vo.CommentVO;
import com.oracleclub.server.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author :RETURN
 * @date :2021/11/8 0:29
 */
@Slf4j
@RestController
@RequestMapping("api/content/comments")
public class CommentController {

    @Resource
    CommentService commentService;

    @GetMapping
    public List<CommentVO> pageCommentByPost(Long postId) {
        Assert.notNull(postId,"postId不能为空");

        return commentService.getAllCommentOfPostBy(postId);
    }
}
