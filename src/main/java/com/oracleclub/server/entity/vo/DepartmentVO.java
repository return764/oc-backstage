package com.oracleclub.server.entity.vo;

import com.oracleclub.server.converter.OutputConverter;
import com.oracleclub.server.entity.Department;
import lombok.Data;

/**
 * @author :RETURN
 * @date :2021/3/20 18:04
 */
@Data
public class DepartmentVO implements OutputConverter<DepartmentVO, Department> {

    private Long id;
    private String name;
    private String aliasName;
    private String content;
}
