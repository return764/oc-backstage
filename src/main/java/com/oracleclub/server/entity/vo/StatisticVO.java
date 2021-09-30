package com.oracleclub.server.entity.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author :RETURN
 * @date :2021/9/23 10:33
 */
@Data
public class StatisticVO {
    private Integer viewCount;
    private Integer likeCount;
    private Integer userCount;
    private Integer articleCount;
    private LocalDateTime now;
}
