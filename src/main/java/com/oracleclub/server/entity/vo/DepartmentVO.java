package com.oracleclub.server.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.oracleclub.server.converter.OutputConverter;
import com.oracleclub.server.entity.Department;
import lombok.Data;

import java.util.Date;

/**
 * @author :RETURN
 * @date :2021/3/20 18:04
 */
@Data
public class DepartmentVO implements OutputConverter<DepartmentVO, Department> {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String name;
    private String aliasName;
    private String content;
    private Date createdAt;
    private Date updatedAt;
}
