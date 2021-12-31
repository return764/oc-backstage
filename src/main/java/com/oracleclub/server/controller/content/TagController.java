package com.oracleclub.server.controller.content;

import com.oracleclub.server.entity.vo.TagVO;
import com.oracleclub.server.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author :RETURN
 * @date :2021/12/31 11:18
 */
@Slf4j
@RestController("content_tags_controller")
@RequestMapping("api/content/tags")
public class TagController {

    @Autowired
    TagService tagService;

    @GetMapping
    public List<TagVO> list() {
        return tagService.convertToListVO(tagService.listTags());
    }
}
