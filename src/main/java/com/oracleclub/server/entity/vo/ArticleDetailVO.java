package com.oracleclub.server.entity.vo;

import com.oracleclub.server.converter.OutputConverter;
import com.oracleclub.server.entity.Article;
import com.oracleclub.server.entity.enums.ArticleStatus;
import lombok.Data;

/**
 * @author :RETURN
 * @date :2021/2/22 21:35
 */
@Data
public class ArticleDetailVO implements OutputConverter<ArticleDetailVO, Article> {
    private Long id;
    private String title;
    private String author;
    private String description;
    private String content;
    private ArticleStatus status;
    private Integer viewCount;
    private Integer likeCount;
    private String coverImage;
}
