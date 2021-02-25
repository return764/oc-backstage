package com.oracleclub.server.utils;

import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author :RETURN
 * @date :2021/2/23 21:40
 */
public class ServiceUtils {

    public static boolean isEmptyId(@Nullable Number id){
        return id == null || id.longValue() <= 0;
    }

    public static <ID, T> Set<ID> fetchProperty(final Collection<T> datas, Function<T, ID> mappingFunction) {
        return CollectionUtils.isEmpty(datas) ?
                Collections.emptySet() :
                datas.stream().map(mappingFunction).collect(Collectors.toSet());
    }
}
