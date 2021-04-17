package com.oracleclub.server.service.impl;

import com.oracleclub.server.dao.PictureDao;
import com.oracleclub.server.entity.Picture;
import com.oracleclub.server.entity.enums.AttachmentType;
import com.oracleclub.server.entity.enums.PictureStatus;
import com.oracleclub.server.entity.param.PictureQueryParam;
import com.oracleclub.server.entity.support.UploadResult;
import com.oracleclub.server.entity.vo.PictureVO;
import com.oracleclub.server.handler.file.FileHandlers;
import com.oracleclub.server.service.PictureService;
import com.oracleclub.server.service.base.AbstractCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Predicate;
import java.util.LinkedList;
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
    private final FileHandlers fileHandlers;

    protected PictureServiceImpl(PictureDao pictureDao,FileHandlers fileHandlers) {
        super(pictureDao);
        this.pictureDao = pictureDao;
        this.fileHandlers = fileHandlers;
    }

    @Override
    public PictureVO convertToVO(Picture picture) {
        return convertTo(picture);
    }

    @Override
    public List<PictureVO> convertToListVO(List<Picture> pictures) {
        return pictures.stream().map(this::convertTo).collect(Collectors.toList());
    }

    @Override
    public Page<PictureVO> convertToPageVO(Page<Picture> pictures) {
        return pictures.map(this::convertTo);
    }

    private PictureVO convertTo(Picture picture){
        Assert.notNull(picture,"图片不能为空");

        return new PictureVO().convertFrom(picture);
    }

    @Override
    public List<Picture> listLatest(int top) {
        Assert.isTrue(top > 0,"Top number must not be less than 0");

        PageRequest pageRequest = PageRequest.of(0,top, Sort.by(Sort.Direction.DESC,"id"));
        return pictureDao.findAllByStatus(PictureStatus.EXIST,pageRequest).getContent();
    }

    @Override
    public PictureVO createBy(Picture picture) {
        return convertToVO(create(picture));
    }

    @Override
    public PictureVO updateBy(Picture picture) {
        return convertToVO(update(picture));
    }

    @Override
    public Picture upload(MultipartFile file) {
        Assert.notNull(file,"上传图片不能为空");
        UploadResult upload = fileHandlers.upload(file, AttachmentType.LOCAL,true);

        Picture picture = new Picture();
        picture.setHeight(upload.getHeight());
        picture.setWidth(upload.getWidth());
        picture.setType("default");
        picture.setSuffix(upload.getSuffix());
        picture.setSize(upload.getSize());
        picture.setMediaType(upload.getMediaType().toString());
        picture.setPath(upload.getFilePath());
        picture.setThumbPath(upload.getThumbPath());
        picture.setStatus(PictureStatus.EXIST);

        return create(picture);
    }

    @Override
    public List<String> getTypes() {
        return pictureDao.findAllTypes();
    }

    @Override
    public Page<PictureVO> pageByParam(Pageable pageable, PictureQueryParam pictureParam) {
        Assert.notNull(pageable,"分页参数不能为空");

        Page<Picture> all = pictureDao.findAllWithExist(buildQuery(pictureParam), pageable);
        return convertToPageVO(all);
    }

    private Specification<Picture> buildQuery(PictureQueryParam pictureParam) {
        Assert.notNull(pictureParam,"参数不能为空");
        return (Specification<Picture>) (root, cq, cb) -> {
            List<Predicate> predicates = new LinkedList<>();

            if (pictureParam.getType() != null){
                predicates.add(cb.equal(root.get("type").as(String.class),pictureParam.getType().trim()));
            }

            if (pictureParam.getName() != null){
                predicates.add(cb.or(
                        cb.like(root.get("name").as(String.class),"%"+pictureParam.getName().trim()+"%")
                ));
            }
            return cq.where(predicates.toArray(new Predicate[0])).getRestriction();
        };
    }


}