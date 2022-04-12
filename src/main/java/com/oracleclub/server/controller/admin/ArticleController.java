package com.oracleclub.server.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oracleclub.server.annotation.OperationLogMarker;
import com.oracleclub.server.entity.Article;
import com.oracleclub.server.entity.enums.ArticleStatus;
import com.oracleclub.server.entity.enums.OperationType;
import com.oracleclub.server.entity.param.ArticleParam;
import com.oracleclub.server.entity.param.ArticleQueryParam;
import com.oracleclub.server.entity.param.PageRequest;
import com.oracleclub.server.entity.vo.ArticleDetailVO;
import com.oracleclub.server.entity.vo.R;
import com.oracleclub.server.handler.page.PageDefault;
import com.oracleclub.server.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Articles)表控制层
 *
 * @author makejava
 * @since 2021-02-21 17:23:09
 */
@Slf4j
@RestController("admin_article_controller")
@RequestMapping("api/admin/articles")
public class ArticleController {


    @Resource
    private ArticleService articleService;


    @GetMapping("{id:\\d+}")
    public R getBy(@PathVariable("id") Long id) {
        Article article = articleService.getByIdExist(id);
        return R.success("成功获取文章",articleService.convertToVO(article));
    }

    // todo page参数不能转换
    @GetMapping
    public R pageBy(@PageDefault PageRequest pageable,
                    ArticleQueryParam queryParam){
        log.debug("ArticleQueryParam:{}",queryParam);
        IPage<Article> articles = articleService.pageBy(queryParam, pageable.convertTo());
        return R.success("成功获取文章列表",articleService.convertToSimplePage(articles));
    }

    @GetMapping("latest")
    public R listLatest(@RequestParam(name = "top",defaultValue = "10")int top){
        return R.success("成功获取顶部文章",articleService.convertToListVO(articleService.listLatest(top)));
    }

    @OperationLogMarker(operaType = OperationType.NEW, operaContent = "创建新文章")
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
            article = articleService.removeLogicById(id);
        }else {
            article = articleService.removeById(id);
        }
        return R.success("删除文章成功",articleService.convertToVO(article));
    }


    @DeleteMapping
    public R deleteUsersInBatch(@RequestBody List<Long> ids,
                                @RequestParam(name = "soft",required = false,defaultValue = "true")boolean soft){
        if (soft){
            ids.forEach(articleService::removeLogicById);
        }else {
            articleService.removeInBatch(ids);
        }
        return R.success("成功删除文章列表");
    }

    @OperationLogMarker(operaType = OperationType.UPDATE, operaContent = "更新文章")
    @PutMapping("{id:\\d+}")
    public R updateArticle(@PathVariable("id")Long id,
                           @RequestBody ArticleParam articleParam){
        Assert.notNull(articleParam,"Article参数不能为空");
        Article article = articleService.getByIdExist(id);

        articleParam.update(article);

        ArticleDetailVO articleDetailVO = articleService.updateBy(article);
        return R.success("更新文章成功",articleDetailVO);
    }


    @PutMapping
    public R updateArticleStatus(@RequestBody List<Long> ids,
                                 @RequestParam ArticleStatus status){
        log.debug("ids:{},articleStatus:{}",ids,status);
        List<Article> articles = articleService.updateStatusByIds(status, ids);
        return R.success("批量更新文章状态成功",articleService.convertToSimpleList(articles));
    }
}
