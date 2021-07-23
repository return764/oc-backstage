package com.oracleclub.server.entity.base;

import com.oracleclub.server.entity.enums.UploadFileType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author :RETURN
 * @date :2021/7/23 22:41
 */
@Data
@MappedSuperclass
@EqualsAndHashCode
public class BaseUploadFile extends BaseEntity {

    @Column(name = "`upload_type`")
    @ColumnDefault("0")
    private UploadFileType uploadType;

    @Column(name = "`upload_key`")
    private String uploadKey;

}
