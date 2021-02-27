package com.oracleclub.server.service.impl;

import com.oracleclub.server.dao.PictureDao;
import com.oracleclub.server.entity.Picture;
import com.oracleclub.server.entity.enums.PictureStatus;
import com.oracleclub.server.entity.vo.PictureVo;
import com.oracleclub.server.service.PictureService;
import com.oracleclub.server.service.base.AbstractCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
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

    private final PictureDao pictureDao;

    protected PictureServiceImpl(PictureDao pictureDao) {
        super(pictureDao);
        this.pictureDao = pictureDao;
    }

    @Override
    public PictureVo convertToVO(Picture picture) {
        return convertTo(picture);
    }

    @Override
    public List<PictureVo> convertToListVO(List<Picture> pictures) {
        return pictures.stream().map(this::convertTo).collect(Collectors.toList());
    }

    @Override
    public Page<PictureVo> convertToPageVO(Page<Picture> pictures) {
        return pictures.map(this::convertTo);
    }

    private PictureVo convertTo(Picture picture){
        Assert.notNull(picture,"Picture must not be null");

        return new PictureVo().convertFrom(picture);
    }

    @Override
    public List<Picture> listLatest(int top) {
        Assert.isTrue(top > 0,"Top number must not be less than 0");

        PageRequest pageRequest = PageRequest.of(0,top, Sort.by(Sort.Direction.DESC,"id"));
        return pictureDao.findAllByStatus(PictureStatus.EXIST,pageRequest).getContent();
    }

    @Override
    public PictureVo createBy(Picture picture) {
        return convertToVO(create(picture));
    }

    @Override
    public PictureVo updateBy(Picture picture) {
        return convertToVO(update(picture));
    }

    @Override
    public Picture upload(MultipartFile file) {
        Assert.notNull(file,"Picture upload must not be null");
        log.debug("开始上传图片... 类型:[{}]",file.getContentType());


        return null;
    }

    @Override
    public Picture removeSoftById(Long id) {
        Picture picture = getById(id);
        picture.setStatus(PictureStatus.DELETED);
        picture.setDeletedAt(new Date());

        return update(picture);
    }


}