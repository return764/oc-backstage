package com.oracleclub.server.entity.base;

import com.oracleclub.server.entity.enums.UploadFileType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author :RETURN
 * @date :2021/7/23 22:41
 */
@Data
@EqualsAndHashCode
public class BaseUploadFile extends BaseEntity {

    private UploadFileType uploadType;

    private String uploadKey;

}
