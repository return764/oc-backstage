package com.oracleclub.server.entity.support;

import lombok.Data;
import lombok.ToString;
import org.springframework.http.MediaType;

/**
 * @author :RETURN
 * @date :2021/2/25 21:26
 */
@Data
@ToString
public class UploadResult {

    private String fileName;
    private String filePath;
    private String thumbPath;
    private String suffix;
    private MediaType mediaType;
    private Long size;
    private Integer height;
    private Integer width;
}
