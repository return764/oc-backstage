package com.oracleclub.server.controller;

import com.oracleclub.server.entity.support.AppConstant;
import com.oracleclub.server.entity.vo.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author :RETURN
 * @date :2021/2/25 22:48
 */
@RestController
public class CommonController {

    @GetMapping("ping")
    public R ping(){

        Path path = Paths.get(AppConstant.USER_HOME,".oc","upload/file/","sdxcsd.png");

        System.out.println(path);
        System.out.println(path.getFileName());
        System.out.println(path.getRoot());
        System.out.println(path.getParent());
        System.out.println(path.getNameCount());
        System.out.println(path.normalize().toString());

        return R.success("pong");
    }
}
