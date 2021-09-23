package com.oracleclub.server.service.base;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * @author :RETURN
 * @date :2021/2/25 12:32
 */
public interface ConverterService<VO,DOMAIN> {
    VO convertToVO(DOMAIN domain);

    List<VO> convertToListVO(List<DOMAIN> domains);

    IPage<VO> convertToPageVO(IPage<DOMAIN> domains);
}
