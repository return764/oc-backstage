package com.oracleclub.server.controller.content;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oracleclub.server.entity.param.PageRequest;
import com.oracleclub.server.entity.vo.PostVO;
import com.oracleclub.server.entity.vo.SimplePostVO;
import com.oracleclub.server.handler.page.PageDefault;
import com.oracleclub.server.service.PostService;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author :RETURN
 * @date :2021/10/10 23:09
 */
@RestController("content_post_controller")
@RequestMapping("api/content/posts")
public class PostController {

    @Resource
    PostService postService;

    @GetMapping("{boardName}")
    public IPage<SimplePostVO> pagePost(@PageDefault PageRequest pageable, @PathVariable("boardName") String boardName){
        Assert.hasText(boardName,"路由名不能为空");

        return postService.pageBy(boardName,pageable.convertTo());
    }

    @GetMapping
    public PostVO getPost(Long id){
        Assert.notNull(id,"id不能为空");

        return postService.getHolePost(id);
    }

}
