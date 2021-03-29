package com.oracleclub.server.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author :RETURN
 * @date :2021/2/22 21:35
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleDetailVO extends ArticleSimpleVO {
    private String content;
}
