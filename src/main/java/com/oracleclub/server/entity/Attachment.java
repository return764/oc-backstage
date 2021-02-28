package com.oracleclub.server.entity;

import com.oracleclub.server.entity.base.BaseEntity;
import com.oracleclub.server.entity.enums.AttachmentType;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author :RETURN
 * @date :2021/2/27 21:47
 */
@Data
@Entity
@Table(name = "attachments")
@ToString(callSuper = true)
public class Attachment extends BaseEntity implements Serializable {


    @Column
    private String name;

    @Column
    private String path;

    @Column(name = "`key`")
    private String key;

    @Column(name = "thumb_path")
    private String thumbPath;

    @Column(name = "media_type")
    private String mediaType;

    @Column
    private String suffix;

    @Column
    private Integer width;

    @Column
    private Integer height;

    @Column
    private Long size;

    @Column
    @ColumnDefault("0")
    private AttachmentType type;

    @Override
    protected void prePersist() {
        super.prePersist();
    }
}
