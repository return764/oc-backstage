package com.oracleclub.server.handler.file.support;

import com.oracleclub.server.entity.bbs.Post;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.oracleclub.server.entity.support.AppConstant.FILE_SEPARATOR;

/**
 * @author :RETURN
 * @date :2021/11/2 0:09
 */
@Component("postImageUpload")
public class PostImageUpload implements UploadStrategy<Post> {
    private final static String POST_IMG_DIR = "post/";
    private final static String DEFAULT = "default";


    @Override
    public String createUploadPath(Post post) {
        String middlePath;
        if (post == null){
            middlePath = DEFAULT;
        }else {
            Optional<String> op = Optional.of(post.getId().toString());
            middlePath = op.orElse(DEFAULT);
        }

        return POST_IMG_DIR + middlePath + FILE_SEPARATOR;
    }

    @Override
    public String createUploadPath() {
        return createUploadPath(null);
    }
}
