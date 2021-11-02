package com.oracleclub.server.dao;

import com.oracleclub.server.dao.base.BaseDao;
import com.oracleclub.server.entity.bbs.Board;
import org.springframework.stereotype.Repository;

/**
 * @author :RETURN
 * @date :2021/10/10 22:48
 */
@Repository
public interface BoardMapper extends BaseDao<Board,Long> {

    Board findByRouterName(String boardRouterName);

    Board findByName(String boardName);
}
