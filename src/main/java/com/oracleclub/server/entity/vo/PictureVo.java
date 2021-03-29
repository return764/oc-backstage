package com.oracleclub.server.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.oracleclub.server.converter.OutputConverter;
import com.oracleclub.server.entity.Picture;

/**
 * @author :RETURN
 * @date :2021/2/25 12:14
 */
public class PictureVo implements OutputConverter<PictureVo, Picture> {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String path;
    private String pathMini;
    private String description;
    private Long size;
    private Integer height;
    private Integer width;
    private String type;
}
