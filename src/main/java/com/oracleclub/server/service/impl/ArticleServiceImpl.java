package com.oracleclub.server.service.impl;

import com.oracleclub.server.dao.ArticleDao;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author :RETURN
 * @date :2021/2/21 18:28
 */
@Slf4j
@Service
public class ArticleServiceImpl extends AbstractCrudService<Article,Long> implements ArticleService {

    private final ArticleDao articleDao;

    protected ArticleServiceImpl(ArticleDao articleDao) {
        super(articleDao);
        this.articleDao = articleDao;
    }

    @Override
    public Article getBy(ArticleStatus status, Long id) {
        Assert.notNull(status, "Post status must not be null");
        Assert.notNull(id, "Post id must not be null");

        Optional<Article> articleOptional = articleDao.findByIdAndStatus(id,status);

        return articleOptional.orElseThrow(()-> new NotFoundException("查询文章信息失败").setErrorData(id));
    }

    @Override
    public List<Article> listAllBy(ArticleStatus status) {
        Assert.notNull(status,"Article status must not be null");

        return articleDao.findAllByStatus(status);
    }

    @Override
    public List<Article> listLatest(int top) {
        Assert.isTrue(top > 0,"Top number must not be less than 0");

        PageRequest pageRequest = PageRequest.of(0,top, Sort.by(Sort.Direction.DESC,"id"));

        return articleDao.findAllByStatus(ArticleStatus.PUBLISHED,pageRequest).getContent();
    }

    @Override
    public Page<Article> pageLatest(int top) {
        Assert.isTrue(top > 0,"Top number must not be less than 0");

        PageRequest pageRequest = PageRequest.of(0,top, Sort.by(Sort.Direction.DESC,"id"));

        return listAll(pageRequest);
    }

    @Override
    public Page<Article> pageBy(Pageable pageable) {
        Assert.notNull(pageable, "Page info must not be null");

        return listAll(pageable);
    }

    @Override
    public Page<Article> pageBy(ArticleQueryParam queryParam, Pageable pageable) {
        Assert.notNull(pageable, "Page info must not be null");

        return articleDao.findAllWithExist(buildQuery(queryParam),pageable);
    }

    @Override
    public ArticleSimpleVO convertToSimple(Article article) {
        Assert.notNull(article,"Article must not be null");

        return new ArticleSimpleVO().convertFrom(article);
    }

    @Override
    public Page<ArticleSimpleVO> convertToSimplePage(Page<Article> articles) {
        return articles.map(this::convertToSimple);
    }

    @Override
    public List<ArticleSimpleVO> convertToSimpleList(List<Article> articles) {
        return articles.stream().map(this::convertToSimple).collect(Collectors.toList());
    }

    private Specification<Article> buildQuery(ArticleQueryParam queryParam) {
        Assert.notNull(queryParam, "文章查询参数不能为空");

        return (Specification<Article>)(root, cq, cb) -> {
            List<Predicate> predicates = new LinkedList<>();
            if (queryParam.getAuthor() != null){
                predicates.add(cb.like(root.get("author").as(String.class),"%"+queryParam.getAuthor()+"%"));
            }

            if (queryParam.getTitle() != null){
                predicates.add(cb.like(root.get("title").as(String.class),"%"+queryParam.getTitle()+"%"));
            }

            if (queryParam.getDescription() != null){
                predicates.add(cb.like(root.get("description").as(String.class),"%"+queryParam.getDescription()+"%"));
            }

            if (queryParam.getStatus() != null){
                predicates.add(cb.equal(root.get("status").as(ArticleStatus.class),queryParam.getStatus()));
            }

            if (queryParam.getCreatedStart() != null){
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt").as(LocalDateTime.class),queryParam.getCreatedStart()));
            }

            if (queryParam.getCreatedEnd() != null){
                predicates.add(cb.lessThanOrEqualTo(root.get("createdAt").as(LocalDateTime.class),queryParam.getCreatedEnd()));
            }

            return cq.where(predicates.toArray(new Predicate[0])).getRestriction();
        };
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
            int row = articleDao.updateStatus(status, articleId);
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
    public Page<ArticleDetailVO> convertToPageVO(Page<Article> articles){
        return articles.map(this::convertToVO);
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
    public void increaseVisit(long visits, Long articleId) {
        Assert.isTrue(visits > 0,"visits to increase must be less than 1");
        Assert.notNull(articleId,"article id is must not be null");

        long affectedRows = articleDao.updateVisits(visits,articleId);

        if (affectedRows != 1){
            log.error("Article with id: [{}] may not be found",articleId);
            throw new RuntimeException("Failed to increase visits "+visits+" for article with id "+articleId);
        }
    }

    @Override
    public void increaseLike(long likes, Long articleId) {
        Assert.isTrue(likes > 0,"visits to increase must be less than 1");
        Assert.notNull(articleId,"article id is must not be null");

        long affectedRows = articleDao.updateLikes(likes,articleId);

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
