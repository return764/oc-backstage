package com.oracleclub.server.entity.bbs;

import com.baomidou.mybatisplus.annotation.TableName;
import com.oracleclub.server.entity.base.BaseEntity;
import lombok.Data;

/**
 * @author :RETURN
 * @date :2021/10/2 16:47
 */
@Data
@TableName("bbs_boards")
public class Board extends BaseEntity {
    private String name;
    private String description;
    private String routerName;
}
