package com.oracleclub.server.dao.base;

import com.oracleclub.server.entity.base.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author :RETURN
 * @date :2021/2/24 0:15
 */
@Slf4j
public class BaseDaoImpl<DOMAIN extends BaseEntity, ID> extends SimpleJpaRepository<DOMAIN, ID> implements BaseDao<DOMAIN, ID> {

    private final JpaEntityInformation<DOMAIN, ID> entityInformation;
    private final EntityManager entityManager;

    public BaseDaoImpl(JpaEntityInformation<DOMAIN, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.entityManager = entityManager;
    }

    @Override
    public <S extends DOMAIN> S save(S entity) {
        entity.setUpdatedAt(LocalDateTime.now());
        return super.save(entity);
    }

    @Override
    public long deleteByIdIn(Collection<ID> ids) {
        log.debug("Customized deleteByIdIn method was invoked");
        List<DOMAIN> domains = findAllById(ids);

        deleteInBatch(domains);

        return domains.size();
    }

    @Override
    public DOMAIN logicDelete(ID id) {
        DOMAIN domain = getOne(id);

        domain.setDeletedAt(LocalDateTime.now());

        return save(domain);
    }

    @Override
    public Optional<DOMAIN> findByIdWithExist(ID id) {
        return findOne(getDeletedAtQueryWithId(id));
    }

    @Override
    public List<DOMAIN> findAllWithExist(){
        return super.findAll(getDeletedAtQuery(true));
    }

    @Override
    public Page<DOMAIN> findAllWithExist(Pageable pageable){
        return super.findAll(getDeletedAtQuery(true),pageable);
    }

    @Override
    public Page<DOMAIN> findAllWithNotExist(Pageable pageable){
        return super.findAll(getDeletedAtQuery(false),pageable);
    }

    @Override
    public Page<DOMAIN> findAllWithExist(@NonNull Specification specification, Pageable pageable){
        Assert.notNull(specification,"specification is not be null");

        return super.findAll(specification.and(getDeletedAtQuery(true)),pageable);
    }
    @Override
    public Page<DOMAIN> findAllWithNotExist(@NonNull Specification specification, Pageable pageable){
        Assert.notNull(specification,"specification is not be null");

        return super.findAll(getDeletedAtQuery(false),pageable);
    }

    private Specification<DOMAIN> getDeletedAtQueryWithId(ID id){
        return ((root, criteriaQuery, criteriaBuilder) -> {
            Predicate p = criteriaBuilder.conjunction();

            p = criteriaBuilder.and(p,criteriaBuilder.equal(root.get("id").as(Long.class),id));
            p = criteriaBuilder.and(p,criteriaBuilder.isNull(root.get("deletedAt")));

            return criteriaQuery.where(p).getRestriction();
        });
    }

    private Specification<DOMAIN> getDeletedAtQuery(boolean exist){
        return ((root, criteriaQuery, criteriaBuilder) -> {
            Predicate p = criteriaBuilder.conjunction();
            if (exist){
                p = criteriaBuilder.and(p,criteriaBuilder.isNull(root.get("deletedAt")));
            }else {
                p = criteriaBuilder.and(p,criteriaBuilder.isNotNull(root.get("deletedAt")));
            }
            return criteriaQuery.where(p).getRestriction();
        });
    }
}
