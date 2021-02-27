package com.oracleclub.server.utils;

import org.springframework.util.Assert;

import java.nio.file.Paths;

/**
 * @author :RETURN
 * @date :2021/2/26 15:21
 */
public class FilenameUtils {
    private FilenameUtils(){}

    public static String getBasename(String filename){
        Assert.hasText(filename,"Filename must not be blank");

        return Paths.get(filename).getFileName().toString();
    }

    public static String getExtension(String filename){
        Assert.hasText(filename,"Filename must not be blank");

        int dotIndex = filename.indexOf(".");

        if (dotIndex < 0){
            return "";
        }

        return filename.substring(dotIndex + 1);
    }
}
