package com.oracleclub.server.controller.admin;

import com.oracleclub.server.entity.vo.R;
import com.oracleclub.server.entity.vo.StatisticVO;
import com.oracleclub.server.service.StatisticService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author :RETURN
 * @date :2021/9/23 10:29
 */
@RestController
@RequestMapping("/api/admin/statistic")
public class StatisticController {

    @Resource
    StatisticService statisticService;

    @GetMapping
    public R statistic(){
        StatisticVO statisticVO = statisticService.getStatisticAll();
        return R.success("获取统计成功",statisticVO);
    }
}
