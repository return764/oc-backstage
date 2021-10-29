package com.oracleclub.server.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oracleclub.server.dao.base.BaseDao;
import com.oracleclub.server.entity.Article;
import com.oracleclub.server.entity.enums.ArticleStatus;
import com.oracleclub.server.entity.param.ArticleQueryParam;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * (Articles)表数据库访问层
 *
 * @author makejava
 * @since 2021-02-21 17:23:09
 */
@Repository
public interface ArticleMapper extends BaseDao<Article,Long> {

    @Update("update articles a set a.like_count = a.like_count + #{likes} where a.id = #{articleId}")
    int updateLikes(@Param("likes") Integer likes,@Param("articleId") @NonNull Long articleId);

    @Update("update articles a set a.view_count = a.view_count + #{visits} where a.id = #{articleId}")
    int updateVisits(@Param("visits") Integer visits, @Param("articleId") @NonNull Long articleId);

    @Select("select * from articles where id = #{id} and status = #{status}")
    Optional<Article> findByIdAndStatus(@Param("id") Long id, @Param("status") ArticleStatus status);

    @Select("select * from articles where id = #{id} and status = #{status}")
    List<Article> findAllByStatus(@Param("status") ArticleStatus status);

    IPage<Article> findAllExistWithParams(IPage<Article> pageable, @Param("a") ArticleQueryParam queryParam);

    @Update("update articles a set a.status = #{status} where a.id = #{id}")
    int updateStatus(@Param("status") ArticleStatus status,@Param("id") Long id);

    int getArticleCount();

    int getViewCount();

    int getLikeCount();
}

