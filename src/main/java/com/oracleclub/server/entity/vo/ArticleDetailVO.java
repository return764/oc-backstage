package com.oracleclub.server.entity.vo;

import com.oracleclub.server.converter.OutputConverter;
import com.oracleclub.server.entity.Article;
import lombok.Data;

/**
 * @author :RETURN
 * @date :2021/2/22 21:35
 */
@Data
public class ArticleDetailVO extends Article implements OutputConverter<ArticleDetailVO, Article> {

}
