package com.oracleclub.server.entity.vo;

import com.oracleclub.server.converter.OutputConverter;
import com.oracleclub.server.entity.Attachment;
import com.oracleclub.server.entity.enums.AttachmentType;
import lombok.Data;

/**
 * @author :RETURN
 * @date :2021/2/28 0:33
 */
@Data
public class AttachmentVO implements OutputConverter<AttachmentVO, Attachment> {

    private String name;
    private String path;
    private String key;
    private String thumbPath;
    private String mediaType;
    private String suffix;
    private Integer width;
    private Integer height;
    private Long size;
    private AttachmentType type;
}
