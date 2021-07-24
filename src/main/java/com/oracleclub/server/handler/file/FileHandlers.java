package com.oracleclub.server.handler.file;

import com.oracleclub.server.entity.enums.UploadFileType;
import com.oracleclub.server.entity.support.UploadFile;
import com.oracleclub.server.entity.support.UploadResult;
import com.oracleclub.server.exception.FileOperationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author :RETURN
 * @date :2021/2/27 21:19
 */
@Component
@Slf4j
public class FileHandlers {

    private final ConcurrentHashMap<UploadFileType,FileHandler> fileHandlers = new ConcurrentHashMap<>(16);

    public FileHandlers(ApplicationContext applicationContext){
        addFileHandler(applicationContext.getBeansOfType(FileHandler.class).values());
        log.info("注册{}个文件处理器",fileHandlers.size());
    }

    public UploadResult upload(MultipartFile file, UploadFileType uploadFileType, String uploadDir) {
        return getSupportedType(uploadFileType).upload(file,uploadDir);
    }

    public void delete(UploadFile uploadFile) {
        Assert.notNull(uploadFile, "附件不能为空");
        getSupportedType(uploadFile.getUploadType())
                .delete(uploadFile.getUploadKey());
    }

    private FileHandler getSupportedType(UploadFileType uploadFileType) {
        FileHandler handler = fileHandlers.getOrDefault(uploadFileType, fileHandlers.get(UploadFileType.LOCAL));
        if (handler == null) {
            throw new FileOperationException("没有合适的文件处理器去处理文件").setErrorData(uploadFileType);
        }
        return handler;
    }

    public FileHandlers addFileHandler(Collection<FileHandler> fileHandlers){
        if(!CollectionUtils.isEmpty(fileHandlers)){
            for (FileHandler handler : fileHandlers) {
                if (this.fileHandlers.containsKey(handler.getUploadFileType())){
                    throw new RuntimeException("相同的附件类型必须唯一");
                }
                this.fileHandlers.put(handler.getUploadFileType(),handler);
            }
        }
        return this;
    }
}
