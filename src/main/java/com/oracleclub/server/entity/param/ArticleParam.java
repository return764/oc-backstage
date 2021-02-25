package com.oracleclub.server.entity.param;

import com.oracleclub.server.converter.InputConverter;
import com.oracleclub.server.entity.Article;
import com.oracleclub.server.entity.enums.ArticleStatus;
import lombok.Data;

/**
 * @author :RETURN
 * @date :2021/2/22 16:33
 */
@Data
public class ArticleParam implements InputConverter<Article> {
    private String title;
    private String description;
    private String content;
    private ArticleStatus status;
    private String coverImage;
}
