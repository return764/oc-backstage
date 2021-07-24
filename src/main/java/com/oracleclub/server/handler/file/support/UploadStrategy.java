package com.oracleclub.server.handler.file.support;

/**
 * @author :RETURN
 * @date :2021/7/24 15:06
 */
public interface UploadStrategy<DOMAIN> {
    String createUploadPath(DOMAIN domain);
    String createUploadPath();
}
