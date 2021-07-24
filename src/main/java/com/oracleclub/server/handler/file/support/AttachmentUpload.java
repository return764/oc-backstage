package com.oracleclub.server.handler.file.support;

import com.oracleclub.server.entity.Attachment;
import org.springframework.stereotype.Component;

import java.util.Calendar;

import static com.oracleclub.server.entity.support.AppConstant.FILE_SEPARATOR;

/**
 * @author :RETURN
 * @date :2021/7/24 15:10
 */
@Component("attachmentUpload")
public class AttachmentUpload implements UploadStrategy<Attachment> {
    private final static String UPLOAD_DIR = "upload/";

    @Override
    public String createUploadPath(Attachment attachment) {
        Calendar current = Calendar.getInstance();

        int year = current.get(Calendar.YEAR);
        int month = current.get(Calendar.MONTH);

        String monthStr = month > 10 ? String.valueOf(month) : "0"+month;

        return UPLOAD_DIR + year + FILE_SEPARATOR + monthStr + FILE_SEPARATOR;
    }

    @Override
    public String createUploadPath() {
        return createUploadPath(null);
    }
}
