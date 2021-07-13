package com.oracleclub.server.utils;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.oracleclub.server.entity.support.AppConstant.FILE_SEPARATOR;
import static com.oracleclub.server.entity.support.AppConstant.URL_SEPARATOR;

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

    public static String changeFileSeparatorToUrlSeparator(String path){
        Assert.hasText(path,"路径不能为空");
        if (FILE_SEPARATOR.equals(URL_SEPARATOR)){
            return path;
        }
        return path.replace(FILE_SEPARATOR,URL_SEPARATOR);
    }
}
