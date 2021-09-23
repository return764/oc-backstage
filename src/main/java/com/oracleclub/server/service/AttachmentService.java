package com.oracleclub.server.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oracleclub.server.entity.Attachment;
import com.oracleclub.server.entity.param.AttachmentParam;
import com.oracleclub.server.entity.vo.AttachmentVO;
import com.oracleclub.server.service.base.ConverterService;
import com.oracleclub.server.service.base.CrudService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author :RETURN
 * @date :2021/2/28 0:31
 */
public interface AttachmentService extends CrudService<Attachment,Long>,ConverterService<AttachmentVO,Attachment> {

    Attachment upload(MultipartFile file);

    IPage<AttachmentVO> pageByParam(IPage<Attachment> pageable, AttachmentParam attachmentParam);

    List<Attachment> rollbackFromRemove(List<Long> ids);

    List<Attachment> removeAttachments(List<Long> ids);

    Attachment removeAttachment(Long id);
}
