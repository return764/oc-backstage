package com.oracleclub.server.controller;

import cn.hutool.core.util.RandomUtil;
import com.oracleclub.server.annotation.PassToken;
import com.oracleclub.server.entity.vo.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author :RETURN
 * @date :2021/2/25 22:48
 */
@RestController
public class CommonController {

    @GetMapping("ping")
    @PassToken
    public R ping(){
        return R.success("pong");
    }


    private LocalDateTime getRandomDate(){
        int year = RandomUtil.randomInt(2000,2020);
        int month = RandomUtil.randomInt(1,12);
        int dayOfMonth = RandomUtil.randomInt(1,28);
        int hour = RandomUtil.randomInt(0,24);
        int minute = RandomUtil.randomInt(0,59);
        int second = RandomUtil.randomInt(0,59);
        return LocalDateTime.of(year,month,dayOfMonth,hour,minute,second);
    }
}
