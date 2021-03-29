package com.oracleclub.server.entity.param;

import com.oracleclub.server.converter.InputConverter;
import com.oracleclub.server.entity.Department;
import lombok.Data;

/**
 * @author :RETURN
 * @date :2021/3/27 0:51
 */
@Data
public class DepartmentParam implements InputConverter<Department> {

    private String name;
    private String aliasName;
    private String content;
}
