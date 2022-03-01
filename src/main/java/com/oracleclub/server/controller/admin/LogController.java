package com.oracleclub.server.controller.admin;

import com.oracleclub.server.entity.param.PageRequest;
import com.oracleclub.server.entity.vo.R;
import com.oracleclub.server.handler.page.PageDefault;
import com.oracleclub.server.service.OperationLogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author :RETURN
 * @date :2021/9/23 12:46
 */
@RestController
@RequestMapping("/api/admin/logs")
public class LogController {

    @Resource
    OperationLogService logService;

    @GetMapping("latest")
    public R listLatest(@RequestParam(name = "top",defaultValue = "10")int top){

        return R.success("成功获取最新日志",logService.convertToListVO(logService.listLatest(top)));
    }

    @GetMapping
    public R pageBy(@PageDefault PageRequest pageable){
        return R.success("成功获取日志列表",logService.convertToPageVO(logService.pageBy(pageable.convertTo())));
    }
}
