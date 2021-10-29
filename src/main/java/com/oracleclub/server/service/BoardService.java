package com.oracleclub.server.service;

import com.oracleclub.server.entity.bbs.Board;
import com.oracleclub.server.entity.support.Route;
import com.oracleclub.server.service.base.CrudService;

import java.util.List;

/**
 * @author :RETURN
 * @date :2021/10/10 23:11
 */
public interface BoardService extends CrudService<Board, Long> {
    List<Route> generateRoute();
}
