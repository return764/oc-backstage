package com.oracleclub.server.entity;

import com.oracleclub.server.entity.base.BaseEntity;
import com.oracleclub.server.entity.enums.ArticleStatus;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * (Articles)实体类
 *
 * @author makejava
 * @since 2021-02-21 17:23:09
 */
@Data
@Entity
@Table(name ="articles")
public class Article extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -48862080062168345L;

    /**
     * 标题
     */
    @Column
    private String title;
    /**
     * 作者
     */
    @Column
    private String author;
    /**
     * 概述
     */
    @Column
    private String description;
    /**
     * 内容
     */
    @Column
    private String content;
    /**
     * 状态
     */
    @Column
    @ColumnDefault("0")
    private ArticleStatus status;
    /**
     * 浏览数
     */
    @Column(name = "view_count")
    private Integer viewCount;
    /**
     * 点赞数
     */
    @Column(name = "like_count")
    private Integer likeCount;
    /**
     * 封面图片
     */
    @Column(name = "cover_image")
    private String coverImage;



}
