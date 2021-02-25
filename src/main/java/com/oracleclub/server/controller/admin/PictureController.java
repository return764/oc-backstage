package com.oracleclub.server.controller.admin;

import com.oracleclub.server.entity.Picture;
import com.oracleclub.server.entity.param.PictureParam;
import com.oracleclub.server.entity.vo.PictureVo;
import com.oracleclub.server.entity.vo.R;
import com.oracleclub.server.service.PictureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
    public R listPicture(@PageableDefault(sort = "id",direction = Sort.Direction.DESC) Pageable pageable){
        Page<Picture> pictures = pictureService.listAll(pageable);
        return R.success("获取图片列表成功",pictureService.convertToPageVO(pictures));
    }

    @GetMapping("latest")
    public R latestPicture(@RequestParam(value = "top",defaultValue = "10")int top){
        List<Picture> pictures = pictureService.listLatest(top);
        return R.success("获取顶部图片成功",pictureService.convertToListVO(pictures));
    }

    @GetMapping("{id:\\d+}")
    public R getPicture(@PathVariable Long id){
        Picture picture = pictureService.getById(id);
        return R.success("获取图片成功",pictureService.convertToVO(picture));
    }

    @PostMapping
    public R createPicture(@RequestBody PictureParam pictureParam){
        Picture picture = pictureParam.convertTo();
        return R.success("创建图片成功",pictureService.createBy(picture));
    }

    @PutMapping("{id:\\d+}")
    public R updatePicture(@PathVariable Long id,
                           @RequestBody PictureParam pictureParam){
        Picture picture = pictureService.getById(id);

        pictureParam.update(picture);

        PictureVo pictureVo = pictureService.updateBy(picture);
        return R.success("更新图片成功",pictureVo);
    }

    @DeleteMapping("{id:\\d+}")
    public R deletePicture(@PathVariable Long id,
                           @RequestParam(value = "soft",defaultValue = "true")boolean soft){
        Picture picture = new Picture();
        if (soft){
            picture = pictureService.removeSoftById(id);
        }else {
            picture = pictureService.removeById(id);
        }
        return R.success("删除图片成功",pictureService.convertToVO(picture));
    }

    @PostMapping("upload")
    public R uploadPicture(){
        return R.success("上传图片成功");
    }
}
