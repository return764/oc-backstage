package com.oracleclub.server.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.oracleclub.server.entity.base.BaseUploadFile;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author :RETURN
 * @date :2021/2/27 21:47
 */
@Data
@ToString(callSuper = true)
@TableName("attachments")
public class Attachment extends BaseUploadFile implements Serializable {

    private String name;

    private String path;

    private String thumbPath;

    private String mediaType;

    private String suffix;

    private Integer width;

    private Integer height;

    private Long size;

}
