package com.oracleclub.server.entity.param;

import com.oracleclub.server.converter.InputConverter;
import com.oracleclub.server.entity.Picture;
import lombok.Data;

/**
 * @author :RETURN
 * @date :2021/2/24 16:57
 */
@Data
public class PictureParam implements InputConverter<Picture> {

    private String name;
    private String description;
    private Long size;
    private Integer height;
    private Integer width;
    private Integer suffix;

}
