package com.oracleclub.server.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author :RETURN
 * @date :2021/12/31 16:19
 */
@Data
@AllArgsConstructor
public class PostTagDTO {
    private Long postId;
    private Long tagId;
}
