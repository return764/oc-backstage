package com.oracleclub.server.controller.content;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oracleclub.server.entity.param.PageRequest;
import com.oracleclub.server.entity.param.PictureQueryParam;
import com.oracleclub.server.entity.vo.PictureVO;
import com.oracleclub.server.handler.page.PageDefault;
import com.oracleclub.server.service.PictureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author :RETURN
 * @date :2021/7/22 16:10
 */
@Slf4j
@RestController("content_picture_controller")
@RequestMapping("api/content/pictures")
public class PictureController {
    @Resource
    private PictureService pictureService;

    @GetMapping
    public IPage<PictureVO> listPicture(@PageDefault PageRequest pageable,
                                        PictureQueryParam pictureQueryParam) {
        log.debug("pageRequest:{}",pageable);
        return pictureService.pageBy(pageable.convertTo(), pictureQueryParam);
    }

}
