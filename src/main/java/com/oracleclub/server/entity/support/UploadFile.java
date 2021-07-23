package com.oracleclub.server.entity.support;

import com.oracleclub.server.converter.OutputConverter;
import com.oracleclub.server.entity.base.BaseUploadFile;
import com.oracleclub.server.entity.enums.UploadFileType;
import lombok.*;

/**
 * @author :RETURN
 * @date :2021/7/23 16:35
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UploadFile implements OutputConverter<UploadFile, BaseUploadFile> {
    private UploadFileType uploadType;
    private String uploadKey;
}
