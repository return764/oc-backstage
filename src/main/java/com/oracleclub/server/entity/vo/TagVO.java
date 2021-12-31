package com.oracleclub.server.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.oracleclub.server.converter.OutputConverter;
import com.oracleclub.server.entity.bbs.Tag;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author :RETURN
 * @date :2021/12/31 11:20
 */
@Data
public class TagVO implements OutputConverter<TagVO, Tag> {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String name;
    private String color;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
