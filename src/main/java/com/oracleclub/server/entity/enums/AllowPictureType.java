package com.oracleclub.server.entity.enums;


import com.oracleclub.server.exception.PictureException;

/**
 * 定义所允许的图片格式
 * @author RETURN
 * @date 2020/8/15 23:05
 */
public enum AllowPictureType {

    //png格式
    PNG("image/png"),
    //jpg格式
    JPG("image/jpeg"),
    //gif格式
    GIF("image/gif");


    private final String type;

    AllowPictureType(String type) {
        this.type = type;
    }

    public static boolean checkType(String type){
        AllowPictureType[] types = AllowPictureType.values();
        for (AllowPictureType t:types) {
            if (t.type.equals(type)){
                return true;
            }
        }
        throw new PictureException("文件格式错误");
    }
}
