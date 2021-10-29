package com.oracleclub.server.entity.bbs;

import com.baomidou.mybatisplus.annotation.TableName;
import com.oracleclub.server.entity.base.BaseEntity;
import lombok.Data;

/**
 * @author :RETURN
 * @date :2021/10/7 22:15
 */
@Data
@TableName("bbs_tags")
public class Tag extends BaseEntity {
    private String name;
    private String color;
}
