package com.oracleclub.server.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.oracleclub.server.converter.OutputConverter;
import com.oracleclub.server.entity.Picture;
import lombok.Data;

import java.util.Date;

/**
 * @author RETURN
 * @date 2020/8/16 21:16
 */
@Data
public class PictureDetailVO implements OutputConverter<PictureDetailVO, Picture> {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String path;
    private Date uploadTime;
    private String describe;
    private String height;
    private String width;
    private String format;
    private Long size;
}
