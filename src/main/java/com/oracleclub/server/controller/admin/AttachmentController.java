package com.oracleclub.server.controller.admin;

import com.oracleclub.server.entity.Attachment;
import com.oracleclub.server.entity.param.AttachmentParam;
import com.oracleclub.server.entity.vo.R;
import com.oracleclub.server.service.AttachmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author :RETURN
 * @date :2021/2/28 0:30
 */
@Slf4j
@RestController
@RequestMapping("api/admin/attachments")
public class AttachmentController {

    @Resource
    AttachmentService attachmentService;

    @GetMapping
    public R listAttachment(@PageableDefault(sort = "id",direction = Sort.Direction.DESC) Pageable pageable,
                            AttachmentParam attachmentParam){
        return R.success("获取附件列表成功",attachmentService.pageByParam(pageable, attachmentParam));
    }

    @GetMapping("{id:\\d+}")
    public R getAttachmentDetails(@PathVariable Long id){
        Attachment attachment = attachmentService.getByIdExist(id);
        return R.success("获取附件详情成功",attachmentService.convertToVO(attachment));
    }

    @DeleteMapping("{id:\\d+}")
    public R deleteAttachmentById(@PathVariable Long id,
                                  @RequestParam(name = "soft",required = false,defaultValue = "true") boolean soft){
        Attachment attachment;
        if (soft){
            attachment = attachmentService.removeLogicById(id);
        }else {
            attachment = attachmentService.removeAttachment(id);
        }
        return R.success("成功删除附件",attachmentService.convertToVO(attachment));
    }

    @DeleteMapping
    public R deleteAttachmentByIds(@RequestBody List<Long> ids,
                                   @RequestParam(name = "soft",required = false,defaultValue = "true") boolean soft){
        log.debug("deleted ids:{},soft:{}",ids,soft);
        List<Attachment> attachments;
        if (soft){
            attachments = ids.stream().map(id ->
                    attachmentService.removeLogicById(id)).collect(Collectors.toList());
        }else {
            attachments = attachmentService.removeAttachments(ids);
        }
        return R.success("成功批量删除附件",attachmentService.convertToListVO(attachments));
    }

    @PutMapping("{id:\\d+}")
    public R updateAttachmentName(@PathVariable Long id,
            String name){
        log.debug("id:{},name:{}",id,name);

        Attachment attachment = attachmentService.getByIdExist(id);

        attachment.setName(name);

        Attachment update = attachmentService.update(attachment);
        return R.success("成功修改附件名",attachmentService.convertToVO(update));
    }

    @PutMapping
    public R rollbackAttachment(@RequestBody List<Long> ids){
        log.debug("ids:{}",ids);

        return R.success("成功从回收站还原附件",
                attachmentService.convertToListVO(attachmentService.rollbackFromRemove(ids)));
    }

    @PostMapping("upload")
    public R upload(@RequestParam("file")MultipartFile file){
        return R.success("成功上传附件",attachmentService.convertToVO(attachmentService.upload(file)));
    }

    @PostMapping("uploads")
    public R uploads(@RequestParam("files")MultipartFile[] files){
        List<Attachment> list = new LinkedList<>();
        for (MultipartFile file : files) {
            Attachment attachment = attachmentService.upload(file);
            list.add(attachment);
        }

        return R.success("成功上传附件",attachmentService.convertToListVO(list));
    }
}
