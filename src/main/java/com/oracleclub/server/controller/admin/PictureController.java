package com.oracleclub.server.controller.admin;

import com.oracleclub.server.entity.vo.R;
import com.oracleclub.server.service.PictureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Picture)表控制层
 *
 * @author RETURN
 * @since 2020-08-13 22:00:59
 */
@Slf4j
@RestController
@RequestMapping("admin/pictures")
public class PictureController {

    @Resource
    private PictureService pictureService;

    @GetMapping
    public R listPicture(){
        return R.success("获取图片列表成功");
    }

    @GetMapping("latest")
    public R latestPicture(){
        return R.success("获取顶部图片成功");
    }

    @GetMapping("{id:\\d+}")
    public R getPicture(@PathVariable Long id){
        return R.success("获取图片成功");
    }

    @PostMapping
    public R createPicture(){
        return R.success("创建图片成功");
    }

    @PutMapping("{id:\\d+}")
    public R updatePicture(@PathVariable Long id){
        return R.success("更新图片成功");
    }

    @DeleteMapping("{id:\\d+}")
    public R deletePicture(@PathVariable Long id){
        return R.success("删除图片成功");
    }
}
