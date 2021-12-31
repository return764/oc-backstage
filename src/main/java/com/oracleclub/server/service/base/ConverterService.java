package com.oracleclub.server.service.base;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author :RETURN
 * @date :2021/2/25 12:32
 */
public interface ConverterService<VO,DOMAIN> {
    VO convertToVO(DOMAIN domain);

    default List<VO> convertToListVO(List<DOMAIN> domains) {
        return domains.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    default IPage<VO> convertToPageVO(IPage<DOMAIN> domains) {
        return domains.convert(this::convertToVO);
    }
}
