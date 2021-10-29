package com.oracleclub.server.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oracleclub.server.dao.base.BaseDao;
import com.oracleclub.server.entity.Picture;
import com.oracleclub.server.entity.enums.PictureStatus;
import com.oracleclub.server.entity.param.PictureQueryParam;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (Picture)表数据库访问层
 *
 * @author RETURN
 * @since 2020-08-13 22:00:59
 */
@Repository
public interface PictureMapper extends BaseDao<Picture,Long> {
    List<Picture> findAllByStatus(@Param("status") PictureStatus status);

    IPage<Picture> findAllByStatus(@Param("status") PictureStatus status, IPage<Picture> pageable);

    @Select("select distinct p.type from pictures p")
    List<String> findAllTypes();

    IPage<Picture> findAllExistWithParams(IPage<Picture> pageable, @Param("p") PictureQueryParam pictureParam);
}
