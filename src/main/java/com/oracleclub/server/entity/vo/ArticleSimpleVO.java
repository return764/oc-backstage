package com.oracleclub.server.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.oracleclub.server.converter.OutputConverter;
import com.oracleclub.server.entity.Article;
import com.oracleclub.server.entity.enums.ArticleStatus;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author :RETURN
 * @date :2021/3/27 21:36
 */
@Data
public class ArticleSimpleVO implements OutputConverter<ArticleSimpleVO, Article> {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String title;
    private String author;
    private String description;
    private ArticleStatus status;
    private Integer viewCount;
    private Integer likeCount;
    private String coverImage;
    private LocalDateTime createdAt;
}
