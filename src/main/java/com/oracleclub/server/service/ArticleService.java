package com.oracleclub.server.service;

import com.oracleclub.server.entity.Article;
import com.oracleclub.server.entity.enums.ArticleStatus;
import com.oracleclub.server.entity.param.ArticleQueryParam;
import com.oracleclub.server.entity.vo.ArticleDetailVO;
import com.oracleclub.server.entity.vo.ArticleSimpleVO;
import com.oracleclub.server.service.base.ConverterService;
import com.oracleclub.server.service.base.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    Page<Article> pageLatest(int top);

    Page<Article> pageBy(Pageable pageable);

    Page<Article> pageBy(ArticleQueryParam queryParam, Pageable pageable);

    ArticleSimpleVO convertToSimple(Article article);

    Page<ArticleSimpleVO> convertToSimplePage(Page<Article> articles);

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
