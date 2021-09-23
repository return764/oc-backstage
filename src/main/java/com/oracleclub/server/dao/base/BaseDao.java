package com.oracleclub.server.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Collection;
import java.util.List;

/**
 * @author : RETURN
 * @date : 2021/2/23 11:31
 */
public interface BaseDao<DOMAIN,ID> extends BaseMapper<DOMAIN> {

    List<DOMAIN> insertInBatchById(Collection<ID> ids);

    List<DOMAIN> insertInBatch(Collection<DOMAIN> domains);

    List<DOMAIN> updateInBatch(Collection<DOMAIN> domains);

    List<DOMAIN> selectAllExist();
}
