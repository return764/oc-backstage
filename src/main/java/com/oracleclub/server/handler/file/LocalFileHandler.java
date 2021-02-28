package com.oracleclub.server.handler.file;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.oracleclub.server.config.properties.AppProperties;
import com.oracleclub.server.entity.enums.AttachmentType;
import com.oracleclub.server.entity.support.UploadResult;
import com.oracleclub.server.exception.FileOperationException;
import com.oracleclub.server.utils.ImageUtils;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

import static com.oracleclub.server.entity.support.AppConstant.FILE_SEPARATOR;

/**
 * @author :RETURN
 * @date :2021/2/25 22:32
 */
@Slf4j
@Component
public class LocalFileHandler implements FileHandler{

    private final static String UPLOAD_DIR = "upload/";

    private final static String THUMBNAIL_SUFFIX = "-thumbnail";

    private final static int THUMB_WIDTH = 256;
    private final static int THUMB_HEIGHT = 256;

    private final AppProperties appProperties;
    private String workDir;
    ReentrantLock lock = new ReentrantLock();

    public LocalFileHandler(AppProperties appProperties){
        this.appProperties = appProperties;
        workDir = FileHandler.normalizeDirectory(appProperties.getWorkPath());

        checkDir();
    }

    private void checkDir(){
        Path workPath = Paths.get(workDir);

        Assert.isTrue(Files.isDirectory(workPath), workDir + " 不是一个目录");
        Assert.isTrue(Files.isReadable(workPath), workDir + " 不可读");
        Assert.isTrue(Files.isWritable(workPath), workDir + " 不可写");
    }

    @Override
    public UploadResult upload(MultipartFile file) {
        Assert.notNull(file,"上传文件不能为空");
        Calendar current = Calendar.getInstance();

        int year = current.get(Calendar.YEAR);
        int month = current.get(Calendar.MONTH);

        String monthStr = month > 10 ? String.valueOf(month) : "0"+month;

        String uploadDir = UPLOAD_DIR + year + FILE_SEPARATOR + monthStr + FILE_SEPARATOR;
        String orgBasename = FileUtil.mainName(Objects.requireNonNull(file.getOriginalFilename()));

        String ext = FileUtil.extName(file.getOriginalFilename());
        String basename =  orgBasename + "-" + IdUtil.fastSimpleUUID();

        log.debug("基本名称:[{}],后缀:[{}],原文件名:[{}]",basename,ext,file.getOriginalFilename());

        String subFilePath = uploadDir + basename + "." + ext;

        Path uploadPath = Paths.get(workDir,subFilePath);

        log.debug("上传文件 [{}] -----> 目录:[{}]",file.getOriginalFilename(),uploadPath.toString());

        try{
            Files.createDirectories(uploadPath.getParent());
            Files.createFile(uploadPath);

            //上传文件
            file.transferTo(uploadPath);

            UploadResult uploadResult = new UploadResult();
            uploadResult.setFileName(orgBasename);
            uploadResult.setFilePath(subFilePath);
            uploadResult.setKey(subFilePath);
            uploadResult.setSuffix(ext);
            uploadResult.setSize(file.getSize());
            uploadResult.setMediaType(MediaType.valueOf(Objects.requireNonNull(file.getContentType())));

            if (FileHandler.isImageType(uploadResult.getMediaType())){
                lock.lock();
                try(InputStream uploadFileInputStream = new FileInputStream(uploadPath.toFile())) {
                    String thumbnailBasename = basename + THUMBNAIL_SUFFIX;
                    String thumbnailFilePath = uploadDir + thumbnailBasename + "." + ext;

                    Path thumbnailPath = Paths.get(workDir,thumbnailFilePath);

                    BufferedImage originalImage = ImageUtils.getImageFromFile(uploadFileInputStream, ext);
                    uploadResult.setWidth(originalImage.getWidth());
                    uploadResult.setHeight(originalImage.getHeight());

                    boolean result = generateThumbnail(originalImage, thumbnailPath, ext);
                    if (result) {
                        uploadResult.setThumbPath(thumbnailFilePath);
                    } else {
                        uploadResult.setThumbPath(subFilePath);
                    }
                }finally {
                    lock.unlock();
                }
            }
            log.info("上传文件 [{}] -----> 目录:[{}]成功", file.getOriginalFilename(), uploadPath.toString());
            return uploadResult;
        }catch (IOException e){
            throw new FileOperationException("文件上传失败").setErrorData(e);
        }
    }

    @Override
    public void delete(String key) {
        Assert.hasText(key, "File key must not be blank");

        Path filePath = Paths.get(workDir, key);

        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new FileOperationException("附件 " + key + " 删除失败", e);
        }

        String basename = FileUtil.mainName(key);
        String extension = FileUtil.extName(key);

        String thumbnailName = basename + THUMBNAIL_SUFFIX + '.' + extension;

        Path thumbnailPath = Paths.get(filePath.getParent().toString(), thumbnailName);

        try {
            boolean deleteResult = Files.deleteIfExists(thumbnailPath);
            if (!deleteResult) {
                log.warn("Thumbnail: [{}] may not exist", thumbnailPath.toString());
            }
        } catch (IOException e) {
            throw new FileOperationException("附件缩略图 " + thumbnailName + " 删除失败", e);
        }
    }

    @Override
    public AttachmentType getAttachmentType() {
        return AttachmentType.LOCAL;
    }

    private boolean generateThumbnail(BufferedImage originalImage, Path thumbPath, String extension) {
        Assert.notNull(originalImage, "Image must not be null");
        Assert.notNull(thumbPath, "Thumb path must not be null");


        boolean result = false;
        //创建缩略图
        try {
            Files.createFile(thumbPath);
            // Convert to thumbnail and copy the thumbnail
            log.debug("正在生成缩略图: [{}]", thumbPath.toString());
            Thumbnails.of(originalImage).size(THUMB_WIDTH, THUMB_HEIGHT).keepAspectRatio(true).toFile(thumbPath.toFile());
            log.debug("生成缩略图成功,写入到[{}]", thumbPath.toString());
            result = true;
        } catch (Throwable t) {
            log.warn("生成缩略图失败: " + thumbPath, t);
        } finally {
            // Disposes of this graphics context and releases any system resources that it is using.
            originalImage.getGraphics().dispose();
        }
        return result;
    }
}
