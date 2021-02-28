package com.oracleclub.server.service;

import com.oracleclub.server.entity.Picture;
import com.oracleclub.server.entity.vo.PictureVo;
import com.oracleclub.server.service.base.ConverterService;
import com.oracleclub.server.service.base.CrudService;

import java.util.List;

/**
 * (Picture)表服务接口
 *
 * @author RETURN
 * @since 2020-08-13 22:00:59
 */
public interface PictureService extends CrudService<Picture,Long>, ConverterService<PictureVo,Picture> {

    List<Picture> listLatest(int top);

    PictureVo createBy(Picture picture);

    PictureVo updateBy(Picture picture);

}