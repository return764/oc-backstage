package com.oracleclub.server.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oracleclub.server.dao.BoardMapper;
import com.oracleclub.server.dao.PostMapper;
import com.oracleclub.server.entity.bbs.Board;
import com.oracleclub.server.entity.bbs.Post;
import com.oracleclub.server.entity.vo.PostVO;
import com.oracleclub.server.entity.vo.SimplePostVO;
import com.oracleclub.server.service.PostService;
import com.oracleclub.server.service.base.AbstractCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @author :RETURN
 * @date :2021/10/10 23:13
 */
@Service
public class PostServiceImpl extends AbstractCrudService<Post,Long> implements PostService {

    private static final String HOME_ROUTER = "home";
    private final PostMapper postMapper;
    private final BoardMapper boardMapper;

    protected PostServiceImpl(PostMapper postMapper, BoardMapper boardMapper) {
        super(postMapper);
        this.postMapper = postMapper;
        this.boardMapper = boardMapper;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public IPage<SimplePostVO> pageBy(String boardRouterName, IPage<Post> pageable) {

        if (HOME_ROUTER.equals(boardRouterName)){
            return postMapper.pageAllBy(pageable);
        }

        Board board = boardMapper.findByRouterName(boardRouterName);

        if (Objects.isNull(board)){
            return pageable.convert(post -> new SimplePostVO().convertFrom(post));
        }
        return postMapper.pageBy(pageable,board.getId());
    }

    @Override
    public PostVO getHolePost(Long id) {
        return postMapper.getById(id);
    }
}
