package com.oracleclub.server.dao.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author : RETURN
 * @date : 2021/2/23 11:31
 */
@NoRepositoryBean
public interface BaseDao<DOMAIN,ID> extends JpaRepository<DOMAIN,ID>, JpaSpecificationExecutor<DOMAIN> {

    long deleteByIdIn(@NonNull Collection<ID> ids);

    DOMAIN logicDelete(ID id);

    Optional<DOMAIN> findByIdWithExist(ID id);

    List<DOMAIN> findAllWithExist();

    Page<DOMAIN> findAllWithExist(Pageable pageable);

    Page<DOMAIN> findAllWithNotExist(Pageable pageable);

    Page<DOMAIN> findAllWithExist(@NonNull Specification specification, Pageable pageable);

    Page<DOMAIN> findAllWithNotExist(@NonNull Specification specification, Pageable pageable);
}
