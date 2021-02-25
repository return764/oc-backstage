package com.oracleclub.server.dao;

import com.oracleclub.server.dao.base.BaseDao;
import com.oracleclub.server.entity.Picture;
import com.oracleclub.server.entity.enums.PictureStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * (Picture)表数据库访问层
 *
 * @author RETURN
 * @since 2020-08-13 22:00:59
 */
public interface PictureDao extends BaseDao<Picture,Long> {
    List<Picture> findAllByStatus(PictureStatus status);

    Page<Picture> findAllByStatus(PictureStatus status, Pageable pageable);
}