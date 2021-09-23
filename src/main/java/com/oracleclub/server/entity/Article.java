package com.oracleclub.server.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.oracleclub.server.entity.base.BaseEntity;
import com.oracleclub.server.entity.enums.ArticleStatus;
import lombok.Data;

import java.io.Serializable;

/**
 * (Articles)实体类
 *
 * @author makejava
 * @since 2021-02-21 17:23:09
 */
@Data
@TableName("articles")
public class Article extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -48862080062168345L;
    /**
     * 标题
     */
    private String title;
    /**
     * 作者
     */
    private String author;
    /**
     * 概述
     */
    private String description;
    /**
     * 内容
     */
    private String content;
    /**
     * 状态
     */
    private ArticleStatus status;
    /**
     * 浏览数
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer viewCount;
    /**
     * 点赞数
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer likeCount;
    /**
     * 封面图片
     */
    private String coverImage;

}
