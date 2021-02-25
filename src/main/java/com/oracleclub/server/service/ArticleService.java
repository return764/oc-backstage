package com.oracleclub.server.service;

import com.oracleclub.server.entity.Article;
import com.oracleclub.server.entity.vo.ArticleDetailVO;
import com.oracleclub.server.entity.enums.ArticleStatus;
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
public interface ArticleService extends CrudService<Article,Long> {

    Article getBy(ArticleStatus status,Long id);

    List<Article> listAllBy(ArticleStatus status);

    List<Article> listLatest(int top);

    Page<Article> pageLatest(int top);

    Page<Article> pageBy(Pageable pageable);

    Page<Article> pageBy(ArticleStatus status,Pageable pageable);

    ArticleDetailVO createBy(Article article);

    ArticleDetailVO updateBy(Article article);

    Article updateStatus(ArticleStatus status,Long articleId);

    List<Article> updateStatusByIds(ArticleStatus status,List<Long> ids);

    List<Article> removeByIds(Collection<Long> ids);

    ArticleDetailVO convertToDetailVo(Article article);

    List<ArticleDetailVO> convertToListVo(List<Article> articles);

    Page<ArticleDetailVO> convertToDetailVo(Page<Article> articles);

    ArticleDetailVO createOrUpdateBy(Article article);

    void increaseVisit(long visits,Long articleId);

    void increaseLike(long likes,Long articleId);

    void increaseVisit(Long articleId);

    void increaseLike(Long articleId);
}
