package com.oracleclub.server.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.oracleclub.server.entity.base.BaseUploadFile;
import com.oracleclub.server.entity.enums.PictureStatus;
import lombok.Data;

import java.io.Serializable;

/**
 * (Picture)实体类
 *
 * @author RETURN
 * @since 2020-08-13 22:00:59
 */
@Data
@TableName("pictures")
public class Picture extends BaseUploadFile implements Serializable {
    private String name;
    private String fileName;
    /**
     * 图片地址
     */
    private String path;
    /**
     * 缩略图地址
     */
    private String thumbPath;
    /**
     * 图片描述
     */
    private String description;
    private Long size;
    private Integer height;
    private Integer width;
    private String suffix;
    /**
     * 图片状态
     */
    private PictureStatus status;
    private String mediaType;
    private String type;
}
