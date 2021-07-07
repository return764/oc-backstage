package com.oracleclub.server.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author :RETURN
 * @date :2021/2/23 10:34
 */
public interface CrudService<DOMAIN,ID> {
    @NonNull
    List<DOMAIN> listAll();

    @NonNull
    Page<DOMAIN> listAll(@NonNull Pageable pageable);

    @NonNull
    List<DOMAIN> listAllByIds(@NonNull Collection<ID> ids);

    @NonNull
    Optional<DOMAIN> fetchById(@NonNull ID id);

    @NonNull
    DOMAIN getById(@NonNull ID id);

    @Nullable
    DOMAIN getByIdOfNullable(@NonNull ID id);

    boolean existById(@NonNull ID id);

    long count();

    @Transactional
    @NonNull
    DOMAIN create(@NonNull DOMAIN domain);

    @Transactional
    @NonNull
    List<DOMAIN> createInBatch(@NonNull Collection<DOMAIN> domains);

    void flush();

    @Transactional
    @NonNull
    DOMAIN update(@NonNull DOMAIN domain);

    @Transactional
    @NonNull
    List<DOMAIN> updateInBatch(@NonNull Collection<DOMAIN> domains);

    @Transactional
    @NonNull
    DOMAIN createOrUpdate(@NonNull DOMAIN domain);

    @Transactional
    @NonNull
    DOMAIN removeById(@NonNull ID id);

    @Transactional
    @NonNull
    DOMAIN removeLogicById(@NonNull ID id);

    @Transactional
    @Nullable
    DOMAIN removeByIdOfNullable(@NonNull ID id);

    @Transactional
    void remove(@NonNull DOMAIN domain);

    @Transactional
    void removeInBatch(@NonNull Collection<ID> ids);

    @Transactional
    void removeAll(@NonNull Collection<DOMAIN> domains);

    @Transactional
    void removeAll();

}
