package com.oracleclub.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oracleclub.server.dao.ArticleMapper;
import com.oracleclub.server.entity.Article;
import com.oracleclub.server.entity.enums.ArticleStatus;
import com.oracleclub.server.entity.param.ArticleQueryParam;
import com.oracleclub.server.entity.vo.ArticleDetailVO;
import com.oracleclub.server.entity.vo.ArticleSimpleVO;
import com.oracleclub.server.exception.NotFoundException;
import com.oracleclub.server.exception.ServiceException;
import com.oracleclub.server.service.ArticleService;
import com.oracleclub.server.service.base.AbstractCrudService;
import com.oracleclub.server.utils.ServiceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author :RETURN
 * @date :2021/2/21 18:28
 */
@Slf4j
@Service
public class ArticleServiceImpl extends AbstractCrudService<Article,Long> implements ArticleService {

    private final ArticleMapper articleMapper;

    protected ArticleServiceImpl(ArticleMapper articleMapper) {
        super(articleMapper);
        this.articleMapper = articleMapper;
    }

    @Override
    public Article getBy(ArticleStatus status, Long id) {
        Assert.notNull(status, "Post status must not be null");
        Assert.notNull(id, "Post id must not be null");

        Optional<Article> articleOptional = articleMapper.findByIdAndStatus(id,status);

        return articleOptional.orElseThrow(()-> new NotFoundException("查询文章信息失败").setErrorData(id));
    }

    @Override
    public List<Article> listAllBy(ArticleStatus status) {
        Assert.notNull(status,"Article status must not be null");

        return articleMapper.findAllByStatus(status);
    }

    @Override
    public List<Article> listLatest(int top) {
        Assert.isTrue(top > 0,"Top number must not be less than 0");

        Page<Article> pageRequest = new Page<>(0,top);
        return articleMapper.selectPage(pageRequest,getArticlePublishAndDESCWrapper()).getRecords();
    }

    private QueryWrapper<Article> getArticlePublishAndDESCWrapper() {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",ArticleStatus.PUBLISHED)
                .orderByDesc("created_at");
        return queryWrapper;
    }

    @Override
    public Page<Article> pageLatest(int top) {
        Assert.isTrue(top > 0,"Top number must not be less than 0");

        Page<Article> pageRequest = new Page<>(0,top);

        return articleMapper.selectPage(pageRequest,getArticlePublishAndDESCWrapper());
    }

    @Override
    public IPage<Article> pageBy(IPage<Article> pageable) {
        Assert.notNull(pageable, "Page info must not be null");

        return listAll(pageable);
    }

    @Override
    public IPage<Article> pageBy(ArticleQueryParam queryParam, IPage<Article> pageable) {
        Assert.notNull(pageable, "Page info must not be null");

        return articleMapper.findAllExistWithParams(pageable, queryParam);
    }

    @Override
    public ArticleSimpleVO convertToSimple(Article article) {
        Assert.notNull(article,"Article must not be null");

        return new ArticleSimpleVO().convertFrom(article);
    }

    @Override
    public IPage<ArticleSimpleVO> convertToSimplePage(IPage<Article> articles) {

        return articles.convert(this::convertToSimple);
    }

    @Override
    public List<ArticleSimpleVO> convertToSimpleList(List<Article> articles) {
        return articles.stream().map(this::convertToSimple).collect(Collectors.toList());
    }

    @Override
    public ArticleDetailVO createBy(Article article) {
        return createOrUpdateBy(article);
    }

    @Override
    public ArticleDetailVO updateBy(Article article) {
        article.setUpdatedAt(LocalDateTime.now());
        return createOrUpdateBy(article);
    }

    @Override
    public Article updateStatus(ArticleStatus status, Long articleId) {
        Assert.notNull(status,"Article status must not be null");
        Assert.isTrue(!ServiceUtils.isEmptyId(articleId),"Article id must not be empty");

        Article article = getById(articleId);

        if (!status.equals(article.getStatus())){
            int row = articleMapper.updateStatus(status, articleId);
            if (row != 1){
                throw new ServiceException("Failed to update article status of article with id "+articleId);
            }
           article.setStatus(status);
        }
        return article;
    }

    @Override
    public List<Article> updateStatusByIds(ArticleStatus status, List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.emptyList();
        }
        return ids.stream().map(id ->
                updateStatus(status,id)
        ).collect(Collectors.toList());
    }

    @Override
    public List<Article> removeByIds(Collection<Long> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.emptyList();
        }
        return ids.stream().map(this::removeById).collect(Collectors.toList());
    }

    @Override
    public ArticleDetailVO convertToVO(Article article) {
        return convertTo(article);
    }

    @Override
    public List<ArticleDetailVO> convertToListVO(List<Article> articles) {
        return articles.stream().map(this::convertTo).collect(Collectors.toList());
    }

    @Override
    public IPage<ArticleDetailVO> convertToPageVO(IPage<Article> articles){
        return articles.convert(this::convertToVO);
    }

    private ArticleDetailVO convertTo(Article article){
        Assert.notNull(article,"Article must not be null");

        return new ArticleDetailVO().convertFrom(article);
    }

    @Override
    public ArticleDetailVO createOrUpdateBy(Article article) {
        return convertToVO(createOrUpdate(article));
    }

    @Override
    public void increaseVisit(int visits, Long articleId) {
        Assert.isTrue(visits > 0,"visits to increase must be less than 1");
        Assert.notNull(articleId,"article id is must not be null");

        long affectedRows = articleMapper.updateVisits(visits,articleId);

        if (affectedRows != 1){
            log.error("Article with id: [{}] may not be found",articleId);
            throw new RuntimeException("Failed to increase visits "+visits+" for article with id "+articleId);
        }
    }

    @Override
    public void increaseLike(int likes, Long articleId) {
        Assert.isTrue(likes > 0,"visits to increase must be less than 1");
        Assert.notNull(articleId,"article id is must not be null");

        long affectedRows = articleMapper.updateLikes(likes,articleId);

        if (affectedRows != 1){
            log.error("Article with id: [{}] may not be found",articleId);
            throw new RuntimeException("Failed to increase likes "+likes+" for article with id "+articleId);
        }
    }

    @Override
    public void increaseVisit(Long articleId) {
        increaseVisit(1,articleId);
    }

    @Override
    public void increaseLike(Long articleId) {
        increaseLike(1,articleId);
    }

}
