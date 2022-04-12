package com.oracleclub.server.controller.content;

import com.oracleclub.server.annotation.PassToken;
import com.oracleclub.server.entity.bbs.Board;
import com.oracleclub.server.entity.support.Route;
import com.oracleclub.server.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author :RETURN
 * @date :2021/10/10 23:09
 */
@PassToken
@RestController("content_board_controller")
@RequestMapping("api/content/boards")
public class BoardController {

    @Autowired
    BoardService boardService;

    @GetMapping
    public List<Board> listBoards(){
        return boardService.listAll();
    }

    @GetMapping("route")
    public Route getBoardRouter(){
        Route route = new Route();
        route.setPath("b");
        List<Route> children = boardService.generateRoute();
        route.setChildren(children);

        return route.wrap("/bbs").wrap("/");
    }
}
