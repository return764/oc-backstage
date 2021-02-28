package com.oracleclub.server.service;

import com.oracleclub.server.entity.Attachment;
import com.oracleclub.server.entity.vo.AttachmentVO;
import com.oracleclub.server.service.base.ConverterService;
import com.oracleclub.server.service.base.CrudService;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author :RETURN
 * @date :2021/2/28 0:31
 */
public interface AttachmentService extends CrudService<Attachment,Long>,ConverterService<AttachmentVO,Attachment> {

    Attachment upload(MultipartFile file);

}
