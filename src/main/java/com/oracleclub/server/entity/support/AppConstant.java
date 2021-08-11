package com.oracleclub.server.entity.support;

import java.io.File;

/**
 * @author :RETURN
 * @date :2021/2/25 22:26
 */
public class AppConstant {

    public final static String BASE_DIR = "oc";

    public final static String USER_HOME = System.getProperties().getProperty("user.home");

    public final static String PROTOCOL_HTTPS = "https://";

    public final static String PROTOCOL_HTTP = "http://";

    public final static String URL_SEPARATOR = "/";

    public static final String FILE_SEPARATOR = File.separator;

}
