package com.oracleclub.server.controller.content;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oracleclub.server.entity.Article;
import com.oracleclub.server.entity.enums.ArticleStatus;
import com.oracleclub.server.entity.param.ArticleQueryParam;
import com.oracleclub.server.entity.param.PageRequest;
import com.oracleclub.server.entity.vo.ArticleDetailVO;
import com.oracleclub.server.entity.vo.ArticleSimpleVO;
import com.oracleclub.server.handler.page.PageDefault;
import com.oracleclub.server.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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
       return articleService.convertToVO(articleService.listLatest(1).get(0));
    }

    @GetMapping("top/{num:\\d+}")
    public List<ArticleSimpleVO> top(@PathVariable Integer num){
        return articleService.convertToSimpleList(articleService.listLatest(num));
    }

    @GetMapping("hot/{num:\\d+}")
    public List<ArticleSimpleVO> hot(@PathVariable Integer num){
        return articleService.convertToSimpleList(articleService.listHot(num));
    }

    // todo page参数不能转换
    @GetMapping
    public IPage<ArticleSimpleVO> pageBy(@PageDefault PageRequest pageable){
        ArticleQueryParam params = new ArticleQueryParam();
        params.setStatus(ArticleStatus.PUBLISHED);

        IPage<Article> articles = articleService.pageBy(params,pageable.convertTo());
        return articleService.convertToSimplePage(articles);
    }

    @GetMapping("{id:\\d+}")
    public ArticleDetailVO getBy(@PathVariable("id") Long id){
        //在这里增加访问次数
        articleService.increaseVisit(id);
        Article article = articleService.getByIdExist(id);

        return articleService.convertToVO(article);
    }
}
