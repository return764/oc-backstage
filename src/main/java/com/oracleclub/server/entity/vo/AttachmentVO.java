package com.oracleclub.server.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.oracleclub.server.converter.OutputConverter;
import com.oracleclub.server.entity.Attachment;
import com.oracleclub.server.entity.enums.UploadFileType;
import lombok.Data;

import java.util.Date;

/**
 * @author :RETURN
 * @date :2021/2/28 0:33
 */
@Data
public class AttachmentVO implements OutputConverter<AttachmentVO, Attachment> {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String name;
    private String path;
    private String key;
    private String thumbPath;
    private String mediaType;
    private String suffix;
    private Integer width;
    private Integer height;
    private Long size;
    private UploadFileType type;
    private Date createdAt;
}
