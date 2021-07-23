package com.oracleclub.server.entity;

import com.oracleclub.server.entity.base.BaseUploadFile;
import com.oracleclub.server.entity.enums.PictureStatus;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * (Picture)实体类
 *
 * @author RETURN
 * @since 2020-08-13 22:00:59
 */
@Data
@Entity
@Table(name = "pictures")
public class Picture extends BaseUploadFile implements Serializable {
    @Column
    private String name;
    @Column(name = "file_name")
    private String fileName;
    /**
     * 图片地址
     */
    @Column
    private String path;
    /**
     * 缩略图地址
     */
    @Column(name = "thumb_path")
    private String thumbPath;
    /**
     * 图片描述
     */
    @Column
    private String description;
    @Column
    private Long size;
    @Column
    private Integer height;
    @Column
    private Integer width;
    @Column
    private String suffix;
    /**
     * 图片状态
     */
    @Column
    @ColumnDefault("0")
    private PictureStatus status;
    @Column(name = "media_type")
    private String mediaType;
    @Column
    private String type;
}
