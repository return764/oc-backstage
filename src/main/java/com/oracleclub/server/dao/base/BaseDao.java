package com.oracleclub.server.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.NonNull;

import java.util.Collection;

/**
 * @author : RETURN
 * @date : 2021/2/23 11:31
 */
@NoRepositoryBean
public interface BaseDao<DOMAIN,ID> extends JpaRepository<DOMAIN,ID> {

    long deleteByIdIn(@NonNull Collection<ID> ids);
}
