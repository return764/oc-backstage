package com.oracleclub.server.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oracleclub.server.entity.Article;
import com.oracleclub.server.entity.enums.ArticleStatus;
import com.oracleclub.server.entity.param.ArticleQueryParam;
import com.oracleclub.server.entity.vo.ArticleDetailVO;
import com.oracleclub.server.entity.vo.ArticleSimpleVO;
import com.oracleclub.server.service.base.ConverterService;
import com.oracleclub.server.service.base.CrudService;

import java.util.Collection;
import java.util.List;

/**
 * (Articles)表服务接口
 *
 * @author makejava
 * @since 2021-02-21 17:23:09
 */
public interface ArticleService extends CrudService<Article,Long>, ConverterService<ArticleDetailVO, Article> {

    Article getBy(ArticleStatus status,Long id);

    List<Article> listAllBy(ArticleStatus status);

    List<Article> listLatest(int top);

    IPage<Article> pageLatest(int top);

    IPage<Article> pageBy(IPage<Article> pageable);

    IPage<Article> pageBy(ArticleQueryParam queryParam, IPage<Article> pageable);

    ArticleSimpleVO convertToSimple(Article article);

    IPage<ArticleSimpleVO> convertToSimplePage(IPage<Article> pageable);

    List<ArticleSimpleVO> convertToSimpleList(List<Article> articles);

    ArticleDetailVO createBy(Article article);

    ArticleDetailVO updateBy(Article article);

    Article updateStatus(ArticleStatus status,Long articleId);

    List<Article> updateStatusByIds(ArticleStatus status,List<Long> ids);

    List<Article> removeByIds(Collection<Long> ids);

    ArticleDetailVO createOrUpdateBy(Article article);

    void increaseVisit(int visits,Long articleId);

    void increaseLike(int likes,Long articleId);

    void increaseVisit(Long articleId);

    void increaseLike(Long articleId);

}
