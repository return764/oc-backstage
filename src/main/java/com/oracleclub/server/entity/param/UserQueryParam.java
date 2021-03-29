package com.oracleclub.server.entity.param;

import com.oracleclub.server.entity.enums.UserStatus;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author :RETURN
 * @date :2021/3/22 0:36
 */
@Data
public class UserQueryParam {
    private String name;
    private String stuNum;
    private String phNum;
    private String email;
    private Long depId;
    private UserStatus status;
    private LocalDateTime loginStart;
    private LocalDateTime loginEnd;
}
