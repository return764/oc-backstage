package com.oracleclub.server.service.base;

import com.oracleclub.server.dao.base.BaseDao;
import com.oracleclub.server.entity.base.BaseEntity;
import com.oracleclub.server.exception.NotFoundException;
import com.oracleclub.server.utils.ServiceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author :RETURN
 * @date :2021/2/23 11:23
 */
@Slf4j
public abstract class AbstractCrudService<DOMAIN extends BaseEntity,ID> implements CrudService<DOMAIN,ID> {

    private final BaseDao<DOMAIN,ID> baseDao;
    private final String domainName;

    protected AbstractCrudService(BaseDao<DOMAIN,ID> baseDao){
        this.baseDao = baseDao;

        @SuppressWarnings("unchecked")
        Class<DOMAIN> domainClass = (Class<DOMAIN>) fetchType(0);
        domainName = domainClass.getSimpleName();
    }

    private Type fetchType(int index) {
        Assert.isTrue(index >= 0 && index <= 1, "type index must be between 0 to 1");

        return ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[index];
    }

    @Override
    public List<DOMAIN> listAll() {
        return baseDao.findAll();
    }

    @Override
    public Page<DOMAIN> listAll(Pageable pageable) {
        return baseDao.findAll(pageable);
    }

    @Override
    public List<DOMAIN> listAllByIds(Collection<ID> ids) {
        return CollectionUtils.isEmpty(ids) ? Collections.emptyList() : baseDao.findAllById(ids);
    }

    private Optional<DOMAIN> fetchById(ID id,boolean expand) {
        Assert.notNull(id, domainName + "不能为空");
        //todo 重大bug 分离此方法 总共分为 查询带deletedAt 和 不带deletedAt 此方法改为私有
        if (expand){
            return baseDao.findById(id);
        }
        return baseDao.findByIdExist(id);
    }

    @Override
    public DOMAIN getById(ID id) {
        return fetchById(id,true).orElseThrow(() -> new NotFoundException(domainName+"未找到或者已删除"));
    }

    @Override
    public DOMAIN getByIdOfNullable(ID id) {
        return fetchById(id,true).orElse(null);
    }

    @Override
    public DOMAIN getByIdExist(ID id) {
        return fetchById(id,false).orElseThrow(() -> new NotFoundException(domainName+"未找到或者已删除"));
    }

    @Override
    public DOMAIN getByIdOfNullableExist(ID id) {
        return fetchById(id,false).orElse(null);
    }

    @Override
    public boolean existById(ID id) {
        Assert.notNull(id, domainName + " id must not be null");

        return baseDao.existsById(id);
    }

    @Override
    public long count() {
        return baseDao.count();
    }

    @Override
    public DOMAIN create(DOMAIN domain) {
        Assert.notNull(domain, domainName + " data must not be null");

        return baseDao.save(domain);
    }

    @Override
    public List<DOMAIN> createInBatch(Collection<DOMAIN> domains) {
        return CollectionUtils.isEmpty(domains) ? Collections.emptyList() : baseDao.saveAll(domains);
    }

    @Override
    public void flush() {
        baseDao.flush();
    }

    @Override
    public DOMAIN update(DOMAIN domain) {
        Assert.notNull(domain, domainName + " data must not be null");
        return baseDao.saveAndFlush(domain);
    }

    @Override
    public List<DOMAIN> updateInBatch(Collection<DOMAIN> domains) {
        return CollectionUtils.isEmpty(domains) ? Collections.emptyList() : baseDao.saveAll(domains);
    }

    @Override
    public DOMAIN createOrUpdate(DOMAIN domain) {
        if (ServiceUtils.isEmptyId(domain.getId())){
            return create(domain);
        }
        return update(domain);
    }

    @Override
    public DOMAIN removeById(ID id) {
        DOMAIN domain = getById(id);

        remove(domain);
        return domain;
    }

    @Override
    public DOMAIN removeByIdOfNullable(ID id) {
        return fetchById(id,true).map(domain -> {
            remove(domain);
            return domain;
        }).orElse(null);
    }

    @Override
    public void remove(DOMAIN domain) {
        Assert.notNull(domain, domainName + " data must not be null");

        baseDao.delete(domain);
    }

    @Override
    public DOMAIN removeLogicById(ID id) {
        Assert.notNull(id,"id must not be null");

        return baseDao.logicDelete(id);
    }

    @Override
    public void removeInBatch(Collection<ID> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            log.debug(domainName + " id collection is empty");
            return;
        }

        baseDao.deleteByIdIn(ids);
    }

    @Override
    public void removeAll(Collection<DOMAIN> domains) {
        if (CollectionUtils.isEmpty(domains)) {
            log.debug(domainName + " collection is empty");
            return;
        }
        baseDao.deleteInBatch(domains);
    }

    @Override
    public void removeAll() {
        baseDao.deleteAll();
    }



}
