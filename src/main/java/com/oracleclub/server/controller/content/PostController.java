package com.oracleclub.server.controller.content;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oracleclub.server.entity.param.PageRequest;
import com.oracleclub.server.entity.vo.PostVO;
import com.oracleclub.server.entity.vo.SimplePostVO;
import com.oracleclub.server.handler.page.PageDefault;
import com.oracleclub.server.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author :RETURN
 * @date :2021/10/10 23:09
 */
@Slf4j
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

    @PostMapping(value = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String imageUpload(@RequestParam("image") MultipartFile image){
        Assert.notNull(image,"上传文件不能为空");
        log.info("上传文件中:{}",image);
        String name = postService.upload(image);
        return name;
    }

}
