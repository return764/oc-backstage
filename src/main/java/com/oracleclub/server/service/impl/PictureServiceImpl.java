package com.oracleclub.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oracleclub.server.annotation.OperationLogMarker;
import com.oracleclub.server.dao.PictureMapper;
import com.oracleclub.server.entity.Picture;
import com.oracleclub.server.entity.enums.OperationType;
import com.oracleclub.server.entity.enums.PictureStatus;
import com.oracleclub.server.entity.enums.UploadFileType;
import com.oracleclub.server.entity.param.PictureQueryParam;
import com.oracleclub.server.entity.support.UploadFile;
import com.oracleclub.server.entity.support.UploadResult;
import com.oracleclub.server.entity.vo.PictureVO;
import com.oracleclub.server.handler.file.FileHandlers;
import com.oracleclub.server.handler.file.support.PictureUpload;
import com.oracleclub.server.service.PictureService;
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
 * (Picture)表服务实现类
 *
 * @author RETURN
 * @since 2020-08-13 22:00:59
 */
@Service
@Slf4j
public class PictureServiceImpl extends AbstractCrudService<Picture,Long> implements PictureService {

    private final PictureMapper pictureMapper;
    private final FileHandlers fileHandlers;
    private final PictureUpload pictureUpload;
    private final ApplicationEventPublisher eventPublisher;

    protected PictureServiceImpl(PictureMapper pictureMapper, FileHandlers fileHandlers, PictureUpload pictureUpload, ApplicationEventPublisher eventPublisher) {
        super(pictureMapper);
        this.pictureMapper = pictureMapper;
        this.fileHandlers = fileHandlers;
        this.pictureUpload = pictureUpload;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public PictureVO convertToVO(Picture picture) {
        return convertTo(picture);
    }

    @Override
    public List<PictureVO> convertToListVO(List<Picture> pictures) {
        log.debug("{}",pictures);
        return pictures.stream().map(this::convertTo).collect(Collectors.toList());
    }

    @Override
    public IPage<PictureVO> convertToPageVO(IPage<Picture> pictures) {
        return pictures.convert(this::convertTo);
    }

    private PictureVO convertTo(Picture picture){
        Assert.notNull(picture,"图片不能为空");

        return new PictureVO().convertFrom(picture);
    }

    @Override
    public List<Picture> listLatest(int top) {
        Assert.isTrue(top > 0,"Top number must not be less than 0");

        Page<Picture> pageRequest = new Page<>(0,top);
        return pictureMapper.selectPage(pageRequest,getPictureExistAndDESCWrapper()).getRecords();
    }

    private QueryWrapper<Picture> getPictureExistAndDESCWrapper() {
        QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", PictureStatus.EXIST)
                .orderByDesc("id");
        return queryWrapper;
    }

    @Override
    public PictureVO createBy(Picture picture) {
        return convertToVO(create(picture));
    }

    @Override
    public PictureVO updateBy(Picture picture) {
        return convertToVO(update(picture));
    }

    @OperationLogMarker(operaType = OperationType.UPLOAD, operaContent = "上传图片")
    @Override
    public Picture upload(MultipartFile file,String type) {
        Assert.notNull(file,"上传图片不能为空");
        Picture picture = new Picture();
        picture.setType(type);

        String uploadDir = pictureUpload.createUploadPath(picture);
        UploadResult upload = fileHandlers.upload(file, UploadFileType.LOCAL,uploadDir);

        picture.setHeight(upload.getHeight());
        picture.setWidth(upload.getWidth());
        picture.setSuffix(upload.getSuffix());
        picture.setSize(upload.getSize());
        picture.setMediaType(upload.getMediaType().toString());
        picture.setPath(ServiceUtils.changeFileSeparatorToUrlSeparator(upload.getFilePath()));
        picture.setThumbPath(upload.getThumbPath());
        picture.setStatus(PictureStatus.EXIST);
        picture.setUploadType(UploadFileType.LOCAL);
        picture.setUploadKey(upload.getFilePath());

        return create(picture);
    }

    @Override
    public List<String> getTypes() {
        return pictureMapper.findAllTypes();
    }

    @Override
    public IPage<PictureVO> pageBy(IPage<Picture> pageable, PictureQueryParam pictureParam) {
        Assert.notNull(pageable,"分页参数不能为空");

        IPage<Picture> all = pictureMapper.findAllExistWithParams(pageable,pictureParam);
        return convertToPageVO(all);
    }

    @OperationLogMarker(operaType = OperationType.DELETE, operaContent = "彻底删除图片")
    @Override
    public List<Picture> removePictures(List<Long> ids) {
        Assert.notEmpty(ids,"idList不能为空");

        return ids.stream().map(this::removePicture).collect(Collectors.toList());
    }

    @Override
    public Picture removePicture(Long id) {
        Picture picture = removeById(id);
        fileHandlers.delete(new UploadFile().convertFrom(picture));

        return picture;
    }

}
