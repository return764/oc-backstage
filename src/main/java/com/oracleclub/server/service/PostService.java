package com.oracleclub.server.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oracleclub.server.entity.bbs.Post;
import com.oracleclub.server.entity.param.PostParams;
import com.oracleclub.server.entity.vo.PostVO;
import com.oracleclub.server.entity.vo.SimplePostVO;
import com.oracleclub.server.service.base.CrudService;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author :RETURN
 * @date :2021/10/10 23:13
 */
public interface PostService extends CrudService<Post, Long> {
    IPage<SimplePostVO> pageBy(String boardRouterName, IPage<Post> pageable);

    PostVO getHolePost(Long id);

    String upload(MultipartFile image);

    void createByParams(PostParams postParams);

    IPage<SimplePostVO> pageOwnPost(Long userId, IPage<Post> pageRequest);
}
