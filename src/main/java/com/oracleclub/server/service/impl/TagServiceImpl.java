package com.oracleclub.server.service.impl;

import com.oracleclub.server.dao.TagMapper;
import com.oracleclub.server.entity.bbs.Tag;
import com.oracleclub.server.entity.vo.TagVO;
import com.oracleclub.server.service.TagService;
import com.oracleclub.server.service.base.AbstractCrudService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author :RETURN
 * @date :2021/12/31 11:26
 */
@Service
public class TagServiceImpl extends AbstractCrudService<Tag,Long> implements TagService {

    private final TagMapper tagMapper;

    protected TagServiceImpl(TagMapper tagMapper) {
        super(tagMapper);
        this.tagMapper = tagMapper;
    }

    @Override
    public List<Tag> listTags() {
        return listAll();
    }

    @Override
    public TagVO convertToVO(Tag tag) {
        return new TagVO().convertFrom(tag);
    }

}
