package com.oracleclub.server.dao;

import com.oracleclub.server.entity.Article;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

/**
 * @author :RETURN
 * @date :2021/9/21 23:32
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ArticleMapperTest {

    @Autowired
    ArticleMapper articleMapper;

    @Test
    public void testSelect(){
        List<Article> articles = articleMapper.selectList(null);

        Assertions.assertNotNull(articles);
    }

    @Test
    public void testSelectOne() {
        Article article = articleMapper.selectById(1426171475689345024L);

        Assertions.assertNotNull(article);
    }

    @Test
    public void testUpdate(){
        Article article = articleMapper.selectById(1426171475689345024L);
        article.setAuthor("11111");
        int i = articleMapper.update(article,null);

        Assertions.assertEquals(1,i);
    }
}
