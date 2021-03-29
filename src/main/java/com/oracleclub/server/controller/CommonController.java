package com.oracleclub.server.controller;

import cn.hutool.core.util.RandomUtil;
import com.oracleclub.server.dao.ArticleDao;
import com.oracleclub.server.dao.UserDao;
import com.oracleclub.server.entity.Article;
import com.oracleclub.server.entity.enums.ArticleStatus;
import com.oracleclub.server.entity.vo.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author :RETURN
 * @date :2021/2/25 22:48
 */
@RestController
public class CommonController {

    @Resource
    UserDao userDao;
    @Resource
    ArticleDao articleDao;

    @GetMapping("ping")
    public R ping(){
        //插入测试用用户
//        User u = new User();
//        u.setAvatar("http://placehold.it/350x200");
//        u.setBirthday(getRandomDate());
//        u.setEmail(RandomUtil.randomString(12));
//        u.setInfo("这里是用户介绍");
//        u.setIpAddr(1344643131L);
//        u.setName("小明");
//        u.setNickname("可可爱爱的小明");
//        u.setRole(RoleEnum.NORMAL);
//        u.setStatus(UserStatus.ACTIVE);
//        u.setPassword("123456");
//        u.setPhNum("12354548315");
//        u.setStuNum("41804404");
//        u.setLoginAt(getRandomDate());
//        Department department = new Department();
//        department.setId(1L);
//        u.setDepartment(department);
//        return R.success("pong",userDao.save(u));

        //插入测试用文章
        Article article = new Article();
        article.setStatus(ArticleStatus.PUBLISHED);
        article.setAuthor("作者"+RandomUtil.randomNumber());
        article.setContent(RandomUtil.randomString(1200));
        article.setCoverImage("http://placehold.it/350x200");
        article.setDescription(RandomUtil.randomString(120));
        article.setTitle("Java基础教程"+RandomUtil.randomNumber());
        article.setLikeCount(1);
        article.setViewCount(1);
        return R.success("pong",articleDao.save(article));
    }


    private LocalDateTime getRandomDate(){
        int year = RandomUtil.randomInt(2000,2020);
        int month = RandomUtil.randomInt(1,12);
        int dayOfMonth = RandomUtil.randomInt(1,28);
        int hour = RandomUtil.randomInt(0,24);
        int minute = RandomUtil.randomInt(0,59);
        int second = RandomUtil.randomInt(0,59);
        return LocalDateTime.of(year,month,dayOfMonth,hour,minute,second);
    }
}
