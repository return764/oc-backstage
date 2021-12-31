package com.oracleclub.server.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.oracleclub.server.converter.OutputConverter;
import com.oracleclub.server.entity.bbs.Tag;
import lombok.Data;

/**
 * @author :RETURN
 * @date :2021/12/31 14:30
 */
@Data
public class SimpleTagVO implements OutputConverter<SimpleTagVO, Tag> {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String name;
    private String color;
}
