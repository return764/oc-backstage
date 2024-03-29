package com.oracleclub.server.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oracleclub.server.entity.Picture;
import com.oracleclub.server.entity.param.PictureQueryParam;
import com.oracleclub.server.entity.vo.PictureVO;
import com.oracleclub.server.service.base.ConverterService;
import com.oracleclub.server.service.base.CrudService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * (Picture)表服务接口
 *
 * @author RETURN
 * @since 2020-08-13 22:00:59
 */
public interface PictureService extends CrudService<Picture,Long>, ConverterService<PictureVO,Picture> {

    List<Picture> listLatest(int top);

    PictureVO createBy(Picture picture);

    PictureVO updateBy(Picture picture);

    Picture upload(MultipartFile file,String type);

    List<String> getTypes();

    IPage<PictureVO> pageBy(IPage<Picture> pageable, PictureQueryParam pictureParam);

    List<Picture> removePictures(List<Long> ids);

    Picture removePicture(Long id);
}
