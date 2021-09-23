package com.oracleclub.server.handler.autofill;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author :RETURN
 * @date :2021/9/22 14:40
 */
@Component
public class AutoFillObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject,"updatedAt", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject,"createdAt", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject,"likeCount", Integer.class, 0);
        this.strictInsertFill(metaObject,"viewCount", Integer.class, 0);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject,"updatedAt", LocalDateTime.class, LocalDateTime.now());
    }
}
