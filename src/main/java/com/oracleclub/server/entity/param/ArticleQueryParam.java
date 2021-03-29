package com.oracleclub.server.entity.param;

import com.oracleclub.server.converter.InputConverter;
import com.oracleclub.server.entity.Article;
import com.oracleclub.server.entity.enums.ArticleStatus;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author :RETURN
 * @date :2021/3/27 17:07
 */
@Data
public class ArticleQueryParam implements InputConverter<Article> {
    private String title;
    private String author;
    private String description;
    private ArticleStatus status;
    private LocalDateTime createdStart;
    private LocalDateTime createdEnd;
}
