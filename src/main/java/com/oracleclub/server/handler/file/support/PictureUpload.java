package com.oracleclub.server.handler.file.support;

import com.oracleclub.server.entity.Picture;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.oracleclub.server.entity.support.AppConstant.FILE_SEPARATOR;

/**
 * @author :RETURN
 * @date :2021/7/24 15:11
 */
@Component("pictureUpload")
public class PictureUpload implements UploadStrategy<Picture> {
    private final static String PICTURE_DIR = "pictures/";
    private final static String DEFAULT = "default";

    @Override
    public String createUploadPath(Picture picture) {
        String middlePath;
        if (picture == null){
            middlePath = DEFAULT;
        }else {
            Optional<String> op = Optional.of(picture.getType());
            middlePath = op.orElse(DEFAULT);
        }

        return PICTURE_DIR + middlePath + FILE_SEPARATOR;
    }

    @Override
    public String createUploadPath() {
        return createUploadPath(null);
    }
}
