package com.oracleclub.server.controller.content;

import com.oracleclub.server.entity.Article;
import com.oracleclub.server.entity.enums.ArticleStatus;
import com.oracleclub.server.entity.param.ArticleQueryParam;
import com.oracleclub.server.entity.vo.ArticleDetailVO;
import com.oracleclub.server.entity.vo.ArticleSimpleVO;
import com.oracleclub.server.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author :RETURN
 * @date :2021/6/18 20:28
 */
@Slf4j
@RestController("content_article_controller")
@RequestMapping("api/content/articles")
public class ArticleController {

    @Resource
    ArticleService articleService;

    @GetMapping("latest")
    public ArticleDetailVO latest(){
        //todo this latest include createdAt and updatedAt ,
       return articleService.convertToVO(articleService.listLatest(1).get(0));
    }

    @GetMapping
    public Page<ArticleSimpleVO> pageBy(@PageableDefault(sort = "id",direction = Sort.Direction.DESC) Pageable pageable){
        ArticleQueryParam params = new ArticleQueryParam();
        params.setStatus(ArticleStatus.PUBLISHED);

        Page<Article> articles = articleService.pageBy(params,pageable);
        return articleService.convertToSimplePage(articles);
    }

    @GetMapping("{id:\\d+}")
    public ArticleDetailVO getBy(@PathVariable("id") Long id){
        Article article = articleService.getByIdExist(id);
        return articleService.convertToVO(article);
    }
}
