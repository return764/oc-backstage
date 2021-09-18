package com.oracleclub.server.dao;

import com.oracleclub.server.dao.base.BaseDao;
import com.oracleclub.server.entity.Article;
import com.oracleclub.server.entity.enums.ArticleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * (Articles)表数据库访问层
 *
 * @author makejava
 * @since 2021-02-21 17:23:09
 */
public interface ArticleDao extends BaseDao<Article,Long> {
    @Modifying
    @Query("update Article a set a.likeCount = a.likeCount + :likes where a.id = :articleId")
    int updateLikes(@Param("likes") int likes, @Param("articleId") @NonNull Long articleId);

    @Modifying
    @Query("update Article a set a.viewCount = a.viewCount + :visits where a.id = :articleId")
    int updateVisits(@Param("visits") int visits, @Param("articleId") @NonNull Long articleId);

    Optional<Article> findByIdAndStatus(Long id, ArticleStatus status);

    List<Article> findAllByStatus(ArticleStatus status);

    Page<Article> findAllByStatus(ArticleStatus status, Pageable pageable);

    @Modifying
    @Query("update Article a set a.status = :status where a.id = :id")
    int updateStatus(@Param("status") ArticleStatus status,@Param("id") Long id);
}

