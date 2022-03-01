package com.oracleclub.server.entity.param;

import com.oracleclub.server.converter.InputConverter;
import com.oracleclub.server.entity.OperationLog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author :RETURN
 * @date :2021/9/23 12:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogParam implements InputConverter<OperationLog> {
    private Long userId;
    private String type;
    private String content;
}
