package com.oracleclub.server.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oracleclub.server.dao.base.BaseDao;
import com.oracleclub.server.entity.bbs.Post;
import com.oracleclub.server.entity.dto.PostTagDTO;
import com.oracleclub.server.entity.vo.PostVO;
import com.oracleclub.server.entity.vo.SimplePostVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author :RETURN
 * @date :2021/10/10 22:59
 */
@Repository
public interface PostMapper extends BaseDao<Post,Long> {
    IPage<SimplePostVO> pageBy(IPage<Post> pageable, @Param("b") Long boardId);

    IPage<SimplePostVO> pageAllBy(IPage<Post> pageable);

    PostVO getById(@Param("id") Long id);

    int insertPostTagInBatch(@Param("list") List<PostTagDTO> lstTag);

    IPage<SimplePostVO> pageByIssuer(IPage<Post> pageRequest, @Param("userId") Long userId);
}
