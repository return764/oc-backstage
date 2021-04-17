package com.oracleclub.server.service;

import com.oracleclub.server.entity.Picture;
import com.oracleclub.server.entity.param.PictureQueryParam;
import com.oracleclub.server.entity.vo.PictureVO;
import com.oracleclub.server.service.base.ConverterService;
import com.oracleclub.server.service.base.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    Picture upload(MultipartFile file);

    List<String> getTypes();

    Page<PictureVO> pageByParam(Pageable pageable, PictureQueryParam pictureParam);
}