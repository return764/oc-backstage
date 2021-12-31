package com.oracleclub.server.service;

import com.oracleclub.server.entity.bbs.Tag;
import com.oracleclub.server.entity.vo.TagVO;
import com.oracleclub.server.service.base.ConverterService;
import com.oracleclub.server.service.base.CrudService;

import java.util.List;

/**
 * @author :RETURN
 * @date :2021/12/31 11:25
 */
public interface TagService extends CrudService<Tag,Long>, ConverterService<TagVO,Tag> {
    List<Tag> listTags();
}
