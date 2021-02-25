package com.oracleclub.server.service.base;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author :RETURN
 * @date :2021/2/25 12:32
 */
public interface ConverterService<VO,DOMAIN> {
    VO convertToVO(DOMAIN domain);

    List<VO> convertToListVO(List<DOMAIN> domains);

    Page<VO> convertToPageVO(Page<DOMAIN> domains);
}
