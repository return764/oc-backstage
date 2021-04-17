package com.oracleclub.server.handler.file;

import cn.hutool.core.util.StrUtil;
import com.oracleclub.server.entity.enums.AttachmentType;
import com.oracleclub.server.entity.support.AppConstant;
import com.oracleclub.server.entity.support.UploadResult;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;

/**
 * @author :RETURN
 * @date :2021/2/25 21:20
 */
public interface FileHandler {

    MediaType IMAGE_TYPE = MediaType.valueOf("image/*");

    static boolean isImageType(@Nullable String mediaType) {
        return mediaType != null && IMAGE_TYPE.includes(MediaType.valueOf(mediaType));
    }

    static boolean isImageType(@Nullable MediaType mediaType) {
        return mediaType != null && IMAGE_TYPE.includes(mediaType);
    }

    @NonNull
    static String normalizeDirectory(@NonNull String dir){
        Assert.hasText(dir,"Directory name must not be blank");
        String normal = Paths.get(dir).normalize().toString();
        return StrUtil.appendIfMissing(normal, AppConstant.FILE_SEPARATOR);
    }

    @NonNull
    UploadResult upload(@NonNull MultipartFile file,boolean isPicture);

    void delete(String path);

    AttachmentType getAttachmentType();
}
