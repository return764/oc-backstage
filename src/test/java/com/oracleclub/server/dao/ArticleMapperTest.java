package com.oracleclub.server.dao;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.oracleclub.server.entity.Article;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author :RETURN
 * @date :2021/9/21 23:32
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@Transactional
@Rollback
@SpringBootTest
public class ArticleMapperTest {

    @Resource
    ArticleMapper articleMapper;

    @Test
    public void testSelect(){
        List<Article> articles = articleMapper.selectList(null);

        Assertions.assertNotNull(articles);
    }

    @Test
    public void testSelectOne() {
        Article article = articleMapper.selectById(1468779978467446784L);

        Assertions.assertNotNull(article);
    }

    @Test
    public void testUpdate(){
        Article article = articleMapper.selectById(1468779978467446784L);
        article.setAuthor("11111");
        UpdateWrapper<Article> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", 1468779978467446784L);
        int i = articleMapper.update(article,updateWrapper);

        Assertions.assertEquals(1,i);
    }
}
