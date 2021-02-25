package com.oracleclub.server.controller.admin;

import com.oracleclub.server.entity.Article;
import com.oracleclub.server.entity.enums.ArticleStatus;
import com.oracleclub.server.entity.param.ArticleParam;
import com.oracleclub.server.entity.vo.ArticleDetailVO;
import com.oracleclub.server.entity.vo.R;
import com.oracleclub.server.service.ArticleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Articles)表控制层
 *
 * @author makejava
 * @since 2021-02-21 17:23:09
 */
@RestController
@RequestMapping("api/admin/articles")
public class ArticleController {


    @Resource
    private ArticleService articleService;


    @GetMapping("{id:\\d+}")
    public R getBy(@PathVariable("id") Long id,
                   @RequestParam(value = "status",required = false,defaultValue = "1") ArticleStatus status) {
        Article article = articleService.getBy(status,id);
        return R.success("成功获取文章",articleService.convertToVO(article));
    }

    @GetMapping
    public R pageBy(@PageableDefault(sort = "id",direction = Sort.Direction.DESC) Pageable pageable,
                    @RequestParam(value = "status",required = false,defaultValue = "published") ArticleStatus status){
        Page<Article> articles = articleService.pageBy(status, pageable);
        return R.success("成功获取文章列表",articleService.convertToPageVO(articles));
    }

    @GetMapping("latest")
    public R listLatest(@RequestParam(name = "top",defaultValue = "10")int top){
        return R.success("成功获取顶部文章",articleService.convertToListVO(articleService.listLatest(top)));
    }

    @PostMapping
    public R createArticle(@RequestBody ArticleParam articleParam){
        Article article = articleParam.convertTo();
        return R.success("创建文章成功",articleService.createBy(article));
    }

    @DeleteMapping("{id:\\d+}")
    public R deleteArticle(@PathVariable("id")Long id,
                           @RequestParam(name = "soft",required = false,defaultValue = "true") boolean soft){
        Article article = new Article();
        if (soft){
            article = articleService.removeSoftById(id);
        }else {
            article = articleService.removeById(id);
        }
        return R.success("删除文章成功",articleService.convertToVO(article));
    }

    @PutMapping("{id:\\d+}")
    public R UpdateArticle(@PathVariable("id")Long id,
                           @RequestBody ArticleParam articleParam){
        Article article = articleService.getById(id);

        articleParam.update(article);

        ArticleDetailVO articleDetailVO = articleService.updateBy(article);
        return R.success("更新文章成功",articleDetailVO);
    }
}
