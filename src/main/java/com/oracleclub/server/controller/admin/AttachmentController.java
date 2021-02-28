package com.oracleclub.server.controller.admin;

import com.oracleclub.server.entity.Attachment;
import com.oracleclub.server.entity.vo.R;
import com.oracleclub.server.service.AttachmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

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
