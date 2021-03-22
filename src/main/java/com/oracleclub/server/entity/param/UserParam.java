package com.oracleclub.server.entity.param;

import lombok.Data;

import java.util.Date;

/**
 * @author :RETURN
 * @date :2021/3/22 0:36
 */
@Data
public class UserParam {
    private String name;
    private String stuNum;
    private String phNum;
    private String email;
    private Long depId;
    private Date loginStart;
    private Date loginEnd;
}
