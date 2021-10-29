package com.oracleclub.server.service.impl;

import com.oracleclub.server.dao.BoardMapper;
import com.oracleclub.server.entity.bbs.Board;
import com.oracleclub.server.entity.support.Route;
import com.oracleclub.server.service.BoardService;
import com.oracleclub.server.service.base.AbstractCrudService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author :RETURN
 * @date :2021/10/10 23:11
 */
@Service
public class BoardServiceImpl extends AbstractCrudService<Board,Long> implements BoardService {

    private final BoardMapper boardMapper;

    public BoardServiceImpl(BoardMapper boardMapper) {
        super(boardMapper);
        this.boardMapper = boardMapper;
    }

    @Override
    public List<Route> generateRoute() {
        List<Board> boards = boardMapper.selectAllExist();
        return boards.stream().map(board -> {
            Route r = new Route();
            r.setPath(board.getRouterName());
            r.setComponent("/bbs/BBSList");
            r.setName(board.getName());
            r.setMeta(Route.RouteMeta.builder()
                    .name(board.getName())
                    .invisible(true)
                    .build());
            return r;
        }).collect(Collectors.toList());
    }
}
