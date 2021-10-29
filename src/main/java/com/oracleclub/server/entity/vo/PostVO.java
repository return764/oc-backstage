package com.oracleclub.server.entity.vo;

import com.oracleclub.server.converter.OutputConverter;
import com.oracleclub.server.entity.bbs.Post;
import lombok.Data;

/**
 * @author :RETURN
 * @date :2021/10/29 10:58
 */
@Data
public class PostVO implements OutputConverter<PostVO, Post> {
}
