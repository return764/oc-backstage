package com.oracleclub.server.service.impl;

import com.oracleclub.server.dao.AttachmentDao;
import com.oracleclub.server.entity.Attachment;
import com.oracleclub.server.entity.enums.AttachmentType;
import com.oracleclub.server.entity.param.AttachmentParam;
import com.oracleclub.server.entity.support.UploadResult;
import com.oracleclub.server.entity.vo.AttachmentVO;
import com.oracleclub.server.handler.file.FileHandlers;
import com.oracleclub.server.service.AttachmentService;
import com.oracleclub.server.service.base.AbstractCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Predicate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author :RETURN
 * @date :2021/2/28 0:31
 */
@Service
@Slf4j
public class AttachmentServiceImpl extends AbstractCrudService<Attachment,Long> implements AttachmentService {

    private final AttachmentDao attachmentDao;
    private final FileHandlers fileHandlers;

    public AttachmentServiceImpl(AttachmentDao attachmentDao,FileHandlers fileHandlers) {
        super(attachmentDao);
        this.attachmentDao = attachmentDao;
        this.fileHandlers = fileHandlers;
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
    public Page<AttachmentVO> convertToPageVO(Page<Attachment> attachments) {
        Assert.notNull(attachments,"待转换附件列表不能为空");

        return attachments.map(this::convertToVO);
    }

    @Override
    public Attachment upload(MultipartFile file) {
        Assert.notNull(file,"文件上传时不能为空");
        log.debug("开始上传图片... 类型:[{}]",file.getContentType());

        UploadResult upload = fileHandlers.upload(file, AttachmentType.LOCAL);

        Attachment a = new Attachment();
        a.setName(upload.getFileName());
        a.setMediaType(upload.getMediaType().toString());
        a.setKey(upload.getFilePath());
        a.setPath(upload.getFilePath());
        a.setThumbPath(upload.getThumbPath());
        a.setHeight(upload.getHeight());
        a.setWidth(upload.getWidth());
        a.setSize(upload.getSize());
        a.setSuffix(upload.getSuffix());
        a.setType(AttachmentType.LOCAL);

        log.debug("正在创建附件: [{}]",a);

        return create(a);
    }

    @Override
    public Page<AttachmentVO> pageByParam(Pageable pageable, AttachmentParam attachmentParam) {
        Assert.notNull(pageable,"分页参数不能为空");

        Page<Attachment> attachmentPage = attachmentDao.findAllWithExist(buildParam(attachmentParam), pageable);
        return convertToPageVO(attachmentPage);
    }

    private Specification<Attachment> buildParam(AttachmentParam attachmentParam) {
        Assert.notNull(attachmentParam,"附件参数不能为空");

        return (Specification<Attachment>) (root, cq, cb) -> {
            List<Predicate> predicates = new LinkedList<>();

            if (attachmentParam.getSuffix() != null){
                predicates.add(cb.equal(root.get("suffix").as(String.class), attachmentParam.getSuffix()));
            }

            if (attachmentParam.getName() != null){
                predicates.add(cb.or(
                        cb.like(root.get("name").as(String.class),"%"+attachmentParam.getName().trim()+"%")
                ));
            }

            return cq.where(predicates.toArray(new Predicate[0])).getRestriction();
        };
    }
}
