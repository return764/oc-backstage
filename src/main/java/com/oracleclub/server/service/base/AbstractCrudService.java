package com.oracleclub.server.service.base;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oracleclub.server.dao.base.BaseDao;
import com.oracleclub.server.entity.base.BaseEntity;
import com.oracleclub.server.exception.NotFoundException;
import com.oracleclub.server.utils.ServiceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author :RETURN
 * @date :2021/2/23 11:23
 */
@Slf4j
public abstract class AbstractCrudService<DOMAIN extends BaseEntity,ID extends Serializable> implements CrudService<DOMAIN,ID> {

    private final BaseDao<DOMAIN,ID> baseMapper;
    private final String domainName;

    protected AbstractCrudService(BaseDao<DOMAIN,ID> baseMapper){
        this.baseMapper = baseMapper;

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
        return baseMapper.selectList(null);
    }

    @Override
    public IPage<DOMAIN> listAll(IPage<DOMAIN> pageable) {
        return baseMapper.selectPage(pageable,null);
    }

    @Override
    public List<DOMAIN> listAllByIds(Collection<ID> ids) {
        return CollectionUtils.isEmpty(ids) ? Collections.emptyList() : baseMapper.selectBatchIds(ids);
    }

    private Optional<DOMAIN> fetchById(ID id,boolean expand) {
        Assert.notNull(id, domainName + "不能为空");
        if (expand){
            return Optional.ofNullable(baseMapper.selectById(id));
        }
        return Optional.ofNullable(baseMapper.selectById(id));
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
        QueryWrapper<DOMAIN> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id)
                .isNull("deleted_at");

        return !Objects.isNull(baseMapper.selectOne(wrapper));
    }

    @Override
    public long count() {
        return baseMapper.selectCount(null);
    }

    @Override
    public DOMAIN create(DOMAIN domain) {
        Assert.notNull(domain, domainName + " data must not be null");
        baseMapper.insert(domain);
        return baseMapper.selectById(domain.getId());
    }

    @Override
    public List<DOMAIN> createInBatch(Collection<DOMAIN> domains) {
        return CollectionUtils.isEmpty(domains) ? Collections.emptyList() : baseMapper.insertInBatch(domains);
    }

    @Override
    public DOMAIN update(DOMAIN domain) {
        Assert.notNull(domain, domainName + " data must not be null");
        baseMapper.updateById(domain);
        return baseMapper.selectById(domain.getId());
    }

    @Override
    public List<DOMAIN> updateInBatch(Collection<DOMAIN> domains) {
        domains.forEach(baseMapper::updateById);
        List<ID> idList = (List<ID>) domains.stream().map(DOMAIN::getId).collect(Collectors.toList());
        return CollectionUtils.isEmpty(domains) ? Collections.emptyList() : baseMapper.selectBatchIds(idList);
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
        baseMapper.deleteById(id);
        return domain;
    }

    @Override
    public DOMAIN removeByIdOfNullable(ID id) {
        return Optional.of(removeById(id)).orElse(null);
    }

    @Override
    public DOMAIN removeLogicById(ID id) {
        Assert.notNull(id,"id must not be null");
        DOMAIN domain = getById(id);
        domain.setDeletedAt(LocalDateTime.now());
        baseMapper.updateById(domain);
        return domain;
    }

    @Override
    public DOMAIN rollBackById(ID id) {
        Assert.notNull(id,"id must not be null");
        DOMAIN domain = getById(id);
        domain.setDeletedAt(null);
        baseMapper.updateById(domain);
        return domain;
    }

    @Override
    public void removeInBatch(Collection<ID> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            log.debug(domainName + " id collection is empty");
            return;
        }

        baseMapper.deleteBatchIds(ids);
    }

    protected final Set<String> commaSeparatedStringToSet(String str) {
        Set<String> result = new HashSet<>();
        if (StrUtil.isNotEmpty(str)) {
            result.addAll(Arrays.asList(str.split(",")));
        }
        return result;
    }
}
