package com.oracleclub.server.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.oracleclub.server.converter.InputConverter;
import com.oracleclub.server.converter.OutputConverter;
import com.oracleclub.server.entity.User;
import lombok.Data;

/**
 * @author :RETURN
 * @date :2021/10/29 10:48
 */
@Data
public class SimpleUserVO implements OutputConverter<SimpleUserVO, User>, InputConverter<User> {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String name;
    private String email;
    private String nickname;
    private String stuNum;
    private String info;
    private String avatar;
    private DepartmentVO department;
}
