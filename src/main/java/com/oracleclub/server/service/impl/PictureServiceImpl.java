package com.oracleclub.server.service.impl;

import com.oracleclub.server.dao.PictureDao;
import com.oracleclub.server.entity.Picture;
import com.oracleclub.server.service.PictureService;
import com.oracleclub.server.service.base.AbstractCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * (Picture)表服务实现类
 *
 * @author RETURN
 * @since 2020-08-13 22:00:59
 */
@Service
@Slf4j
public class PictureServiceImpl extends AbstractCrudService<Picture,Long> implements PictureService {

    private final PictureDao pictureDao;

    protected PictureServiceImpl(PictureDao pictureDao) {
        super(pictureDao);
        this.pictureDao = pictureDao;
    }

}