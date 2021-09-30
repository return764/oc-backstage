package com.oracleclub.server.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oracleclub.server.dao.AttachmentMapper;
import com.oracleclub.server.entity.Attachment;
import com.oracleclub.server.entity.enums.UploadFileType;
import com.oracleclub.server.entity.param.AttachmentParam;
import com.oracleclub.server.entity.support.UploadFile;
import com.oracleclub.server.entity.support.UploadResult;
import com.oracleclub.server.entity.vo.AttachmentVO;
import com.oracleclub.server.event.LogEvent;
import com.oracleclub.server.handler.file.FileHandlers;
import com.oracleclub.server.handler.file.support.AttachmentUpload;
import com.oracleclub.server.service.AttachmentService;
import com.oracleclub.server.service.base.AbstractCrudService;
import com.oracleclub.server.utils.ServiceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author :RETURN
 * @date :2021/2/28 0:31
 */
@Service
@Slf4j
public class AttachmentServiceImpl extends AbstractCrudService<Attachment,Long> implements AttachmentService {

    private final AttachmentMapper attachmentMapper;
    private final FileHandlers fileHandlers;
    private final AttachmentUpload attachmentUpload;
    private final ApplicationEventPublisher eventPublisher;

    public AttachmentServiceImpl(AttachmentMapper attachmentMapper, FileHandlers fileHandlers, AttachmentUpload attachmentUpload, ApplicationEventPublisher eventPublisher) {
        super(attachmentMapper);
        this.attachmentMapper = attachmentMapper;
        this.fileHandlers = fileHandlers;
        this.attachmentUpload = attachmentUpload;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public AttachmentVO convertToVO(Attachment attachment) {
        Assert.notNull(attachment,"待转换附件不能为空");

        return new AttachmentVO().convertFrom(attachment);
    }

    @Override
    public List<AttachmentVO> convertToListVO(List<Attachment> attachments) {
        Assert.notNull(attachments,"待转换附件列表不能为空");

        return attachments.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public IPage<AttachmentVO> convertToPageVO(IPage<Attachment> attachments) {
        Assert.notNull(attachments,"待转换附件列表不能为空");

        return attachments.convert(this::convertToVO);
    }

    @Override
    public Attachment upload(MultipartFile file) {
        Assert.notNull(file,"文件上传时不能为空");
        log.debug("开始上传文件... 类型:[{}]",file.getContentType());

        String uploadDir = attachmentUpload.createUploadPath();
        UploadResult upload = fileHandlers.upload(file, UploadFileType.LOCAL,uploadDir);

        Attachment a = new Attachment();
        a.setName(upload.getFileName());
        a.setMediaType(upload.getMediaType().toString());
        a.setUploadKey(ServiceUtils.changeFileSeparatorToUrlSeparator(upload.getFilePath()));
        a.setPath(ServiceUtils.changeFileSeparatorToUrlSeparator(upload.getFilePath()));
        a.setThumbPath(upload.getThumbPath());
        a.setHeight(upload.getHeight());
        a.setWidth(upload.getWidth());
        a.setSize(upload.getSize());
        a.setSuffix(upload.getSuffix());
        a.setUploadType(UploadFileType.LOCAL);

        LogEvent l = new LogEvent(this,"上传","上传附件:"+a.getName());
        eventPublisher.publishEvent(l);

        log.debug("正在创建附件: [{}]",a);

        return create(a);
    }

    @Override
    public IPage<AttachmentVO> pageByParam(IPage<Attachment> pageable, AttachmentParam attachmentParam) {
        Assert.notNull(pageable,"分页参数不能为空");
        IPage<Attachment> attachmentPage = attachmentMapper.findAllWithParams(pageable,attachmentParam);
        return convertToPageVO(attachmentPage);
    }

    @Override
    public List<Attachment> rollbackFromRemove(List<Long> ids) {
        Assert.notEmpty(ids,"idList不能为空");
        ids.forEach(this::rollBackById);

        LogEvent l = new LogEvent(this,"更新","恢复附件");
        eventPublisher.publishEvent(l);

        return attachmentMapper.selectBatchIds(ids);
    }

    @Override
    public List<Attachment> removeAttachments(List<Long> ids) {
        Assert.notEmpty(ids,"idList不能为空");

        LogEvent l = new LogEvent(this,"删除","删除附件");
        eventPublisher.publishEvent(l);

        return ids.stream().map(this::removeAttachment).collect(Collectors.toList());
    }

    @Override
    public Attachment removeAttachment(Long id) {
        Attachment attachment = removeById(id);
        fileHandlers.delete(new UploadFile().convertFrom(attachment));
        return attachment;
    }

}
