package com.oracleclub.server.dao;

import com.oracleclub.server.dao.base.BaseDao;
import com.oracleclub.server.entity.bbs.Tag;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author :RETURN
 * @date :2021/12/31 11:27
 */
@Repository
public interface TagMapper extends BaseDao<Tag, Long> {
    List<Tag> selectSimpleByPostId(Long id);
}
