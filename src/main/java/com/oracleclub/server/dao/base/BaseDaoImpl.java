package com.oracleclub.server.dao.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.List;

/**
 * @author :RETURN
 * @date :2021/2/24 0:15
 */
@Slf4j
public class BaseDaoImpl<DOMAIN, ID> extends SimpleJpaRepository<DOMAIN, ID> implements BaseDao<DOMAIN, ID> {

    private final JpaEntityInformation<DOMAIN, ID> entityInformation;

    public BaseDaoImpl(JpaEntityInformation<DOMAIN, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
    }

    @Override
    @Transactional
    public long deleteByIdIn(Collection<ID> ids) {
        log.debug("Customized deleteByIdIn method was invoked");
        List<DOMAIN> domains = findAllById(ids);

        deleteInBatch(domains);

        return domains.size();
    }
}
