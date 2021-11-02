package com.oracleclub.server.entity.param;

import lombok.Data;

/**
 * @author :RETURN
 * @date :2021/11/2 13:32
 */
@Data
public class PostParams {
    private String name;
    private String content;
    private Long issuerId;
    private String boardName;
}
