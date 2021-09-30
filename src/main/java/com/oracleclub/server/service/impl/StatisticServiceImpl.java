package com.oracleclub.server.service.impl;

import com.oracleclub.server.dao.ArticleMapper;
import com.oracleclub.server.dao.UserMapper;
import com.oracleclub.server.entity.vo.StatisticVO;
import com.oracleclub.server.service.StatisticService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author :RETURN
 * @date :2021/9/23 10:32
 */
@Service
public class StatisticServiceImpl implements StatisticService {
    private final UserMapper userMapper;
    private final ArticleMapper articleMapper;

    public StatisticServiceImpl(UserMapper userMapper, ArticleMapper articleMapper) {
        this.userMapper = userMapper;
        this.articleMapper = articleMapper;
    }

    @Override
    public StatisticVO getStatisticAll() {
        StatisticVO statisticVO = new StatisticVO();
        statisticVO.setNow(LocalDateTime.now());
        statisticVO.setUserCount(userMapper.getUserCount());
        statisticVO.setArticleCount(articleMapper.getArticleCount());
        statisticVO.setViewCount(articleMapper.getViewCount());
        statisticVO.setLikeCount(articleMapper.getLikeCount());

        return statisticVO;
    }
}
