package com.oracleclub.server.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oracleclub.server.dao.base.BaseDao;
import com.oracleclub.server.entity.Attachment;
import com.oracleclub.server.entity.param.AttachmentParam;
import org.apache.ibatis.annotations.Param;

/**
 * @author :RETURN
 * @date :2021/2/28 0:36
 */
public interface AttachmentMapper extends BaseDao<Attachment,Long> {
    IPage<Attachment> findAllWithParams(IPage<Attachment> pageable,@Param("a") AttachmentParam queryParam);
}
